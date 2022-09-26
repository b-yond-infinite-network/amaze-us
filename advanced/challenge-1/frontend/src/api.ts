import jwt_decode from 'jwt-decode'
import _ from 'lodash'
import { Bus, Driver, DriverSummary, User } from './types'

import { AuthContextType, AuthCustomContext, CustomContext } from './types/AppContext'

const proxy = 'http://localhost:8000'
const fetchProxy = (url: string, params: RequestInit | undefined) => {
  return fetch(proxy + url, params)
}

export const login = (contextProvider: AuthCustomContext,
  username: string,
  password: string,
  callback: () => void
) => {
  const formData = new URLSearchParams();
  formData.append('username', username)
  formData.append('password', password)

  fetchProxy('/api/token', {
    method: 'post',
    body: formData,
    headers: {
      'Accept': 'application/json',
    }
  }).then((response) => {
    if (response.status === 201) {
      return response.json()
    }
    return response.text()
  }).then((res) => {
    if (!_.isString(res)) {
      const token = res.access_token
      const payload = jwt_decode<User>(token)

      localStorage.setItem('token', token)
      localStorage.setItem('username', payload.sub!)
      localStorage.setItem('scopes', payload.scopes!)

      contextProvider.setAuthContext({
        token: res.access_token,
        user: payload
      })
      callback()
    } else {
      throw new Error(res);
    }
  }).catch((error) => {
    console.log(error)
  })
}

export const getDrivers = (
  authContext: AuthContextType,
  contextProvider: CustomContext
) => {
  if (!authContext.token) {
    return
  }

  const headers: HeadersInit = new Headers()
  headers.set('Accept', 'application/json')
  headers.set('Content-Type', 'application/json')
  headers.set('Authorization', `Bearer ${authContext.token}`)

  fetchProxy('/api/driver', {
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

export const getBuses = (
  authContext: AuthContextType,
  contextProvider: CustomContext
) => {
  if (!authContext.token) {
    return
  }

  const headers: HeadersInit = new Headers()
  headers.set('Accept', 'application/json')
  headers.set('Content-Type', 'application/json')
  headers.set('Authorization', `Bearer ${authContext.token}`)

  fetchProxy('/api/bus', {
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

export const getTopDrivers = (
  authContext: AuthContextType,
  startWeek: Date,
  endWeek: Date,
  size: number,
  callback: (newData: DriverSummary[]) => void
) => {
  if (!authContext.token) {
    return
  }

  const headers: HeadersInit = new Headers()
  headers.set('Accept', 'application/json')
  headers.set('Content-Type', 'application/json')
  headers.set('Authorization', `Bearer ${authContext.token}`)

  fetchProxy('/api/driver/top?' + new URLSearchParams({
    start: startWeek.toISOString(),
    end: endWeek.toISOString(),
    n: size.toString()
  }), {
    method: 'get',
    headers
  }).then((response) => {
    return response.json()
  }).then((res) => {
    callback(res)
  }).catch((error) => {
    console.log(error)
  })
}

export const createSchedule = (
  authContext: AuthContextType,
  contextProvider: CustomContext,
  driverId: number,
  busId: number,
  date: Date,
  origin: string,
  destination: string,
  distance: number
) => {
  if (!authContext.token) {
    return
  }

  const headers: HeadersInit = new Headers()
  headers.set('Accept', 'application/json')
  headers.set('Content-Type', 'application/json')
  headers.set('Authorization', `Bearer ${authContext.token}`)

  fetchProxy('/api/schedule', {
    method: 'post',
    body: JSON.stringify({
      driverId,
      busId,
      date,
      origin,
      destination,
      distance
    }),
    headers
  }).then((response) => {
    return response.json()
  }).then((res) => {
    console.log('Success')
  }).catch((error) => {
    console.log(error)
  })
}
