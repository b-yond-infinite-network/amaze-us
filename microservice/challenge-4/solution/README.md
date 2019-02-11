# How to run

1. spinup postgres database
```
$> docker-compose up -d
```

2. start Cat application
```
$> sbt "runMain com.regisleray.CatApp"
```

`run 1000 cats in parallel and persist state every 27 seconds`

the application can scale vertically (more cats) and also horizontally since
we can spawn multiple instance of the same program.
Since we rely on postgres upsert query every record is atomic and support concurrent operation


3. start Stats application
```
$> sbt "runMain com.regisleray.StatApp"
```

`query database table "moods" to compute stats(average / median / variance) every seconds`



#### Challenge 4 - Mysteries of the Cats

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
