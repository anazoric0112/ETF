from datetime import timedelta
import os

host=""
try:
    host=os.environ["DBHOST"]
except:
    host = "localhost:3309"


class Configuration ( ):
    SQLALCHEMY_DATABASE_URI = f"mysql+pymysql://root:root@{host}/authentication"
    JWT_SECRET_KEY = "JWT_SECRET_KEY"
    JWT_ACCESS_TOKEN_EXPIRES = timedelta ( hours=1 )
    JWT_REFRESH_TOKEN_EXPIRES = timedelta ( days = 30 )
