# Setup & Considerations
The musicxmatch API doesn't have CORS enabled. That means that direct requests to the API from browsers will fail. As a workaround I've used [https://cors-anywhere.herokuapp.com/](https://cors-anywhere.herokuapp.com/) as a proxy. Obviously this isn't a viable solution for a production application but its the only way I could get the front end working without setting up a backend myself.

Another thing to be noted is that in order to consume many API endpoints it is required to pass an api key (which must be stored client side) as a query string in the url, which is yet another reason musicxmatch APIs shouldn't be consumed directly from a browser.

Finally, I could not find any endpoint that would give me a track's duration so I only allow sorting by title and lyrics instead.

## Architectural approach
I kept things as simply as I could and didn't use redux as it felt overkill.

For styling I used bootstrap and modified a few defaults to get it to look somewhat decent. Other than a handful of inline styles where it felt appropriate, all styling was done in the `App.scss` file.

## How to run
1. Go to [https://cors-anywhere.herokuapp.com/](https://cors-anywhere.herokuapp.com/) and click the button.
2. Create an `env` file in the root directory that contains an api key: `echo 'REACT_APP_API_KEY=KEY_FROM_PR' > .env`
3. `npm i` (only the first time)
4. `npm start`

## Usage
1. Type the name of an artist in the home page.
2. Click on the artist.
3. Click on a song that you want to see the lyrics of.
4. Go back to the artist or go home using the navbar to search for a different artist.
