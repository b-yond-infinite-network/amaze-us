import jwt_decode from 'jwt-decode'
import _ from 'lodash'
import { Bus, Driver, User } from './types'

import { CustomContext } from './types/AppContext'

const proxy = 'http://localhost:8000'
const fetchProxy = (url: string, params: RequestInit | undefined) => {
  return fetch(proxy + url, params)
}

export const login = (contextProvider: CustomContext,
  username: string,
  password: string
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

      contextProvider.setContext({
        token: res.access_token,
        user: payload,
        ...contextProvider
      })
    } else {
      throw new Error(res);
    }
  }).catch((error) => {
    console.log(error)
  })
}

export const getDrivers = (contextProvider: CustomContext) => {
  if (!contextProvider.context.token) {
    return
  }

  const headers: HeadersInit = new Headers()
  headers.set('Accept', 'application/json')
  headers.set('Content-Type', 'application/json')
  headers.set('Authorization', `Bearer ${contextProvider.context.token}`)

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

export const getBuses = (contextProvider: CustomContext) => {
  if (!contextProvider.context.token) {
    return
  }

  const headers: HeadersInit = new Headers()
  headers.set('Accept', 'application/json')
  headers.set('Content-Type', 'application/json')
  headers.set('Authorization', `Bearer ${contextProvider.context.token}`)

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

export const getTopDrivers = (contextProvider: CustomContext,
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
  headers.set('Authorization', `Bearer ${contextProvider.context.token}`)

  fetchProxy('/api/drivers/top', {
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

export const createSchedule = (
  contextProvider: CustomContext,
  driverId: number,
  busId: number,
  date: Date,
  origin: string,
  destination: string,
  distance: number
) => {
  if (!contextProvider.context.token) {
    return
  }

  const headers: HeadersInit = new Headers()
  headers.set('Accept', 'application/json')
  headers.set('Content-Type', 'application/json')
  headers.set('Authorization', `Bearer ${contextProvider.context.token}`)

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
