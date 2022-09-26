import React, { useContext, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { AuthContext } from '../../types/AppContext'

export const Logout = () => {
  const authContext = useContext(AuthContext)
  const navigate = useNavigate()

  useEffect(() => {
    localStorage.clear()
    authContext.setAuthContext({
      token: null,
      user: null
    })
    navigate('/')
  }, [])

  return null
}
