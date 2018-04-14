import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

// chain options via builder
val df = spark.read.option("header", "true").option("inferSchema", "true").csv("CitiGroup2006_2008")

//df.head(5)

// returns column names in an array
df.columns

for (row <- df.head(3))
{
    println(row)
}

// provides statistical information
df.describe().show()

// select one column
df.select("Volume").show()

// select multiple columns
df.select($"Date", $"Close").show()

// create new column
// first arg -> new column name
// second arg -> new column values
df.withColumn("HighPlusLow", df("High")+df("Low"))

// can be saved into a variable
val df2 = df.withColumn("LowPlusHigh", df("Low")+df("High"))
df2.printSchema()

// rename a column
// like SQL 'as' aliasing
df2("LowPlusHigh").as("LPH")
