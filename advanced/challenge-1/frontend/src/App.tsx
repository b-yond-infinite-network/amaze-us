import React, { useMemo, useState } from 'react'
import { Route, Routes } from 'react-router-dom'

import { Home } from './views/Home'
import { Login } from './views/Login'
import { Logout } from './views/Logout'
import { Details } from './views/Details'
import {
  AppContext,
  AuthContext,
  initialAuthContext,
  initialContext
} from './types/AppContext'
import { ProtectedRoute } from './components/ProtectedRoute'

export const App = () => {
  const [authContext, setAuthContext] = useState(initialAuthContext)
  const [context, setContext] = useState(initialContext)

  return (
    <AuthContext.Provider
      value={useMemo(() => ({ authContext, setAuthContext }), [authContext])}
    >
      <AppContext.Provider
        value={useMemo(() => ({ context, setContext }), [context])}
      >
        <div className='App'>
          <Routes>
            <Route path='/login' element={<Login />} />
            <Route path='/logout' element={<Logout />} />
            <Route
              path='/'
              element={
                <ProtectedRoute>
                  <Home />
                </ProtectedRoute>
              }
            />
            <Route
              path='/details'
              element={
                <ProtectedRoute>
                  <Details />
                </ProtectedRoute>
              }
            />
          </Routes>
        </div>
      </AppContext.Provider>
    </AuthContext.Provider>
  )
}
