import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema", "true").csv("CitiGroup2006_2008")

df.printSchema()

// grabs the month value from a datetime column
df.select(month(df("Date"))).show()
df.select(year(df("Year"))).show()

// add new column with year transformation
val df2 = df.withColumn("Year", year(df("Date")))
// group by newly created year column
val dfavgs = df2.groupBy("Year").mean()

dfavgs.select($"Year", $"avg(Close)").show()
