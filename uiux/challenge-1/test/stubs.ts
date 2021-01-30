import { Artist, parseRating, Track } from "../app/models"

export function createArtist(): Artist {
  return {
    id: Math.floor(Math.random() * 10000),
    name: "Pantera",
    rating: parseRating(Math.floor(Math.random() * 10), 10),
  }
}

export function createRandomAmountOfArtists(): Artist[] {
  return Array.from({ length: Math.floor(Math.random() * 100) }, () =>
    createArtist()
  )
}

export function createTrack(withLyrics = true): Track {
  return {
    id: Math.floor(Math.random() * 10000),
    name: "Cementery Gates",
    rating: parseRating(Math.floor(Math.random() * 5)),
    hasLyrics: withLyrics,
    numFavourite: Math.floor(Math.random() * 100000),
    albumName: "Cowboys from Hell",
    artistName: "Pantera",
    artistId: 5258,
  }
}

export function createRandomAmountOfTracks(): Track[] {
  return Array.from({ length: Math.floor(Math.random() * 100) }, () =>
    createTrack()
  )
}
