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

## Proposed solution overview

Cats had enough of their master ignoring them and spending their time on social networks instead.
They decided to come up with their own alternative to be able to share their mood publicly as well.

+ Generate a configurable pool of cats (default 1000)
+ Have each cat tweeting its mood to Kafka every 27s (message include name/mood + implicitly timestamp)
+ Leverage Spark structured streaming with Kafka to extract cumulated/windowed mood trends from the tweets
https://spark.apache.org/docs/2.3.0/structured-streaming-kafka-integration.html

## Running the code

Requirements : Maven 3.5.x, JDK8, Docker Compose, Spark 2.3.0

+ Go the `amaze-us/microservice/challenge4` directory
+ Bring up Kafka/Zookeeper using `docker-compose up -d`
+ Run `mvn clean install exec:exec -Dspark.installation.path=/home/wherever/is/spark`
+ Enjoy the console slideshow!

## Next steps
+ Have cat management/publishing to Kafka clearly isolated from the data analysis / Spark layer (2 apps)
+ Leverage Akka Persistence, no innocent cat should ever die no matter its current mood
+ Give cats proper names
+ Do it in Scala. Definitely.

