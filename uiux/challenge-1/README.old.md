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

## Implementation checklist

*   Create project setup using [Create React App](https://github.com/facebook/create-react-app)
*   Create musixmatch API provider :+1:
*   An artist search input :+1:
    * Enter at least 3 characters
    * Validation
    * Live search results grid
        * Search result component
*   Artist view
    * List of albums
        * Album component (links to grid of album tracks)
    * Sortable grid of tracks
        * by number of lyrics
        * title
        * duration
*   Track view
    * Display lyrics
    * Navigation item to go back
*   Design implementation

## What's new and exciting?

* Trying out React's new [Context API](https://reactjs.org/docs/context.html)

### To do
*   Pass apikey via configuration
*   Unit tests
*   Transitions

### Nice to have
*   Connect to Spotify API
*   Routing
*   Get artist image from somewhere?