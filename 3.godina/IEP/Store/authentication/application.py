from flask import Flask, request, Response, jsonify, json
from sqlalchemy_utils import database_exists, create_database

from configuration import Configuration
from models import *
from flask_jwt_extended import JWTManager, create_access_token, jwt_required, get_jwt, get_jwt_identity
from sqlalchemy import and_
import re

application = Flask ( __name__ )
application.config.from_object ( Configuration )



@application.route ( "/register_customer", methods = ["POST"] )
def register_customer ( ):
    forename = request.json.get ( "forename", "" )
    surname = request.json.get ( "surname", "" )
    email = request.json.get ( "email", "" )
    password = request.json.get ( "password", "" )

    if len(forename)==0:
        return  Response(json.dumps({"message":"Field forename is missing."}), status = 400)
    if len(surname)==0:
        return  Response(json.dumps({"message":"Field surname is missing."}), status = 400)
    if len(email)==0:
        return  Response(json.dumps({"message":"Field email is missing."}), status = 400)
    if len(password)==0:
        return  Response(json.dumps({"message":"Field password is missing."}), status = 400)

    email_regex = re.compile(r'([A-Za-z0-9]+[.-_])*[A-Za-z0-9]+@[A-Za-z0-9-]+(\.[A-Z|a-z]{2,})+')
    if not re.fullmatch(email_regex,email):
        return  Response(json.dumps({"message":"Invalid email."}), status = 400)

    u=User.query.filter(User.email==email).all()
    if len(u)>0:
        return Response(json.dumps({"message": "Email already exists."}), status=400)
    if len(password)<8:
        return Response(json.dumps({"message": "Invalid password."}), status=400)


    user = User ( email = email, password = password, forename = forename, surname = surname )
    database.session.add ( user )
    database.session.commit ( )

    userRole = UserRole ( userId = user.id, roleId = 2 ) #change in register_courier
    database.session.add ( userRole )
    database.session.commit ( )

    return Response ( status = 200 )

@application.route ( "/register_courier", methods = ["POST"] )
def register_courier ( ):
    forename = request.json.get ( "forename", "" )
    surname = request.json.get ( "surname", "" )
    email = request.json.get ( "email", "" )
    password = request.json.get ( "password", "" )

    if len(forename)==0:
        return  Response(json.dumps({"message":"Field forename is missing."}), status = 400)
    if len(surname)==0:
        return  Response(json.dumps({"message":"Field surname is missing."}), status = 400)
    if len(email)==0:
        return  Response(json.dumps({"message":"Field email is missing."}), status = 400)
    if len(password)==0:
        return  Response(json.dumps({"message":"Field password is missing."}), status = 400)

    email_regex = re.compile(r'([A-Za-z0-9]+[.-_])*[A-Za-z0-9]+@[A-Za-z0-9-]+(\.[A-Z|a-z]{2,})+')
    if not re.fullmatch(email_regex,email):
        return  Response(json.dumps({"message":"Invalid email."}), status = 400)

    u=User.query.filter(User.email==email).all()
    if len(u)>0:
        return Response(json.dumps({"message": "Email already exists."}), status=400)
    if len(password)<8:
        return Response(json.dumps({"message": "Invalid password."}), status=400)


    user = User ( email = email, password = password, forename = forename, surname = surname )
    database.session.add ( user )
    database.session.commit ( )

    userRole = UserRole ( userId = user.id, roleId = 3 )
    database.session.add ( userRole )
    database.session.commit ( )

    return Response ( status = 200 )

jwt = JWTManager ( application )

@application.route ( "/login", methods = ["POST"] )
def login ( ):
    email = request.json.get ( "email", "" )
    password = request.json.get ( "password", "" )

    if len(email)==0:
        return  Response(json.dumps({"message":"Field email is missing."}), status = 400)
    if len(password)==0:
        return  Response(json.dumps({"message":"Field password is missing."}), status = 400)

    email_regex = re.compile(r'([A-Za-z0-9]+[.-_])*[A-Za-z0-9]+@[A-Za-z0-9-]+(\.[A-Z|a-z]{2,})+')
    if not re.fullmatch(email_regex,email):
        return  Response(json.dumps({"message":"Invalid email."}), status = 400)


    user = User.query.filter(and_ (User.email == email, User.password == password )).first()

    if ( not user ):
        return Response (json.dumps({"message":"Invalid credentials."}), status = 400 )

    additionalClaims = {
            "forename": user.forename,
            "surname": user.surname,
            "email": user.email,
            "password": user.password,
            "roles": [ str ( role ) for role in user.roles ]
    }

    accessToken = create_access_token ( identity = user.email, additional_claims = additionalClaims )

    return Response(json.dumps({"accessToken": accessToken}), status=200)

@application.route("/delete",methods=["POST"])
@jwt_required ( )
def delete_user():
    identity = get_jwt_identity()
    data = get_jwt()

    email=data["email"]
    password=data["password"]
    user=User.query.filter(and_(User.email==email,User.password==password)).first()
    if not user:
        return Response(json.dumps({"message":"Unknown user."}),status=400)

    database.session.delete(user)
    database.session.commit()

    return Response(status=200)


@application.route ( "/", methods = ["GET"] )
def index ( ):
    return "Hello world!"

def init_roles_and_admin():
    with application.app_context() as context:
        adminRole=Role.query.filter(Role.name=="admin").all()
        if len(adminRole)==0:
            adminRole = Role(name="admin")
            database.session.add(adminRole)
        else:
            adminRole = Role.query.filter(Role.name == "admin").first()
        customerRole = Role.query.filter(Role.name == "customer").all()
        if len(customerRole) == 0:
            customerRole = Role(name="customer")
            database.session.add(customerRole)
        courierRole = Role.query.filter(Role.name == "courier").all()
        if len(courierRole) == 0:
            courierRole = Role(name="courier")
            database.session.add(courierRole)

        database.session.commit()

        admin=User.query.filter(User.email=="onlymoney@gmail.com").all()
        if len(admin)==0:
            admin = User(
                forename="Scrooge",
                surname="McDuck",
                email="onlymoney@gmail.com",
                password="evenmoremoney"
            )
            database.session.add(admin)
            database.session.commit()

            userRole = UserRole(
                userId=admin.id,
                roleId=adminRole.id
            )
            database.session.add(userRole)
            database.session.commit()

if ( __name__ == "__main__" ):
    database.init_app ( application )
    if (not database_exists(Configuration.SQLALCHEMY_DATABASE_URI)):
        create_database(Configuration.SQLALCHEMY_DATABASE_URI)
    init_roles_and_admin()
    application.run ( debug = True, host = "0.0.0.0")