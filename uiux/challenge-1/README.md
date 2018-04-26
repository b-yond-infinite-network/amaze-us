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

*   Create project setup using [Create React App](https://github.com/facebook/create-react-app) :heavy_check_mark:
*   Create musixmatch API provider :heavy_check_mark:
*   An artist search input :heavy_check_mark:
    *   Enter at least 3 characters :heavy_check_mark:
    *   Debounced input :heavy_check_mark:
    *   Validation :heavy_check_mark:
    *   Search results component :heavy_check_mark:
*   Artist view :heavy_check_mark:
    *   Remove artist search results :heavy_check_mark:
    *   Sortable grid of tracks :heavy_check_mark:
        *   by number of lyrics :heavy_check_mark:
        *   title :heavy_check_mark:
        *   duration :heavy_check_mark:
*   Track view :heavy_check_mark:
    *   Display lyrics :heavy_check_mark:
    *   Navigation item to go back :heavy_check_mark:

## Interesting factoids

*   Trying out React's new [Context API](https://reactjs.org/docs/context.html)
*   How were API calls minimized?
    * Input is debounced
    * Sorts are performed on the client side with the existing data set
    * A minimum char input is implemented to avoid unnecessary calls

### To do
*   Unit tests
*   UI Transitions

### Would have been nice to have
*   API call cache: Store in memory results from previous calls
*   Routing
*   Get artist image from somewhere?
*   Get song from somewhere!!!
*   List of albums in artist view that links to the track list component
*   Pass apikey via configuration
