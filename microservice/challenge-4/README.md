# Challenge 4 - Mysteries of the Cats

## Problem
Discovery is upon us! The mystery has to be solved! 
After years of research, century of mysticism, we have it.

Here is the truth, we can say with certainty that cats have 7 moods:
```scala
  val GROWL         = "grr"
  val HISS          = "kssss"
  val PURR          = "rrrrr"
  val THROWGLASS    = "cling bling"
  val ROLLONFLOOR   = "fffff"
  val SCRATCHCHAIRS = "gratgrat"
  val LOOKDEEPINEYES = "-o-o-___--"
``` 
Not counting of course, their default "Miaw" mood.
We are on mission. So should you!

First, let's modelize our cats in Scala.
To match reality, those cats should randomly change mood every 27 seconds.
Now, let's simulate a thousand of those during a day.
We will need then to store all of those different cat moods.

Once that's done, we will at last be able to generate some statistics: average, median, variance of the different moods 
over time, anything that can help us pierce the secret.
This of course should scale orthogonally to the number of cats we are simulating, we don't want to end up with angry 
cats, right?

Make sparks, solve the mystery!


## WIP Solution
### Basic Idea 

From the sound of it, what we need is a system of publishers who send messages to another 
system that should be able to support consuming a high number of them.
Consumed messages can be either be processed with streaming for real-time reporting 
or be written into a database for batch processing.

This can be modelled with actors publishing to a broker such as Kafka where incoming messages
can be fed into either Spark directly or a database that's a good fit for extensive writes 
(e.g. Cassandra).

### Modules
There are 3 SBT projects here (everything is done in Scala with some rust [not Rust!] mixed in):
- **catz-app** to contain our cat overlords (which I, for one, welcome) 
- **catz-broker** is basically the project wrapping Kafka (and Spark streaming, if we were to use it)
- **catz-analytics** would just contain batch processing code. This was done last and 
although it's really trivial, my machine doesn't like it for some reason... either way,
after you get the data in Cassandra, we can get the Spark magicians out of their lab 
to do their thing I'm sure!

So what happens is that Cat actors get a command every X milliseconds to change their
mood and blast messages to Kafka, which picks them up and simply saves them
to Cassandra. Easy peasy, except when you lose a million years trying to figure
out why producers can't connect to Kafka once inside Docker or what @$&#%^!
implicits you need to import for the thing to compile.

### Setup
You will need `sbt`. If you have that, then it's more or less a piece of cake (until it isn't...)

```bash
cd catz-app
sbt docker

cd ../catz-broker
sbt docker

docker-compose up -d
# takes some seconds for containers to be "stable"
# this is your cue for a break


# ok, soon enough Cassandra should have data
docker exec -it cassandra /bin/bash

# (in the belly of the beast)
cqlsh
use catz;
select * from cat_moods;
```

None of the config changes I tried made Spark stop logging a ton by the way.

I hinted at this above: the "analytics" standalone app is having issues on my machine 
I wasn't able to resolve in time but they do look like setup/dependency issues. 
You might be luckier!

Most of my time was spent figuring out how to wire things into one another as I had
never used Kafka, Spark and Cassandra prior to this and my Scala + Akka was rusty. 
On that note, implicits can be pure evil.

### Tests

Besides the two trivial tests for Cat actors I have in their app, I don't see 
much value for tests here but that is mainly because I couldn't get Spark to work properly.
More complicated scenarios would make tests valuable.


### TODO List
0. Fix the stats app. I have a hard time believing there is something wrong with the 
actual code there...

1. This is obviously (?) the equivalent of what would be a PoC in a real setting. 
To get this to production-level (setting up clusters etc. etc.) a lot more work 
would be required but first of all I would have to read up on things I have used 
with which I'm not familiar enough.

2. Centralize the build configuration and create a root-level project to house common types etc.
(e.g. the `Config` traits)

3. A front end to look at the stats produced by Spark on top of an infinite stream of 
cat pictures would be nice

### Finally
![blurry_cat.jpg](https://pics.me.me/thumb_cat-falling-off-a-table-42299280.png)

Obviously given the problem domain, the canon implementation would have to be in [LOLCODE](http://www.lolcode.org)...

Sorry for not doing more but I tried my best with the time I had. In all honesty, I learned a lot. Cheers!