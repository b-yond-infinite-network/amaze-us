## KAROAKE NEEDS WORDS

## Quick Setup

This app is composed of two entities, a backend that acts as wrapper for the API requests to and from MusixMatch and a frontend.

To run the docker container,

    $ cd uiux/challenge-1/
    $ docker-compose up --build

Open your browser at `http://localhost:9000`. The backend runs on port 5000.

## Starting in dev modes

To start backend in dev mode:

    $ cd backend
    $ npm run dev

Listens on port 5000

To start frontend in dev mode:

    $ cd frontend
    $ npm start

Opens Webpack dev server on port 8080

## To run tests

### Backend

    $ cd backend
    $ npm run test

### Frontend

    $ cd frontend
    $ npm run test

## Implemented features

- Search for Artist and tracks separately (unique search result card for artist and track).
- On a single click, get all track results of an artist.
- Server side pagination for all results.
- Responsive design and compatible for mobile screens too.
- Sort any results based on name and ratings.
- Opens lyrics on a seperate page, so you can have multiple lyrics opened simultaneously.
- Access twitter profiles of artists, if available from MusixMatch.
- Get explicit content warnings for tracks.

## Design designs and considerations

### Backend

#### _What I did..._

- Decided to have dedicated backend service is to have the ability to add more third party API providers like MusixMatch. This essentially makes it possible to add/customize/filter features, while keeping the frontend code focusing only on the UI.
- Dedicated error handling classes implemented for several error codes, so there is a clear and easy way to handle client vs server errors (`util/errorHandling`, `util/httpErrors`).
- The `api` folder mimics the actual API path of that particular endpoint, and this is just a personal preference.

#### _I could've..._

- **Cached API query results using Redis or Memcache**. This would've helped in bypassing frequent MusixMatch fetch call for recently searched queries.
- **User accounts**? For the particular situation (people are drunk and singing), bothering them with logging in is unnecessary. But for those who wants to have personalized search results and tracking, account feature would've been great.
- **Stream music on demand while showing lyrics**. Or even a simple Youtube video embedding...
  This could've been another good add-on.

### Frontend

#### _I did..._

- Both artist and search results are paginated and I stored this on `React` state object. So, when N different pages were searched, N results are stored to the object in this mapping: `[pageNumber: number]: IArtist[] | ITrack[]` where `IArtist` and `ITrack` are type definitions for artist and track search results respectively. This makes it possible to get already searched page results without fetching it again from the backend but the _space complexity is O(N \* M) where N is the number of pages and M is the page size of each page._
- Created responsive designs from the very beginning and one that supports mobile screens too.
- The choice of searching artists separately and getting all tracks from a particular artist. Like search for `Eminem` and by clicking `Get all tracks` to get all paginated results of tracks by `Eminem`.
- Sorting the results by name (title) and ratings.

#### _I could've..._

- **Display N recently searched queries.**, by using a LRU cache.
- **Sort by duration and number of lyrics**. Duration of a track is not provided by MusixMatch. For number of lyrics, MusixMatch doesn't have a single point of access, but it would have to be searching all tracks of an artist with lyrics available, which in turns makes many unnecessary calls to MusixMatch.

### Some technical difficulties I faced

- MusixMatch servers were frequently responding with 503 errors, so I decided to have it shown explicitly in the frontend that it's been caused by MusixMatch.

### Others

Check out the basic design (`designs/karaoke-needs-words.xd`) I made using AdobeXD to get some understanding of what I'm about to build. It doesn't resemble the final product but it pretty much tells how I approached the UI designing.
