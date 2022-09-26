import React, { PropsWithChildren, useContext } from 'react'
import { Navigate } from 'react-router-dom'

import { AuthContext } from '../../types/AppContext'

export const ProtectedRoute = ({ children }: PropsWithChildren) => {
  const { authContext } = useContext(AuthContext)
  if (!authContext.token) {
    console.error('Unauthenticated, redirecting to /login')
    return <Navigate to='/login' />
  }
  return <>{children}</>
}
