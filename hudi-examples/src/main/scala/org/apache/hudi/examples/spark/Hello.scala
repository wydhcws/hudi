package org.apache.hudi.examples.spark
// spark-shell
import org.apache.hudi.QuickstartUtils._

import scala.collection.JavaConversions._
import org.apache.spark.sql.SaveMode._
import org.apache.hudi.DataSourceReadOptions._
import org.apache.hudi.DataSourceWriteOptions._
import org.apache.hudi.config.HoodieWriteConfig._
import org.apache.spark.sql.SparkSession

object Hello {
  def main(args: Array[String]): Unit = {

    val tableName = "hudi_trips_cow"
    val basePath = "file:///tmp/hudi_trips_cow"
    val dataGen = new DataGenerator

    val spark = new SparkSession()
    // spark-shell
    val inserts = convertToStringList(dataGen.generateInserts(10))
    val df = spark.read.json(spark.sparkContext.parallelize(inserts, 2))
//    df.write.format("hudi").
//      options(getQuickstartWriteConfigs).
//      option(PRECOMBINE_FIELD_OPT_KEY, "ts").
//      option(RECORDKEY_FIELD_OPT_KEY, "uuid").
//      option(PARTITIONPATH_FIELD_OPT_KEY, "partitionpath").
//      option(TABLE_NAME, tableName).
//      mode(Append).
//      save(basePath)

    df.write.csv(basePath)
    spark.read.text(basePath)


  }
}
