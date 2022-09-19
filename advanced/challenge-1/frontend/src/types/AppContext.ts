import { createContext, useContext } from 'react'
import { DriverSummary } from './DriverSummary'
import { Schedule } from './Schedule'
import { User } from './User'

export type AppContextType = {
  token?: string
  user?: User
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
export const useAppContext = () => useContext(AppContext)