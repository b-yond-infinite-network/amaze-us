# Cat Simulation
I decided to use fs2 for the first time in order to solve this exercise as it seemed to fit the use case very well. The code is mostly self explainatory, I created a stream of moods based on the number of cats and zipped it with a stream of time in order to simulate the moods over time which are saved to a csv file. Then after that I just read the results and calculate the stats.

Use `sbt run` to run the program.
