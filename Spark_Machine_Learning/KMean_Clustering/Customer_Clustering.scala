import org.apache.spark.sql.SparkSession

import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

val spark = SparkSession.builder().getOrCreate()

import org.apache.spark.ml.clustering.KMeans

val data = spark.read.option("header", "true").option("inferSchema", "true").csv("Wholesale customers data.csv")

data.printSchema


val feature_data = data.select($"Fresh", $"Milk", $"Grocery", $"Frozen", $"Detergents_Paper", $"Delicassen")


import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

// no use of a label column
val assembler = new VectorAssembler().setInputCols(Array("Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper", "Delicassen")).setOutputCol("features")


val training_data = assembler.transform(feature_data).select("features")

for (num <- Range(2, 10))
{
    val kmeans = new KMeans().setK(num)

    val model = kmeans.fit(training_data)

    val WSSSE = model.computeCost(training_data)

    println(s"Within Set Sum of Squared Errors for k = ${num}: ${WSSSE}")
}
