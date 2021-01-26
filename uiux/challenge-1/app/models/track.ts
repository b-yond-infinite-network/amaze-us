import { Rating } from "./rating"

export interface Track {
  id: number
  name: string
  rating: Rating
  hasLyrics: boolean
  numFavourite: number
  albumId: number
}
