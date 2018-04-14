import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema", "true").csv("Sales.csv")

df.printSchema()

df.show()

// groupBy
// takes numerical columns and runs the aggregate
df.groupBy("Company").mean().show()
df.groupBy("Company").max().show()
df.groupBy("Company").min().show()
df.groupBy("Company").sum().show()


// select specific column that you want to aggregate
// returns a single output -> can be an array
df.select(countDistinct("Sales")).show()
df.select(sumDistinct("Sales")).show()
df.select(variance("Sales")).show()
df.select(stddev("Sales")).show()
df.select(collect_set("Sales")).show()


// orderBy
df.orderBy("Sales").show()

// use descending order
df.orderBy($"Sales".desc).show()
