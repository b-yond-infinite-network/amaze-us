import { CustomContext } from "./types/AppContext"
import { DriverSummary } from "./types/DriverSummary"
import { User } from "./types/User"

export const login = (contextProvider: CustomContext,
  username: string,
  password: string
) => {
  fetch('/api/token', {
    method: 'post',
    body: JSON.stringify({ username, password }),
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
  }).then((response) => {
    return response.json()
  }).then((res) => {
    if (res.status === 201) {
      const token = res.token
      const user: User = res.user
      contextProvider.setContext({ token, user, ...contextProvider })
    }
  }).catch((error) => {
    console.log(error)
  })
}

export const getTopDrivers = async (contextProvider: CustomContext,
  startWeek: Date,
  endWeek: Date,
  size: number
) => {
  if (!contextProvider.context.token) {
    return
  }

  const headers: HeadersInit = new Headers()
  headers.set('Accept', 'application/json')
  headers.set('Content-Type', 'application/json')
  headers.set('bearer', contextProvider.context.token)

  await fetch('/api/topdrivers', {
    method: 'post',
    body: JSON.stringify({ startWeek, endWeek, size }),
    headers
  }).then((response) => {
    return response.json()
  }).then((res) => {
    const records: DriverSummary[] = []

    res.data.forEach((retrieved: { id: any; totalTasks: any; totalDistance: any }) => records.push({
      id: retrieved.id,
      totalTasks: retrieved.totalTasks,
      totalDistance: retrieved.totalDistance
    }))

    contextProvider.setContext({ topDrivers: records, ...contextProvider })
  }).catch((error) => {
    console.log(error)
  })
}
