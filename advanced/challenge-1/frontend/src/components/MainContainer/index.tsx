import React, { PropsWithChildren, useContext } from 'react'
import { Container } from 'react-bootstrap'

import { AuthContext } from '../../types/AppContext'
import { Navigation } from '../Navigation'

export const MainContainer = ({ children }: PropsWithChildren) => {
  const { authContext } = useContext(AuthContext)
  const token = authContext.token

  return (
    <Container>
      <h1 className='text-center'>Bus Drivers Schedule APP</h1>
      {token ? <Navigation /> : null}
      {children}
    </Container>
  )
}
