import { createContext, useContext } from 'react'
import { Bus, Driver, DriverSummary, Schedule, User } from '.'

export type AppContextType = {
  token?: string
  user?: User
  drivers?: Driver[],
  buses?: Bus[],
  topDrivers?: DriverSummary[],
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