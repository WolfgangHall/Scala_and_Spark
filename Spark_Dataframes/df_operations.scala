import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
// chain options via builder
val df = spark.read.option("header", "true").option("inferSchema", "true").csv("CitiGroup2006_2008")

df.printSchema()

// filter data
// allows you to use $ scala notation
import spark.implicits._

df.filter($"Close" > 480).show()
// SQL notation, passing in where clause
df.filter("Close > 480")

// filter multiple columns
df.filter($"Close" < 480 && $"High" < 480).show()
df.filter("Close < 480 AND High < 480").show()

// collect results into a scala object
// you can then do what you want with this array
val sample = df.filter($"Close" < 480 && $"High" < 480).collect()

// aggregate function
val sample_count = df.filter($"Close" < 480 && $"High" < 480).count()


// filter for equality
// need triple equals for scala notation
df.filter($"High" === 484.40).show()
df.filter("High = 484.40").show()


// calculate pearson correlation
df.select(corr("High", "Low")).show()
