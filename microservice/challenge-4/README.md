# Challenge 4 - Mysteries of the Cats

Discovery is upon us! The mystery has to be solved! 
After years of research, century of mysticism, we have it.

Here is the truth, we can say with certainty that cats have 7 moods :
```scala
  val GROWL         = "grr"
  val HISS          = "kssss"
  val PURR          = "rrrrr"
  val THROWGLASS    = "cling bling"
  val ROLLONFLOOR   = "fffff"
  val SCRATCHCHAIRS = "gratgrat"
  val LOOKDEEPINEYES = "-o-o-___--"
``` 
Not counting of course, there default "Miaw" mood.
We are on mission. So should you!

First, let's modelize our cats in Scala.
To match reality, those cats should randomly change mood every 27 seconds.
Now, let's simulate a thousand of those during a day.
We will need then to store all of those different cats mood.

Once that's done, we will at last be able to generate some statistics: average, median, variance of the different mood 
over time, anything that can help us pierce the secret.
This of course should scale orthogonally to the number of cats we are simulating, we don't want to end up with angry 
cats, right ?

Make sparks, solve the mystery!


## Expected steps
+ Create a branch of this project (or fork it in your github account if you prefer)
+ Do you **_thang_** inside this folder (challenge-4)
+ Push your change inside a Pull Request to our master

## Solution overview

Due cats are not hard to get angry, the best choice is to use a micro sensor to collect a publish their moods a regular time.

+ Modelling Cats on Scala, Sensor moods on Akka and publish to Kafka,
+ Schedulle to obtain moods every 27s and publish to Kafka,
+ Use Spark streaming to consume all mood messages from Kafka,
+ Implement a UADF to compute median,
+ To facilite the execution of the solution, we use docker-compose container that includes: JDK, Zookeeper and Kafka, defined on *docker-compose.yml*
+ Application settings was configured on *resources/application.conf*, for example: kafka configuration, batch duration, interval time to scan cats, num of cats to sense.

## Execute the solution

Requirements: Scala 2.12, Docker, Docker-compose, Spark-2.2.0, JDK8

+ Go to *amaze-us/microservice/challenge4* directory
+ Build assembly *sbt assembly*
+ Copy *deploy.sh* file to *build* directory
+ Go to *build* directory
+ Download spark distribuition *wget https://d3kbcqa49mib13.cloudfront.net/spark-2.2.0-bin-hadoop2.7.tgz*
+ Unpack it *tar -xzf spark-2.2.0-bin-hadoop2.7.tgz*
+ Remove archive file
+ Start up docker-composer (Kafka/Zookeeper) *sudo docker-compose run --rm --service-ports java*, sudo if is required
+ Now on java container, execute *./deploy.sh*

After that you should see output like:

```
+------------------+------------------+------+------------------+
|           Average|              Mean|Median|          Variance|
+------------------+------------------+------+------------------+
|157.14285714285714|157.14285714285714| 163.0|125.80952380952381|
+------------------+------------------+------+------------------+
```

### Next steps

+ Split microservice in two apps 1) to collect and publish cats moods and 2) to consume and analyzed the data.
+ Simplified running process
+ Configure deploy.sh to to include log4.properties on spark-submit
+ Added Test Cases
+ Use another docker image that contains a Spark distribuition
