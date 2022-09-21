import React, { PropsWithChildren, useContext } from 'react'
import { Navigate } from 'react-router-dom'

import { AppContext } from '../../types/AppContext'

export const ProtectedRoute = ({ children }: PropsWithChildren) => {
  const { context } = useContext(AppContext)
  if (!context.user) {
    return <Navigate to='/login' />
  }
  return <>{children}</>
}
