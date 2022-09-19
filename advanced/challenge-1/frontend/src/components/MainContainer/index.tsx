import React, { PropsWithChildren, useContext } from 'react'

import { AppContext } from '../../types/AppContext'
import { Navigation } from '../Navigation'

export const MainContainer = ({ children }: PropsWithChildren) => {
  const appContext = useContext(AppContext)
  const token = appContext.context.token

  return (
    <div className='container'>
      {token ? <Navigation /> : null}
      {children}
    </div>
  )
}
