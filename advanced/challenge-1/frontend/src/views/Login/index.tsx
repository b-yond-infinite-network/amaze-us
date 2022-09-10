import React from 'react'
import { Button, Form, FormControl } from 'react-bootstrap'

import { MainContainer } from '../../components/MainContainer'

export const Login = () => (
  <MainContainer>
    <Form action="/api/login" method="post">
      <FormControl name="username" placeholder="Username" />
      <FormControl name="password" placeholder="Password" />
      <Button>LOGIN</Button>
    </Form>
  </MainContainer>
)
