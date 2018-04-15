import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.Pipeline
import org.apache.spark.mllib.evaluation.MulticlassMetrics

import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

val spark = SparkSession.builder.getOrCreate()

val data = ( spark.read.option("header", "true").
option("inferSchema", "true").
format("csv").
load("titanic.csv") )

data.printSchema

val datacol = data.columns
val firstrow = data.head(1)(0)
println("\n")

for (item <- Range(0, datacol.length))
{
    println(datacol(item))
    println(firstrow(item))
}


val logregdataall = (data.select(data("Survived").as("label"),
$"Pclass", $"Name", $"Sex", $"Age", $"SibSp",
 $"Parch", $"Fare", $"Embarked") )

// drop columns with null values
val logregdata = logregdataall.na.drop()

// convert string values to numerical values
val genderIndexer = new StringIndexer().setInputCol("Sex").setOutputCol("SexIndex")
val embarkIndexer = new StringIndexer().setInputCol("Embarked").setOutputCol("EmbarkIndex")

// convert numerical values to OneHotEncoding (0, 1)
val genderEncoder = new OneHotEncoder().setInputCol("SexIndex").setOutputCol("SexVec")
val embarkEncoder = new OneHotEncoder().setInputCol("EmbarkIndex").setOutputCol("EmbarkVec")


val assembler = ( new VectorAssembler().
setInputCols(Array("Pclass", "SexVec", "Age", "SibSp", "Parch", "EmbarkVec", "Fare")).
setOutputCol("features"))

// split up data with training and test
val Array(training, test) = logregdata.randomSplit(Array(0.7, 0.3), seed=12345)

// create a Pipeline
val lr = new LogisticRegression()

val pipeline = ( new Pipeline().
setStages(Array(genderIndexer, embarkIndexer,
          genderEncoder, embarkEncoder,
          assembler, lr))
)


val model = pipeline.fit(training)

val results = model.transform(test)

val predictionAndLabels = results.select($"prediction", $"label").as[(Double, Double)].rdd

val metrics = new MulticlassMetrics(predictionAndLabels)

println("Confusion matrix: ")
println(metrics.confusionMatrix)
