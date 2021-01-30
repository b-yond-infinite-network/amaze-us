import { Rating } from "./rating"

export interface Track {
  id: number
  name: string
  rating: Rating
  hasLyrics: boolean
  numFavourite: number
  albumName: string
  artistName: string
  artistId: number
}

export enum TrackOrdering {
  NAME = "name",
  RATING = "rating",
  LIKES = "numFavourite",
  HAS_LYRICS = "hasLyrics",
  ALBUM = "albumName",
}
