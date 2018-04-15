import org.apache.spark.ml.regression.LinearRegression

import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

import org.apache.spark.sql.SparkSession

import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

val spark = SparkSession.builder.getOrCreate()

val data = (spark.read.
    option("header", "true").
    option("inferSchema", "true").
    format("csv").
    load("Clean-Ecommerce.csv"))

data.printSchema

val datcols = data.columns
val row1 = data.head(1)(0)

for (item <- Range(0, datcols.length))
{
    println(datcols(item))
    println(row1(item))
}


val df = (data.select(data("Yearly Amount Spent").as("label"),
$"Avg Session Length", $"Time on Website", $"Length of Membership"))


val assembler = ( new VectorAssembler().
setInputCols(Array(
    "Avg Session Length", "Time on Website", "Length of Membership")).
    setOutputCol("features") )


val ouput = assembler.transform(df).select($"label", $"features")


val lr = new LinearRegression()

val lrModel = lr.fit(output)

println(s"Coeff: ${lrModel.coefficients}, intercept: ${lrModel.intercept}")

val trainingSummary = lrModel.summary
trainingSummary.residuals.show()
trainingSummary.rootMeanSquaredError
trainingSummary.meanSquaredError
trainingSummary.r2
