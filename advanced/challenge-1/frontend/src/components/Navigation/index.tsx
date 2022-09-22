import React from 'react'
import { useNavigate } from 'react-router-dom'
import { Button, Col, Row } from 'react-bootstrap'

export const Navigation = () => {
  let navigate = useNavigate()

  return (
    <Row>
      <Col className='text-center mb-3'>
        <Button onClick={() => navigate('/')}>Home</Button>{' '}
        <Button onClick={() => navigate('/details')}>Details</Button>
      </Col>
    </Row>
  )
}
