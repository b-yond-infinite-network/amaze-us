export type User = {
  sub: string | null
  scopes: string | null
}

export type Schedule = {
  id: number
  date: Date
  origin: string
  destination: string
  distance: number
}

export type Driver = {
  id: number
  firstName: string
  lastName: string
  ssn: string
  email: string
  schedules?: Schedule[]
}

export type Bus = {
  id: number
  capacity: number
  model: string
  make: string
  schedules?: Schedule[]
}

export type DriverSummary = {
  id: number
  first_name: string
  last_name: string
  total_tasks: number
  total_distance: number
}
