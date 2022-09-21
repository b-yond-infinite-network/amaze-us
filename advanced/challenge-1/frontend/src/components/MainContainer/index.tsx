import React, { PropsWithChildren, useContext } from 'react'
import { Container } from 'react-bootstrap'

import { AppContext } from '../../types/AppContext'
import { Navigation } from '../Navigation'

export const MainContainer = ({ children }: PropsWithChildren) => {
  const appContext = useContext(AppContext)
  const token = appContext.context.token

  return (
    <Container>
      <h1 className='text-center'>Bus Drivers Schedule APP</h1>
      {token ? <Navigation /> : null}
      {children}
    </Container>
  )
}
