package cats

import java.sql.{Connection, DriverManager, Statement, ResultSet}
import java.time.Instant.now
import java.util.UUID.randomUUID
import scala.collection.mutable.Queue

object Config {
    val MOODCHANGETIMEINMS = 27000 // 27 sec
    val TOTALNUMOFCATS = 1000
    val RUNNINGTIMEINMS = 86400000 // 1 day
    val DATABASEURL = "jdbc:mysql://localhost:3306/mydb"
    val DATABASEDRIVER = "com.mysql.cj.jdbc.Driver"
    val DATABASEUSERNAME = "root"
    val DATABASEPASSWORD = "example"
}

object Mood extends Enumeration {
    type Mood = Value

    val DEFAULT        = Value("Miaw")
    val GROWL          = Value("grr")
    val HISS           = Value("kssss")
    val PURR           = Value("rrrrr")
    val THROWGLASS     = Value("cling bling")
    val ROLLONFLOOR    = Value("fffff")
    val SCRATCHCHAIRS  = Value("gratgrat")
    val LOOKDEEPINEYES = Value("-o-o-___--")
}

import Mood._

class Database {
    var connection: Connection = _

    def connect(): Unit = {
        try {
            Class.forName(Config.DATABASEDRIVER)
            connection = DriverManager.getConnection(Config.DATABASEURL, Config.DATABASEUSERNAME, Config.DATABASEPASSWORD)
            val numOfConnections = Config.TOTALNUMOFCATS + 100

            setMaxConnections(numOfConnections)
        } catch {
            case e: Exception => e.printStackTrace()
        }        
    }

    def getConnection(): Connection = {
        connection
    }

    def setMaxConnections(numOfConnections: Int): Unit = {
        val query: String = s"SET GLOBAL max_connections = $numOfConnections"

        execute(query)
    }

    def insertMood(timestamp: BigInt, id: String, mood: Mood): Unit = {
        val query: String = s"INSERT INTO `mydb`.`changedMoods` (`timestamp`, `id`, `mood`) VALUES ('$timestamp', '$id', '$mood')"

        execute(query)
    }

    def execute(query: String): Unit = {
        try {
            val statement: Statement = connection.createStatement()
            val result = statement.execute(query)

            statement.close()
        } catch {
            case e: Exception => e.printStackTrace()
        }
    }

    def close(): Unit = {
        connection.close()
    }
}

class Cat {
    private val moodChangeTimeInMs = Config.MOODCHANGETIMEINMS
    private val id = randomUUID().toString()
    private val db = new Database()
    private val timer = new java.util.Timer()
    private val task = new java.util.TimerTask {
        def run() = {
            changeMood()
        }
    }

    db.connect()
    timer.schedule(task, moodChangeTimeInMs, moodChangeTimeInMs)
    private var mood = DEFAULT

    def cancelTimer(): Unit = {
        timer.cancel()
        db.close()
    }

    def changeMood(): Unit = {
        var newMood = Mood(scala.util.Random.nextInt(Mood.maxId))

        while (mood == newMood) {
            newMood = Mood(scala.util.Random.nextInt(Mood.maxId))
        }
        mood = newMood
        db.insertMood(now.getEpochSecond, getId(), mood)
    }

    def getId(): String = {
        id
    }

    def getMood(): Mood = {
        mood
    }
}

object Main {
    def main(args: Array[String]) {
        val totalNumOfCats = Config.TOTALNUMOFCATS
        var cats = Queue[Cat]()
        val startTimestamp = now.getEpochSecond

        while(cats.length < totalNumOfCats) {
            cats.enqueue(new Cat())
        }
        
        Thread.sleep(Config.RUNNINGTIMEINMS)

        cats.foreach((cat) => {
            cat.cancelTimer()
        })
        val endTimestamp = now.getEpochSecond

        println(s"\n\nStart timestamp: $startTimestamp \nEnd timestamp: $endTimestamp \n")

        val db = new Database()
        db.connect()
        val connection = db.getConnection()
        val statement: Statement = connection.createStatement()
        val result = statement.executeQuery(s"SELECT mood, count(*) AS count FROM mydb.changedMoods WHERE timestamp >= $startTimestamp AND timestamp <= $endTimestamp GROUP BY mood ORDER BY count DESC")
        val numOfChangedMoods = Config.TOTALNUMOFCATS * (Config.RUNNINGTIMEINMS / Config.MOODCHANGETIMEINMS).floor
        val padding = "| %1$-11s | %2$-10s | %3$-10s | %4$-10s | %5$-10s |"

        println("-" * 67)
        println(padding.format("Mood", "Count", "Average", "Median", "Variance"))
        println("-" * 67)
        while (result.next()) {
            var mood = result.getString("mood")
            var count = result.getString("count").toInt
            var average = count / numOfChangedMoods
            var median = "" // TODO
            var variance = "" // TODO

            println(padding.format(mood, count, average, median, variance))
        }
        println("-" * 67)

        statement.close()
        db.close()
    }
}
