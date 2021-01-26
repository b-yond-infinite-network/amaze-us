import { Rating } from "./rating"

export interface Album {
  id: number
  name: string
  rating: Rating
  release_date: string
  copyright: string
}
