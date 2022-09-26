import React, { useContext, useState } from 'react'
import { Button, Col, Form, FormControl, Row } from 'react-bootstrap'
import { Navigate, useNavigate } from 'react-router-dom'

import { MainContainer } from '../../components/MainContainer'
import { login } from '../../api'
import { AuthContext } from '../../types/AppContext'

export const Login = () => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const navigate = useNavigate()
  const authContextProvider = useContext(AuthContext)

  if (authContextProvider.authContext.token) {
    return <Navigate to='/' />
  }

  return (
    <MainContainer>
      <Row className='justify-content-md-center'>
        <Col xs={4}>
          <Form.Group className='mb-3' controlId='username'>
            <Form.Label>Username</Form.Label>
            <Form.Control
              value={username}
              onChange={(event) => setUsername(event.target.value)}
            />
          </Form.Group>

          <Form.Group className='mb-3' controlId='password'>
            <Form.Label>Password</Form.Label>
            <FormControl
              type='password'
              value={password}
              onChange={(event) => setPassword(event.target.value)}
            />
          </Form.Group>

          <Button
            onClick={() => {
              login(authContextProvider, username, password, () =>
                navigate('/')
              )
            }}
          >
            Login
          </Button>
        </Col>
      </Row>
    </MainContainer>
  )
}
