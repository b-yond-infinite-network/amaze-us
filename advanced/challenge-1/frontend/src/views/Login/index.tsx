import React, { useContext, useState } from 'react'
import { Button, Col, Form, FormControl, Row } from 'react-bootstrap'

import { MainContainer } from '../../components/MainContainer'
import { login } from '../../api'
import { AppContext } from '../../types/AppContext'

export const Login = () => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const appContext = useContext(AppContext)

  return (
    <MainContainer>
      <Row className='justify-content-md-center'>
        <Col xs={4}>
          <Form.Group className='mb-3' controlId='username'>
            <Form.Label>Username</Form.Label>
            <Form.Control
              id='username'
              value={username}
              onChange={(event) => setUsername(event.target.value)}
            />
          </Form.Group>
          <Form.Group className='mb-3' controlId='password'>
            <Form.Label>Password</Form.Label>
            <FormControl
              placeholder='password'
              value={password}
              onChange={(event) => setPassword(event.target.value)}
            />
          </Form.Group>

          <Button onClick={() => login(appContext, username, password)}>
            LOGIN
          </Button>
        </Col>
      </Row>
    </MainContainer>
  )
}
