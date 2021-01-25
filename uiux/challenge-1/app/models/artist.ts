import { Rating } from "./rating"

export interface Artist {
  id: number
  name: string
  rating: Rating
  country?: string
  startYear?: number
  endYear?: number
  pictureUrl?: string
  twitterUrl?: string
}
