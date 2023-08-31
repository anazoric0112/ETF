import os

import requests
import web3
from eth_account import Account
from flask import Flask, request, json, Response
from flask_jwt_extended import JWTManager, jwt_required, get_jwt_identity, get_jwt
from sqlalchemy import and_, or_
from sqlalchemy_utils import database_exists, create_database
from web3 import Web3, HTTPProvider

from role_check import roleCheck
from configuration import Configuration
from models import *

import io
import csv

def read_file(path):
	with open(path, 'r') as file:
		return file.read()

application = Flask ( __name__ )
application.config.from_object ( Configuration )
jwt = JWTManager ( application )

host_bc="localhost"
try:
    host_bc=os.environ['BC_HOST']
except:
    pass

web3 = Web3(HTTPProvider(f"http://{host_bc}:8545"))
admin_acc = web3.eth.accounts[0]
abifile=read_file("./contracts/Contract.abi")
bytefile=read_file("./contracts/Contract.bin")
contract = web3.eth.contract(abi=abifile,bytecode=bytefile)


def ret_msg(msg):
    return json.dumps({"message":msg})

@application.route("/update",methods=["POST"])
@jwt_required()
@roleCheck ( role = "admin" )
def update():
    file=None
    try:
        file=request.files["file"]
    except:
        return Response(ret_msg("Field file is missing."),status=400)
    content = file.stream.read ( ).decode ( "utf-8" )
    stream = io.StringIO ( content )
    reader = csv.reader ( stream )

    products = []
    product_categories=dict()
    categories = []
    c_appeared=[]
    rows=[]
    for row in reader:
        rows.append(row)

    for i in range(len(rows)):
        row=rows[i]
        if len(row)<3 or len(row[0])==0 or len(row[1])==0 or len(row[2])==0:
            return Response(ret_msg(f"Incorrect number of values on line {i}."),status=400)


    for i in range(len(rows)):
        row=rows[i]
        try:
            if float(row[2]) <= 0:
                return Response(ret_msg( f"Incorrect price on line {i}."), status=400)
        except:
            return Response(ret_msg(f"Incorrect price on line {i}."), status=400)
    for row in rows:
        if len(Product.query.filter(Product.name == row[1]).all()) > 0:
            return Response(ret_msg(f"Product {row[1]} already exists."), status=400)

    for row in rows:
        category_names = row[0].split('|')
        name=row[1]
        price=float(row[2])

        for category_name in category_names:
            category = Category.query.filter(Category.name == category_name).all()
            if len(category) > 0: continue
            if category_name in c_appeared: continue

            category = Category(name=category_name)
            categories.append(category)
            c_appeared.append(category_name)

        product = Product(name=name, price=price)
        products.append(product)

        product_categories[product] = category_names

    database.session.add_all(categories)
    database.session.add_all (products)
    database.session.commit()

    pcs=[]
    for p in product_categories.keys():
        for cat in product_categories[p]:
            c=Category.query.filter(Category.name==cat).first()
            pc=ProductCategory(idP=p.id, idC=c.id)
            pcs.append(pc)

    database.session.add_all (pcs)
    database.session.commit ()

    return Response(status=200)


@application.route("/product_statistics",methods=["GET"])
@jwt_required()
@roleCheck ( role = "admin" )
def product_statistics():
    IP="localhost"
    try:
        IP=os.environ["IP_ADDR"]
    except:
        pass
    resp = requests.get(f"http://{IP}:5003/product_statistics")
    return Response(resp.text.replace("'",'"'),status=200)

@application.route("/category_statistics",methods=["GET"])
@jwt_required()
@roleCheck ( role = "admin" )
def category_statistics():
    IP="localhost"
    try:
        IP=os.environ["IP_ADDR"]
    except:
        pass
    resp = requests.get(f"http://{IP}:5003/category_statistics")
    # return Response(json.dumps(resp.text),status=200)
    return Response(resp.text.replace("'",'"'),status=200)


@application.route("/search",methods=["GET"])
@jwt_required()
@roleCheck ( role = "customer" )
def search():
    category=""; product=""
    p=[]
    for k,v in request.args.items():
        if k=="name": product=v
        if k=="category": category=v


    query_c=Category.query.join(ProductCategory).join(Product)
    query_p=Product.query.join(ProductCategory).join(Category)

    if len(category)>0:
        query_c=query_c.filter(Category.name.like(f"%{category}%"))
        query_p=query_p.filter(Category.name.like(f"%{category}%"))
    if len(product)>0:
        query_c=query_c.filter(Product.name.like(f"%{product}%"))
        query_p=query_p.filter(Product.name.like(f"%{product}%"))

    categories=query_c.all()
    products=query_p.all()
    product_list=[]
    for p in products:
        obj={
            "categories":[c.name for c in p.categories],
            "id":p.id,
            "name":p.name,
            "price":p.price
        }
        product_list.append(obj)
    cat_list=[c.name for c in categories]

    return Response(json.dumps({
            "categories": cat_list,
            "products": product_list
        }),status=200)


@application.route("/order",methods=["POST"])
@jwt_required()
@roleCheck ( role = "customer" )
def order():
    content=request.json
    try:
        content=content['requests']
    except:
        return Response(ret_msg("Field requests is missing."),status=400)
    for i in range(len(content)):
        if content[i].get("id")==None:
            return Response(ret_msg(f"Product id is missing for request number {i}."),status=400)
    for i in range(len(content)):
        if content[i].get("quantity")==None:
            return Response(ret_msg(f"Product quantity is missing for request number {i}."), status=400)
    for i in range(len(content)):
        if not str(content[i]["id"]).isnumeric() or int(content[i]["id"])<=0:
            return Response(ret_msg(f"Invalid product id for request number {i}."), status=400)
    for i in range(len(content)):
        if not str(content[i]["quantity"]).isnumeric() or int(content[i]["quantity"])<=0:
            return Response(ret_msg(f"Invalid product quantity for request number {i}."), status=400)
    price=0
    for i in range(len(content)):
        try:
            p=Product.query.filter(Product.id==int(content[i]["id"])).all()
            if len(p)==0: return Response(ret_msg(f"Invalid product for request number {i}."), status=400)
            p=p[0]
            price+=p.price*int(content[i]["quantity"])
        except:
            return Response(ret_msg(f"Invalid product for request number {i}."), status=400)

    address=""
    try:
        address=request.json['address']
        if address=="": return Response(ret_msg("Field address is missing."),status=400)
    except:
        return Response(ret_msg("Field address is missing."),status=400)
    if not web3.is_address(address):
        return Response(ret_msg("Invalid address."),status=400)

    #make contract
    transaction_hash = contract.constructor(address).transact({"from": admin_acc})
    receipt = web3.eth.wait_for_transaction_receipt(transaction_hash)

    data = get_jwt()

    order=Order(price=price,status="CREATED",idU=data["email"],contract=receipt.contractAddress, address=address)
    database.session.add(order)
    database.session.commit()
    items=[]
    for r in content:
        item=InOrder(count=r["quantity"],idP=r["id"],idO=order.id)
        items.append(item)

    database.session.add_all(items)
    database.session.commit()

    return Response(json.dumps({"id":order.id}),status=200)


@application.route("/status",methods=["GET"])
@jwt_required()
@roleCheck ( role = "customer" )
def status():
    data = get_jwt()
    orders=Order.query.filter(Order.idU==data["email"])

    ret_list=[]
    for o in orders:
        p_list=[]
        for p in o.products:
            p_list.append({
                "categories":[c.name for c in p.categories],
                "name":p.name,
                "price":p.price,
                "quantity":InOrder.query.filter(and_(InOrder.idP==p.id,InOrder.idO==o.id)).first().count
            })
        ret_list.append({
            "products":p_list,
            "price":o.price,
            "status":o.status,
            "timestamp":o.timestamp
        })

    return Response(json.dumps({"orders":ret_list}),status=200)


@application.route("/delivered",methods=["POST"])
@jwt_required()
@roleCheck ( role = "customer" )
def delivered():
    id = request.json
    try:
        id = id['id']
    except:
        return Response(ret_msg("Missing order id."), status=400)
    try:
        id=int(id)
    except:
        return Response(ret_msg("Invalid order id."), status=400)
    if id<=0:
        return Response(ret_msg("Invalid order id."),status=400)

    order=None
    try:
        order=Order.query.filter(Order.id==int(id)).all()
        if len(order)==0: return Response(ret_msg("Invalid order id."), status=400)
        order=order[0]
    except:
        return Response(ret_msg("Invalid order id."), status=400)

    if order.status!="PENDING":
        return Response(ret_msg("Invalid order id."), status=400)

    keys=None; passphrase=""
    try:
        keys=request.json["keys"]
        if keys=="": return Response(ret_msg("Missing keys."),status=400)
    except:
        return Response(ret_msg("Missing keys."),status=400)
    try:
        passphrase=request.json["passphrase"]
        if passphrase=="": return Response(ret_msg("Missing passphrase."),status=400)
    except:
        return Response(ret_msg("Missing passphrase."),status=400)
    private_key="";address=""
    try:
        keys=keys.replace("'",'"')
        keys = json.loads(keys)
        private_key = Account.decrypt(keys, passphrase).hex()
        # address = web3.to_checksum_address(keys["address"])
        address=Account.from_key(private_key).address
    except Exception:
        return Response(ret_msg("Invalid credentials."),status=400)

    contract_new = web3.eth.contract(order.contract, abi=abifile, bytecode=bytefile)

    if order.address!=address:
        return Response(ret_msg("Invalid customer account."),status=400)

    if not contract_new.functions.is_paid().call():
        return Response(ret_msg("Transfer not complete."),status=400)
    if not contract_new.functions.in_transit().call():
        return Response(ret_msg("Delivery not complete."), status=400)

    transaction = contract_new.functions.deliver().build_transaction({
        "from": address,
        "gasPrice": 21000,
        "nonce": web3.eth.get_transaction_count(address)
    })

    signed_transaction = web3.eth.account.sign_transaction(transaction, private_key)
    transaction_hash = web3.eth.send_raw_transaction(signed_transaction.rawTransaction)
    receipt = web3.eth.wait_for_transaction_receipt(transaction_hash)

    order.status="COMPLETE"
    database.session.commit()

    return Response(status=200)


@application.route("/orders_to_deliver",methods=["GET"])
@jwt_required()
@roleCheck ( role = "courier" )
def orders_to_deliver():
    orders=Order.query.filter(Order.status=="CREATED")
    ret_list=[]
    for o in orders:
        ret_list.append({
            "id":o.id,
            "email":o.idU
        })
    return Response(json.dumps({"orders":ret_list}))


@application.route("/pick_up_order",methods=["POST"])
@jwt_required()
@roleCheck ( role = "courier" )
def pick_up_order():
    id = request.json
    try:
        id = id['id']
    except:
        return Response(ret_msg("Missing order id."), status=400)
    if not str(id).isnumeric() or int(id) <= 0:
        return Response(ret_msg("Invalid order id."), status=400)
    order = None
    try:
        order = Order.query.filter(Order.id == int(id)).all()
        if len(order)==0: return Response(ret_msg("Invalid order id."), status=400)
        order=order[0]
    except:
        return Response(ret_msg("Invalid order id."), status=400)
    if order.status != "CREATED":
        return Response(ret_msg("Invalid order id."), status=400)
    address=""
    try:
        address=request.json['address']
        if address=="": return Response(ret_msg("Missing address."),status=400)
    except:
        return Response(ret_msg("Missing address."),status=400)
    if not web3.is_address(address):
        return Response(ret_msg("Invalid address."),status=400)
    contract_new = web3.eth.contract(order.contract, abi=abifile, bytecode=bytefile)

    if not contract_new.functions.is_paid().call():
        return Response(ret_msg("Transfer not complete."), status=400)

    contract_new.functions.pick_up_order(address).transact({"from": admin_acc})

    order.status = "PENDING"
    database.session.commit()

    return Response(status=200)

@application.route("/pay",methods=["POST"])
@jwt_required()
@roleCheck ( role = "customer" )
def pay():
    id = request.json
    try:
        id = id['id']
    except:
        return Response(ret_msg("Missing order id."), status=400)
    if not str(id).isnumeric() or int(id) <= 0:
        return Response(ret_msg("Invalid order id."), status=400)
    order = None
    try:
        order = Order.query.filter(Order.id == int(id)).all()
        if len(order) == 0: return Response(ret_msg("Invalid order id."), status=400)
        order = order[0]
    except:
        return Response(ret_msg("Invalid order id."), status=400)

    keys = None;
    passphrase = ""
    try:
        keys = request.json["keys"]
        if keys=="": return Response(ret_msg("Missing keys."), status=400)
    except:
        return Response(ret_msg("Missing keys."), status=400)
    try:
        passphrase = request.json["passphrase"]
        if passphrase=="": return Response(ret_msg("Missing passphrase."), status=400)
    except:
        return Response(ret_msg("Missing passphrase."), status=400)
    private_key = "";
    address = ""
    try:
        keys = json.loads(keys.replace("'", '"'))
        private_key = Account.decrypt(keys, passphrase).hex()
        address = web3.to_checksum_address(keys["address"])
    except:
        return Response(ret_msg("Invalid credentials."), status=400)

    if web3.eth.get_balance(address) < order.price:
        return Response(ret_msg("Insufficient funds."),status=400)

    contract_new = web3.eth.contract(order.contract, abi=abifile, bytecode=bytefile)
    if contract_new.functions.is_paid().call():
        return Response(ret_msg("Transfer already complete."), status=400)

    transaction = contract_new.functions.pay().build_transaction({
        "from": address,
        "value": int(order.price),
        "gasPrice": 21000,
        "nonce": web3.eth.get_transaction_count(address)
    })

    signed_transaction = web3.eth.account.sign_transaction(transaction, private_key)
    transaction_hash = web3.eth.send_raw_transaction(signed_transaction.rawTransaction)
    receipt = web3.eth.wait_for_transaction_receipt(transaction_hash)

    return Response(status=200)

@application.route("/",methods=["GET"])
def index():
    return "Hello world"

if (__name__ == "__main__"):
    database.init_app(application)
    if (not database_exists(Configuration.SQLALCHEMY_DATABASE_URI)):
        create_database(Configuration.SQLALCHEMY_DATABASE_URI)
    application.run ( debug = True, host = "0.0.0.0")
