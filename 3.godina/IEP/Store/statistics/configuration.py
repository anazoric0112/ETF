import os

host=""
try:
    host=os.environ["DBHOST"]
except:
    host = "localhost:3307"

class Configuration ( ):
    SQLALCHEMY_DATABASE_URI = f"mysql+pymysql://root:root@{host}/store"
    JWT_SECRET_KEY = "JWT_SECRET_KEY"

