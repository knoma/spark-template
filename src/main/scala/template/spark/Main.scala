package template.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, LogManager, Logger}


final case class Person(firstName: String, lastName: String,
                        country: String, age: Int)

object Main {
  def main(args: Array[String]) = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("spark pika")
      .config("spark.cassandra.connection.host", "localhost")
      .getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)
    LogManager.getRootLogger.setLevel(Level.ERROR)

    val version = spark.version
    println("SPARK VERSION = " + version)

    val sumHundred = spark.range(1, 101).reduce(_ + _)
    println(f"Sum 1 to 100 = $sumHundred")

    println("Reading from csv file: people-example.csv")
    val persons = spark.read
      .option("header", true)
      .option("inferSchema", true)
      .option("mode", "DROPMALFORMED")
      .csv("people-example.csv").as[Person]
    persons.show(2)
    val averageAge = persons.agg(avg("age"))
      .first.get(0).asInstanceOf[Double]
    println(f"Average Age: $averageAge%.2f")

    spark.close()
  }
}
