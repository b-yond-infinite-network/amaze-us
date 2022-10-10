# Karaoke app

All data provided by [Musixmatch](https://www.musixmatch.com).

Uses as few libraries as possible

## How to run for development?

### 1. Run backend

```bash
> cd backend

> npm run start
```

Listen on 8080 port by default

### 2. Run frontend

```bash
> cd frontend

> npm run dev
```

Listens on 3000 port. If your server is not on 8080 port,
you should change it in `frontend/.env` file.

## How to run in docker?
```bash
> docker compose up
```

Now your app should be available on http://localhost:4000

## Task Description

Develop an app for karaoke.

### Requirements

- [x] Search by artist
- [x] Search by song of selected artist
- [ ] Ability to sort the songs by the number of lyrics they have, title, and
      duration
- [x] Display the song lyrics
- [x] Protection against drunk user

For now [Musixmatch](https://www.musixmatch.com) not returns all the information
required for track filters (duration).
Also, one song has only 1 or 0 lyrics, so we can filter songs without them.
This is why song sorting is not added **yet**.

In a way to avoid extra api calls there is no searching while typing.
Instead, a hint added next to input to remind user to click search button
or `Enter`

## TODO

- [ ] Artist/Song sorting
- [ ] Mobile adaptivity
- [ ] Karaoke (music player + words highlighting)
