import { CustomContext } from "./types/AppContext"

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
      contextProvider.setContext({
        token: res.token,
        user: res.user,
        ...contextProvider
      })
    }
  }).catch((error) => {
    console.log(error)
  })
}

export const getDrivers = async (contextProvider: CustomContext) => {
  if (!contextProvider.context.token) {
    return
  }

  const headers: HeadersInit = new Headers()
  headers.set('Accept', 'application/json')
  headers.set('Content-Type', 'application/json')
  headers.set('bearer', contextProvider.context.token)

  await fetch('/api/drivers', {
    method: 'get',
    headers
  }).then((response) => {
    return response.json()
  }).then((res) => {
    contextProvider.setContext({ drivers: res.data, ...contextProvider })
  }).catch((error) => {
    console.log(error)
  })
}

export const getBuses = async (contextProvider: CustomContext) => {
  if (!contextProvider.context.token) {
    return
  }

  const headers: HeadersInit = new Headers()
  headers.set('Accept', 'application/json')
  headers.set('Content-Type', 'application/json')
  headers.set('bearer', contextProvider.context.token)

  await fetch('/api/bus', {
    method: 'get',
    headers
  }).then((response) => {
    return response.json()
  }).then((res) => {
    contextProvider.setContext({ buses: res.data, ...contextProvider })
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

  await fetch('/api/drivers/top', {
    method: 'get',
    body: JSON.stringify({ startWeek, endWeek, size }),
    headers
  }).then((response) => {
    return response.json()
  }).then((res) => {
    contextProvider.setContext({ topDrivers: res.data, ...contextProvider })
  }).catch((error) => {
    console.log(error)
  })
}
