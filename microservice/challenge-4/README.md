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


## How to run the apps:
+ Setup a Kafka server on localhost:9092 
+ Create topic "cats"
+ In amaze-us\microservice\challenge-4\cats 
	- Launch the producer with: sbt runMain example.CatsProducerApp
	- Launch the consumer with: sbt runMain example.CatsConsumerApp

## Comments:
This was a fun exercise and a good opportunity for me to learn about the SMACK stack (or at least get hands-on with Kafka and Spark). 
This was also my first time using sbt as a build tool (instead of gradle), and I ended up spending quite a bit of time 
fiddling around with dependencies. In the end, I still had to manually add the jars for streaming-kafka-0-10 since sbt did 
not seem to able to resolve it. 

If I had more time, I would have tried to implement more parts of the SMACK stack and would've tried to design something along the lines of:
Live data feed -> Kafka -> Spark Streaming -> Cassandra -> Reactive Akka App


