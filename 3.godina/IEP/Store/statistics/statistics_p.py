from pyspark.sql import SparkSession
import os

from pyspark.sql.functions import col, when, lit, sum

DATABASE_IP = os.environ["IP_ADDR"] if ("IP_ADDR" in os.environ) else "localhost:3307"

spark = SparkSession.builder.appName("Product statistics").getOrCreate()


inorder_table = spark.read \
    .format("jdbc") \
    .option("driver", "com.mysql.cj.jdbc.Driver") \
    .option("url", f"jdbc:mysql://{DATABASE_IP}/store") \
    .option("dbtable", "store.in_order") \
    .option("user", "root") \
    .option("password", "root") \
    .load()

order_table = spark.read \
    .format("jdbc") \
    .option("driver", "com.mysql.cj.jdbc.Driver") \
    .option("url", f"jdbc:mysql://{DATABASE_IP}/store") \
    .option("dbtable", "store.order") \
    .option("user", "root") \
    .option("password", "root") \
    .load()


product_table = spark.read \
    .format("jdbc") \
    .option("driver", "com.mysql.cj.jdbc.Driver") \
    .option("url", f"jdbc:mysql://{DATABASE_IP}/store") \
    .option("dbtable", "store.product") \
    .option("user", "root") \
    .option("password", "root") \
    .load()

ret_list = []
res=product_table.alias("p").join(
    inorder_table.alias("io"),
    inorder_table["idP"]==product_table["id"]
).join(
    order_table.alias("o"),
    order_table["id"]==inorder_table["idO"]
).groupby("p.name").agg(
    sum(when(col("o.status") == "COMPLETE", col("io.count")).otherwise(lit(0))).alias("sold"),
    sum(when(col("o.status") != "COMPLETE", col("io.count")).otherwise(lit(0))).alias("waiting")
).collect()

for row in res:
    stat = {
        "name": row["name"],
        "sold": row["sold"],
        "waiting": row["waiting"]
    }
    ret_list.append(stat)
obj='{"statistics":'+str(ret_list)+'}'
print("%%%%%"+obj+"%%%%%")

spark.stop()