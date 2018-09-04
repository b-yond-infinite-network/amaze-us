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


## Running locally
### Pre-requisites
1. CORS (Cross-Origin Resource Sharing) must be enabled. This chrome extension will do the trick:
[Allow-Control-Allow-Origin: *](https://chrome.google.com/webstore/detail/allow-control-allow-origi/nlfbmbojpeacfghkpbjhddihlkkiljbi/related?hl=en)

2. A recent version of Nodejs must be installed (tested using v8.0.0)

###
From the **/uiux/challenge-1** project directory:
1. run `npm install`
2. run `npm start`

## Using the app
Once the app is running you are presented with a UI where you can enter an artist into a search box. Enter an artist and press the "Enter" key to search by artist. Select an artist from the results. You will then be presented with a list of tracks by that artist (if any exist). Select a desired track and you will be presented with the lyrics for the selected track.

## Future improvements
- Add pagination
- Request caching

## Notes
I did not include a column for sorting the number of lyrics because the api wasn't consistent with the lyrics returned. In some instances there would be a single sentence worth of lyrics followed by `...` while in other instances it would include a larger set of the lyrics. This wouldn't give an accurate representation of the number of lyrics in the song as the lyrics are incomplete in many cases.

## Resources
[https://react-bootstrap.github.io/](https://react-bootstrap.github.io/.