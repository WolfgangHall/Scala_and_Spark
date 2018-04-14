import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema", "true").csv("ContainsNull.csv")

df.show()

df.printSchema()

// drops any row that has any null values in the column
df.na.drop().show()
// drops rows with columns containg 2 or more missing values
df.na.drop(2).show()

// fills null numerical values with the specified value
df.na.fill(100).show()
// fills null string values with the specified value
df.na.fill("Missing Name").show()

// specify the name of the column that you want to manipulate
df.na.fill("New Name", Array("Name")).show()
df.na.fill(200, Array("Sales")).show()


// grab mean value from the describe()
val df2 = df.na.fill(400.5, Array(Sales))
df2.na.fill("missing name", Array("Name")).show()
