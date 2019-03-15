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


## Solution

Provided solution consists of two programs: observation of moods and reporting of statistics.

### Key features

1. Due to the long-running nature of the task, applications operate in constant memory space.
   Everything is done with streaming internally.
2. Applications work with local files as a mean to persist/consume data. It reads/writes header-less csv.
3. It is implemented with bare scala/scalaz,
   there are no external requirements except JVM itself.
4. No cats were harmed during the work on the project.
5. It has been conducted that cats have 8 moods in total: the default "Miaw" can be observed.
 
### Running / Testing

This is a regular SBT project and SBT is the easiest way to work with it.
SBT is [here](https://www.scala-sbt.org/download.html) if needed.

Navigate to `amaze-us/microservice/challenge-4` and run

 - `sbt test` to compile and test the project.

 - `sbt "runMain com.test.cats.Observation 20 2.minutes"` to start observing 20 cats for 2 minutes.
   `2000` cats have been tested too, and `1.day` duration can be specified, 
   although app will indeed take 1 day to complete.
   Data is saved to OS temp directory, and app tells where exactly upon completion:
   
```
Saved to /var/folders/0x/zwhvfqvx2zqfb5zz6p6h_6pr3qzk3d/T/cats-observation-1552662326833
```

 - `sbt "runMain com.test.cats.Statistics /var/folders/0x/zwhvfqvx2zqfb5zz6p6h_6pr3qzk3d/T/cats-observation-1552662326833"`
   to compute stats and print them:

```
 Number of known moods: 8
 Total number of observed moods:           120
 Cats are in each mood on average (times): 15.00000
 Median value across all mods (times):     15.5
 Mood variance:                            56.00000
```
 