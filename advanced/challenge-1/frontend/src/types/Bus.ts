import { Schedule } from "./Schedule"

export type Bus = {
  id: number
  capacity: number
  model: string
  make: string
  schedules?: Schedule[]
}