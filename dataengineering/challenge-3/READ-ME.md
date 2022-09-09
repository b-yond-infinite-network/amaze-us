# Challenge 3 - EvilNet rules the world
It’s year 2050, cities are now evaluated based on their citizens social media feeds 😱

You have been hired at EvilNet, the corporation controlling the world, to provide realtime social media insights.

Part of your first day at the job you were asked to build a realtime data pipeline that generates the following metrics for Canadian cities only.
1. Total tweets per city over 5 minutes
1. Total of retweets per city over 5 minutes
1. Count of active tweeters per city over 5 minutes
The aggregated data should be made available for reporting with a retention of 1 day.

To showcase your mastery of this domain, you will provide the ability to **reprocess** data over the past hour as well, just in case EvilNet requires an additional insight.

To make EvilNet happy you will provide them with a dashboard using your custom or open source tool to visualize the following:
1. Trend of the three metrics over the last hour, bonus point if you make this reactive.
1. Top 5 active cities over the last hour (use tweet count)
1. Worst 5 trending cities over the last hour (use retweet count)

## Expected steps
+ Create a branch of this project (or fork it in your github account if you prefer)
+ Do your changes inside this folder (challenge-3)
+ Push your changes then Pull request to our master when you are ready for a review and notify the recruiter to let us know.

## Expected deliverables
+ Design document presenting your solution and architecture, including your data flow, resource footprint analysis, and scalability plan (say EvilNet asks you to run this pipeline for all cities of the world)
+ Some code that compiles and runs using the frameworks/languages that you are comfortable with preferably with a local machine (docker-compose/minikube) if not possible using a cloud env (expecting all tokens to be able to access the solution)  
Make sure to include a script to start your application
+ If you are unable to complete the full scope, make sure to detail how you would have done it.
+ Some automated tests to enable your colleagues at EvilNet to safely update your pipeline.

## Hints
+ [This tutorial](https://developer.twitter.com/en/docs/tutorials/step-by-step-guide-to-making-your-first-request-to-the-twitter-api-v2) can guide you how to interact with Twitter API v2.
+ [This tutorial](https://developer.twitter.com/en/docs/tutorials/listen-for-important-events) shows you how to filter tweets from the source.
If this approach fails, fallback to using the sample stream and filter data at the consumer level.
+ [This tutorial](https://developer.twitter.com/en/docs/tutorials/stream-tweets-in-real-time) shows you how to stream tweets in realtime.
