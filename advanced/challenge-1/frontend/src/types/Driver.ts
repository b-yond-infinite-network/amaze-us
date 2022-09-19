import { Schedule } from "./Schedule"

export type Driver = {
  id: number
  firstName: string
  lastName: string
  ssn: string
  email: string
  schedules?: Schedule[]
}