from pyspark.sql import SparkSession
import os

from pyspark.sql.functions import col, when, lit, sum

DATABASE_IP = os.environ["IP_ADDR"] if ("IP_ADDR" in os.environ) else "localhost:3307"

spark = SparkSession.builder.appName("Category statistics").getOrCreate()

order_table = spark.read \
    .format("jdbc") \
    .option("driver", "com.mysql.cj.jdbc.Driver") \
    .option("url", f"jdbc:mysql://{DATABASE_IP}/store") \
    .option("dbtable", "store.order") \
    .option("user", "root") \
    .option("password", "root") \
    .load()

inorder_table = spark.read \
    .format("jdbc") \
    .option("driver", "com.mysql.cj.jdbc.Driver") \
    .option("url", f"jdbc:mysql://{DATABASE_IP}/store") \
    .option("dbtable", "store.in_order") \
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

pc_table = spark.read \
    .format("jdbc") \
    .option("driver", "com.mysql.cj.jdbc.Driver") \
    .option("url", f"jdbc:mysql://{DATABASE_IP}/store") \
    .option("dbtable", "store.product_category") \
    .option("user", "root") \
    .option("password", "root") \
    .load()


category_table = spark.read \
    .format("jdbc") \
    .option("driver", "com.mysql.cj.jdbc.Driver") \
    .option("url", f"jdbc:mysql://{DATABASE_IP}/store") \
    .option("dbtable", "store.category") \
    .option("user", "root") \
    .option("password", "root") \
    .load()



categories = category_table.alias("c").join(
        pc_table.alias("pc"),
        category_table["id"] == pc_table["idC"], "left"
    ).join(
        product_table.alias("p"),
        product_table["id"]==pc_table["idP"], "left"
    ).join(
        inorder_table.alias("io"),
        inorder_table["idP"]==product_table["id"], "left"
    ).join(
        order_table.alias("o"),
        order_table["id"]==inorder_table["idO"], "left"
    ).groupby("c.name") \
    .agg(sum(when(col("o.status") == "COMPLETE", col("io.count")).otherwise(lit(0))).alias("cnt"))\
	.orderBy(col("cnt").desc(), col("c.name")).collect()

ret_list = [c["name"] for c in categories]

all_c=category_table.alias("c").orderBy(col("c.name")).collect()
for c in all_c:
    if c["name"] not in ret_list:
        ret_list.append(c["name"])

obj='{"statistics":'+str(ret_list)+'}'
print("%%%%%"+obj+"%%%%%")

spark.stop()