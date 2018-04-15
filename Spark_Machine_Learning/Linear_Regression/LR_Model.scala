import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql._
import org.apache.spark.sql.types._


import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

val spark = SparkSession.builder().getOrCreate()


// workaround used because inferschema was not accepting first two columns as doubles
val customSchema = StructType(Array(
    StructField("Avg Area Income", DoubleType, nullable=false),
    StructField("Avg Area House Age", DoubleType, nullable=false),
    StructField("Avg Area Number of Rooms", DoubleType, nullable=false),
    StructField("Avg Area Number of Bedrooms", DoubleType, nullable=false),
    StructField("Area Population", DoubleType, nullable=false),
    StructField("Price", DoubleType, nullable=false),
    StructField("Address", StringType, nullable=false)
))

val rowData = spark.read.textFile("USA_Housing.csv")
val data = spark.read.schema(customSchema).csv(rowData)

data.printSchema


// set up dataframe for machine learning
// ("label", "features") -> features are an array of values
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

// label column is generated in the new df
val df = (data.select(data("Price").as("label"),
$"Avg Area Income", $"Avg Area House Age", $"Avg Area Number of Rooms", $"Avg Area Number of Bedrooms", $"Area Population"))


// convert into Vector Array called features
val assembler = (new VectorAssembler().setInputCols(
    Array("Avg Area Income",
    "Avg Area House Age",
    "Avg Area Number of Rooms",
    "Avg Area Number of Bedrooms",
    "Area Population")).setOutputCol("features"))

//
val output = assembler.transform(df).select($"label", $"features")
