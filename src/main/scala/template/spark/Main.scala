package template.spark

import com.datastax.driver.core.Session
import com.datastax.spark.connector._
import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, LogManager, Logger}
import com.datastax.spark.connector.cql.CassandraConnector


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

    val sc = spark.sparkContext

    val rows = sc.cassandraTable("cycling", "cyclist_name")
      .filter(r => r.getString("firstname")=="Steven")
    println(rows.count())
    println(rows.partitions.size)

    val connector = CassandraConnector.apply(sc.getConf)

    rows.foreachPartition(partition => {
      val session: Session = connector.openSession()
      partition.foreach{elem =>
        val delete = s"DELETE FROM cycling.cyclist_name where id=${elem.getString("id")};"
        println(delete)
        session.execute(delete)
      }
      session.close()
    })

    spark.close()
  }
}
