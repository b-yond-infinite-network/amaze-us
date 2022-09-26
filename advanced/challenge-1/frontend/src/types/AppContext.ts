import { createContext } from 'react'
import { Bus, Driver, DriverSummary, Schedule, User } from '.'

export type AppContextType = {
  drivers?: Driver[],
  buses?: Bus[],
  schedules?: Schedule[]
}

export type CustomContext = {
  context: AppContextType
  setContext: (context: AppContextType) => void
}

export const initialContext: AppContextType = {}
export const AppContext = createContext<CustomContext>({
  context: initialContext,
  // eslint-disable-next-line no-console
  setContext: (context) => console.warn(`no provider for ${context}`)
})

export type AuthContextType = {
  token: string | null
  user: User | null
}

export type AuthCustomContext = {
  authContext: AuthContextType
  setAuthContext: (context: AuthContextType) => void
}

export const initialAuthContext: AuthContextType = {
  token: localStorage.getItem('token'),
  user: {
    sub: localStorage.getItem('username'),
    scopes: localStorage.getItem('scopes')
  }
}
export const AuthContext = createContext<AuthCustomContext>({
  authContext: initialAuthContext,
  // eslint-disable-next-line no-console
  setAuthContext: (context) => console.warn(`no provider for ${context}`)
})