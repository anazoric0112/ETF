import json
import os
import subprocess

from flask import Flask



application = Flask ( __name__ )

@application.route("/product_statistics",methods=["GET"])
def product_statistics():
    os.environ["SPARK_APPLICATION_PYTHON_LOCATION"] = "/app/statistics_p.py"

    os.environ[
        "SPARK_SUBMIT_ARGS"] = "--driver-class-path /app/mysql-connector-j-8.0.33.jar --jars /app/mysql-connector-j-8.0.33.jar"

    result = subprocess.check_output(["/template.sh"]).decode().split("%%%%%")
    # return json.loads(result.replace("/",""))
    return result[1]


@application.route("/category_statistics",methods=["GET"])
def category_statistics():
    os.environ["SPARK_APPLICATION_PYTHON_LOCATION"] = "/app/statistics_c.py"

    os.environ[
        "SPARK_SUBMIT_ARGS"] = "--driver-class-path /app/mysql-connector-j-8.0.33.jar --jars /app/mysql-connector-j-8.0.33.jar"

    result = subprocess.check_output(["/template.sh"]).decode().split("%%%%%")
    # return json.loads(result.replace("/",""))
    return result[1]


if (__name__ == "__main__"):
    application.run ( debug = True, host = "0.0.0.0", port = 5003 )
