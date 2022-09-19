import React, { useContext, useState } from 'react'
import { Button, FormControl } from 'react-bootstrap'

import { MainContainer } from '../../components/MainContainer'
import { login } from '../../api'
import { AppContext } from '../../types/AppContext'

export const Login = () => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const appContext = useContext(AppContext)

  const handleLogin = () => {
    login(appContext, username, password)
  }

  return (
    <MainContainer>
      <FormControl
        placeholder='Username'
        value={username}
        onChange={(event) => setUsername(event.target.value)}
      />
      <FormControl
        placeholder='Password'
        value={password}
        onChange={(event) => setPassword(event.target.value)}
      />
      <Button onClick={handleLogin}>LOGIN</Button>
    </MainContainer>
  )
}
