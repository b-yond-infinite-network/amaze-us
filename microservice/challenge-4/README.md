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

---

# Caveats
* I took this challenge as an opportunity to experiment with the Akka framework. It's the first time 
I'm using it and I haven't read the entire documentation yet, so it's possible the architecture is 
not optimal.

* The description did not specify the probabilities for choosing a new mood, and it also mentions a 
default mood, but doesn't specify in what way it is different from the others. I decided to use the 
following rules:
  * There's a 50% that the current mood will be maintained.
  * If the current mood is not maintained and is **not** the default mood, the new mood will be the 
  default mood.
  * If the current mood is not maintained and **is** the default mood, the new mood is chosen from 
  among the 7 others using equal probabilities.

* I couldn't actually generate scaladoc, because it seems that some change in version 2.13+ of 
Scala broke the support from the `scaladoc` task on Gradle's scala plugin. There could be errors in 
the scaladoc then, but it still serves as documentation.

## Interpretation
I thought that the challenge description was very vague and unclear about what was being asked. I'm 
assuming that the first step is to create a dataset from the changes of state of 1000 "cats", 
which will change state 3,200 times (number os seconds in a day divided by 27) from among the 8 
listed states. Then, the next step is to obtain some statistical metrics from this data (mean, 
median and variance).

The first step is straightforward, but it seems strange that this is stated as a parallel 
computing challenge (from 
[this description](https://github.com/joaogsma/amaze-us/tree/master/microservice#-challenge-4---mysteries-of-the-cats)), 
because the "cats" don't interact. The second step is more confusing. The recorded states (cat moods) are not numbers, so statistical 
mean and variance don't apply. Also, they don't have an ordering, so the median doesn't 
apply as well. I couldn't think of any metrics to compute, so I only implemented a histogram.
