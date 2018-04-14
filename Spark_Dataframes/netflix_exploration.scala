import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema", "true").csv("Netflix_2011_2016.csv")

// return array of column names
df.columns

// return the data schema
df.printSchema()

// print out the first few columns
df.head(5)

// stats info about the data
df.describe().show()

// create new column with ratio between high and volume
val df2 = df.withColumn("HV Ratio", df("High")/df("Volume"))

// the day with the highest peak price
df.orderBy($"High".desc).show(1)

// the mean of the close column
df.select(mean("Close")).show()

// max and min of the volume column
df.select(max("Volume")).show()
df.select(min("Volume")).show()


// import implicits to use scala $ syntax for filtering
import spark.implicits._

// number of days the close price was less than $600
df.filter("Close < 600").count()
df.filter($"Close" < 600).count()

// percentage of time the high was greater than $500
// multiply first value by 1.0 to convert to Double
(df.filter("High > 500").count()*1.0 / df.count()) * 100

// pearson correlation between high and Volume
df.select(corr("High", "Volume")).show()

// max per Year
val yearsdf = df.withColumn("Year", year(df("Date")))
val yearmaxvals = yearsdf.select($"Year", $"High").groupBy("Year").max()
yearmaxvals.select($"Year", $"max(High)").show()

// average close per month
val monthsdf = df.withColumn("Month", month(df("Data")))
val monthavgs = monthsdf.select($"Month", $"Close").groupBy("Month").mean()
monthavgs.select($"Month", $"avg(Close)").show()
