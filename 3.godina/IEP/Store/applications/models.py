from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import func

database = SQLAlchemy ( )

class ProductCategory(database.Model):
    __tablename__="product_category"

    id=database.Column(database.Integer,primary_key=True)
    idP=database.Column(database.Integer,database.ForeignKey("product.id"),nullable=False)
    idC=database.Column(database.Integer,database.ForeignKey("category.id"),nullable=False)

class InOrder(database.Model):
    __tablename__="in_order"

    id=database.Column(database.Integer,primary_key=True)

    idP=database.Column(database.Integer,database.ForeignKey("product.id"),nullable=False)
    idO=database.Column(database.Integer,database.ForeignKey("order.id"),nullable=False)
    count=database.Column(database.Integer, nullable=False)
class Product(database.Model):
    __tablename__="product"

    id=database.Column(database.Integer,primary_key=True)
    name=database.Column(database.String(256),nullable=False,unique=True)
    price=database.Column(database.Float,nullable=False)

    categories=database.relationship("Category", secondary = ProductCategory.__table__,back_populates="products")
    orders = database.relationship("Order", secondary=InOrder.__table__, back_populates="products")


class Category(database.Model):
    __tablename__="category"

    id=database.Column(database.Integer,primary_key=True)
    name=database.Column(database.String(256),nullable=False) #unique?

    products=database.relationship("Product",secondary=ProductCategory.__table__,back_populates="categories")

class Order(database.Model):
    __tablename__="order"

    id=database.Column(database.Integer, primary_key=True)
    price=database.Column(database.Float,nullable=False)
    status=database.Column(database.String(256),nullable=False)
    timestamp=database.Column(database.DateTime,nullable=False,default=func.now())
    idU=database.Column(database.String(256),nullable=False)
    contract=database.Column(database.String(256),nullable=False)
    address=database.Column(database.String(256),nullable=False)

    products=database.relationship("Product",secondary=InOrder.__table__,back_populates="orders")
