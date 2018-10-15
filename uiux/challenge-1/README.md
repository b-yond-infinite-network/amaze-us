# Challenge 1 - Karaoke needs words
It's your birthday (come on, just pretend!) and you're organizing a party with all your friends. 
You've decided that people shall be singing. 
Now, being the developer that you are, you won't use some cranky old book with songs to get the lyrics, you want a web 
app! Not only an app, but a beautiful Single Web Page app that you can be proud of.

It so happens that the MusixMatch API (https://developer.musixmatch.com/documentation) has all the data you need.

Now, it just needs an artist search bar, list of songs, ability to sort the songs by the number of lyrics they have, 
title, and duration. You will want, of course, to be able to display the song lyrics themselves. 

A nice bonus would be to minimize the number of calls you're doing to the MusixMatch API, because we don't want to 
bother them while singing drunk...

## Expected steps
+ Create a branch of this project (or fork it in your github account if you prefer)
+ Do you **_thang_** inside this folder (challenge-1)
+ Push your change inside a Pull Request to our master

## Requirements
[NodeJS LTS (8.12.0)](https://nodejs.org/en/download/)

## Install
Command line: `npm i`

## Run
To execute tests:
+ `npm run tests`

To see code coverage:
+ `npm run coverage`

To run (hot reload):
+ `npm run watch`

For building source:
+ `npm run build`

## Dependencies
+ React
+ FontAwesome
+ TypeScript
+ FlexBox
+ SASS
+ WebPack
+ Jest
+ Enzyme

## Missing bits
+ Would liked to have paging to go through the lenghty collection of some artists (Michael Jackson for instance)
+ Better design. This is kind of a quick (less then 10 hours) solution. So it's still pretty basic.
+ Missing the sort on the `songs by the number of lyrics`. That was probably a way to sort the artist and not the tracks/lyrics, since it implied to fetch all tracks for artists I won't be clicking. If it was to sort by the length of the lyrics, would have been the same problem. So to reduce the number of calls, I prefered to got with the lazy loading.
+ A better way to manage already fetched data. Just having an object that could become a "pain", maybe I could have gone for the session storage or local storage... 
+ A logo would have been cool... Yeah, a better design... But I prefered to go for some of the functionalities and cut short on the design... 
+ Font Awesome acting up with improper copy of fonts... Or Webpack...
