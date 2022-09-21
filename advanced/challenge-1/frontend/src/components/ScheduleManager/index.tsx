import React, { useContext, useState } from 'react'
import { Button, Col, Form, Row } from 'react-bootstrap'
import { createSchedule } from '../../api'
import { Bus, Driver } from '../../types'
import { AppContext } from '../../types/AppContext'

import { DriverBusSelector } from '../DriverBusSelector'

export const ScheduleManager = () => {
  const [driver, setDriver] = useState<Driver | undefined>()
  const [bus, setBus] = useState<Bus | undefined>()
  const [date, setDate] = useState(new Date())
  const [origin, setOrigin] = useState('')
  const [destination, setDestination] = useState('')
  const [distance, setDistance] = useState(0)
  const appContext = useContext(AppContext)

  const handleDriverBusChange = (
    newDriver: Driver | undefined = undefined,
    newBus: Bus | undefined = undefined
  ) => {
    setDriver(newDriver)
    setBus(newBus)
  }

  return (
    <>
      <DriverBusSelector callback={handleDriverBusChange} />

      <Row className='mb-3'>
        <Form.Group as={Col} controlId='date'>
          <Form.Label>Date</Form.Label>
          <Form.Control
            value={date.toString()}
            onChange={(event) => setDate(new Date(event.target.value))}
            type='date'
          />
        </Form.Group>

        <Form.Group as={Col} controlId='origin'>
          <Form.Label>Origin</Form.Label>
          <Form.Control
            value={origin}
            onChange={(event) => setOrigin(event.target.value)}
          />
        </Form.Group>
      </Row>

      <Row>
        <Form.Group as={Col} controlId='destination'>
          <Form.Label>Destination</Form.Label>
          <Form.Control
            value={destination}
            onChange={(event) => setDestination(event.target.value)}
          />
        </Form.Group>

        <Form.Group as={Col} controlId='distance'>
          <Form.Label>Distance</Form.Label>
          <Form.Control
            value={distance}
            onChange={(event) => setDistance(parseInt(event.target.value))}
            type='number'
          />
        </Form.Group>
      </Row>

      <Button
        onClick={() => {
          if (!driver || !bus || !date || !origin || !destination || !distance)
            return

          createSchedule(
            appContext,
            driver.id,
            bus.id,
            date,
            origin,
            destination,
            distance
          )
        }}
      >
        LOGIN
      </Button>
    </>
  )
}
