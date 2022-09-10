import { createContext, useContext } from 'react'
import { cloneDeep } from 'lodash'

import { User } from './User'

export type AppContextType = {
  user?: User
}

type Context = {
  context: AppContextType
  setContext: (context: AppContextType) => void
}

export const initialContext: AppContextType = {}
export const AppContext = createContext<Context>({
  context: initialContext,
  // eslint-disable-next-line no-console
  setContext: (context) => console.warn(`no provider for ${context}`)
})
export const useAppContext = () => useContext(AppContext)