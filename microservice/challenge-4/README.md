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

# Cats and Moods

## Create infra

```
docker run --name cassandra -p 9042:9042  -d cassandra:3.11.2
docker run -it --link cassandra:cassandra -v $(pwd)/microservice/challenge-4/src/main/resources:/scripts  --rm cassandra:3.11.2 cqlsh cassandra -f /scripts/cats-and-moods.cql
```

## Run the generator - using actors

launches an app that doesn't stop generating every 27 seconds a new mood for each of the 1000 cats and registers it to cassandra

```
sbt 'run-main com.catsandmoods.CatMoodGeneratorAkka'
```

## Run the generator - using spark
launches a spark job that register to cassandra a cartesian product between an rdd of cats and a rdd of times - each multiple of 27 seconds starting from the beginning of the day.

```
sbt 'run-main com.catsandmoods.CatMoodGeneratorSpark'
```

## Run the report

```
sbt 'run-main com.catsandmoods.Reporting'
```
