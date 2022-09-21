import React from 'react'
import { useNavigate } from 'react-router-dom'
import { Button, Col, Row } from 'react-bootstrap'

export const Navigation = () => {
  let navigate = useNavigate()

  return (
    <Row>
      <Col xs='6' lg='3'>
        <Button onClick={() => navigate('/')}>Home</Button>
      </Col>

      <Col>
        <Button onClick={() => navigate('/details')}>Details</Button>
      </Col>
    </Row>
  )
}
