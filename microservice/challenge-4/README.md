# Challenge 4 - Mysteries of the Cats

## Plan
1. To create a **Config** object holding all configuration parameters
2. To create a **Mood** object holding all moods as per the description
3. To create a **Database** class responsible for the communication with the MySQL server
4. To create a **Cat** class holding the functionality related to a single cat
5. To create a **Main** object launching the whole functionality and showing the stats after the simulation

## Data representation

### Configuration parameters
```scala
object Config {
    val MOODCHANGETIMEINMS = 27000 // 27 sec
    val TOTALNUMOFCATS = 1000
    val RUNNINGTIMEINMS = 86400000 // 1 day
    val DATABASEURL = "jdbc:mysql://localhost:3306/mydb"
    val DATABASEDRIVER = "com.mysql.cj.jdbc.Driver"
    val DATABASEUSERNAME = "root"
    val DATABASEPASSWORD = "example"
}
```

### Database create statement
```sql
CREATE TABLE IF NOT EXISTS `mydb`.`changedMoods` (
  `idchangedMoods` INT NOT NULL AUTO_INCREMENT,
  `timestamp` BIGINT NOT NULL,
  `id` VARCHAR(45) NOT NULL,
  `mood` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idchangedMoods`))
ENGINE = InnoDB
``` 

### Available moods
```scala
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
```

## How it's working
The idea is to have a single cat fully isolated from other cats. Each cat changes its mood based on its own timer. Each cat also has a dedicated connection to the database.

When you run the project the required number of cats will be created. Once instantiated they will begin changing their moods as per **Config.MOODCHANGETIMEINMS** parameter and record the changes into a MySQL table. That will continue until the **Config.RUNNINGTIMEINMS** time passes. As there are no destructors in Scala the **cancelTimer()** method will be taking care of the cleanup of created resources and desactivating 'live' cats. The method will cancel the timer and will close the connection to the MySQL server related to each cat.

Once the process of running all cats is over a select statement will be executed against the `mydb.changedMoods` table to group, order and count the number of occurences of all moods for the specified time frame.

## How to run it
+ Into the challenge-4 folder run `docker-compose -f stack.yml up` to pull, create and run a container holding a MySQL server and an Admin panel. The server is accessible at `localhost:3306` with `root/example` as username/password. The Admin panel is accessible at `http://localhost:8080/` with `root/example` as username/password.
+ I'm using [sbt](https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Linux.html) to compile and run the project, so you could launch it by issuing `sbt compile` or `sbt run` from command line inside the challenge-4 folder.

## Notes
+ As I mentioned during the onsite intervew I don't have any experience with Scala, but really wanted to experiment and play with the language.
+ I faced some technical challenges that I resolved by relying on my experience in other programming languages, so probably some parts look a little strange, but everything was thoroughly tested and working :-)
+ I did many tests, and the longest one covered the case with 1000 cats changing their moods every 27 seconds for one hour.
+ At the top of the file there are the configuration parameters with values set as per the challenge description. I don't think you really want to run the simulation for 24 hours, so I'm suggesting you to change at least the RUNNINGTIMEINMS parameter to something meaningful. 

## TODO
+ Median and variance calculations
