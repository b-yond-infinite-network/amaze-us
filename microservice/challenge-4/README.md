# Challenge 4 - My travel into the Cats' mystery
My version is a simple version that works. My objective was to provide a functional project and then improve it, iteratively.
In four days, I achieved a version with :
+ Simulator in **Scala** : the file *src/main/scala/Main.scala* defines the simulation. Then it can be built and run 
with **sbt**. It was my first project in Scala. I discovered **Akka Actors**, an amazing library to create parallel Scala
applications.
+ Database : I got some issues setting up Cassandra database (driver to execute queries from Scala, the environment 
with Spark). Thus I did the simplest version of a database, a CSV file per simulation (as a local database). At the beginning
my intention was to use Cassandra to handle a big amount of data and proceed, in a later step, data streaming.
+ Statistics : I set up a Jupyter Notebook (in *src/main/python*) because I think it is a wonderful tool to interact with
data : we can load data in one cell and then manipulate it without re-loading it. Moreover, Python has very useful libraries
such as Pandas, Numpy and Plotly. I upload a HTML version of the execution **Statistics.html** of the Jupyter Notebook to
give you on overview of the obtained results.

Some examples of data are available in the *data/* directory (https://drive.google.com/drive/folders/11oDfZjbUPSd-AsQJmtdcbVW_rwlPEjFe?usp=sharing). The smallest one contains 13,300 rows (100cats27sec1hour),
the biggest contains 3,600,000 rows (1000cats1sec1hour).

My discoveries
--------------
+ Scala
+ Akka Actors
+ Cassandra/Spark (even if it is not released in my submission, I need some more time to fully understand it)

Performance
-----------
Akka Actors is very powerful : my Actor System can manage 10.000 cats with a 1 second mood cycle. The library deals with
the machine properties, such as the number of processors. It is much more efficient than using independent threads because
the messaging system enables passive waiting : the process is waken up only when it receives a message.

For the Statistics part, I essentially use pandas.DataFrame. The code is quite simple and easy to understand but I really
thought about each operation to avoid using for loops (very time consuming with large matrices). I timed and optimized the
different operations both by mathematical thinking (see the process of calculation) and a knowledge about *economic* ways.
The table below show the time spent by block of code. This is a one-shot time, so it can vary. About the scalability,
we can see that the ratio time/rows is slightly decreasing, and the process for 3M rows (approximately the amount of data
for a one day simulation with a thousand cats) is executed in a decent time. It is only batch version for now.

|                 | 13,300 rows | 360,000 rows | 3,600,000 rows |
|:---------------:|-------------|--------------|----------------|
| Load data       | 31.9 ms     | 288 ms       | 3.01 s         |
| new_data        | 16.6 ms     | 119 ms       | 983 ms         |
| prev_data       | 13 ms       | 138 ms       | 931 ms         |
| mood_mvt_data   | 0.997 ms    | 1.99 ms      | 1 ms           |
| data            | 0.987 ms    | 0.998 ms     | 0.999 ms       |
| graph 1         | 186 ms      | 419 ms       | 1.5 s          |
| avg_data        | 12 ms       | 16 ms        | 8.98 ms        |
| graph 2         | 185 ms      | 934 ms       | 869 ms         |
| var_data        | 9.97 ms     | 18.9 ms      | 13 ms          |
| graph 3         | 455 ms      | 987 ms       | 705 ms         |
| graph 4         | 150 ms      | 279 ms       | 328 ms         |
| Total           | ~1.06 s     | ~3.2 s       | ~8.3 s         |
| Ratio time/rows | ~7,9e-5     | ~8.8e-6      | ~2.3e-6        |

About the database, I considered that it was a Big Data database so I decided to add the previous mood of the cat to
compute more efficiently the statistics. Indeed, it has a memory cost.

Ideas for the next steps
------------------------
I think the first thing would be to achieve using Cassandra/Spark. I already designed a Cassandra scheme. Then, with these
tools, we can pass to a Streaming process with a Kafka pipeline to feed the database through Spark Streaming. Moreover,
it would led to a near real-time processing. So it would be necessary to change the statistics process with iterative
formulas.

Moreover, I proceed manually the tests; I execute and checked by hand the results : counting the rows to check all the cats
are displayed for example. An improvement should be to create programmatic tests.

# Challenge 4 - Mysteries of the Cats (original post)
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