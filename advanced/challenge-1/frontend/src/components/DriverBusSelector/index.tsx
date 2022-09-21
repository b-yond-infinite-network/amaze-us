import React, { useContext, useState } from 'react'
import { Col, Form, Row } from 'react-bootstrap'

import { Bus, Driver } from '../../types'
import { AppContext } from '../../types/AppContext'

type DriverBusSelectorProps = {
  callback: (driver: Driver | undefined, bus: Bus | undefined) => void
}

export const DriverBusSelector = ({ callback }: DriverBusSelectorProps) => {
  const [driver, setDriver] = useState<Driver | undefined>()
  const [bus, setBus] = useState<Bus | undefined>()
  const appContext = useContext(AppContext)

  const drivers = appContext.context.drivers
  const buses = appContext.context.buses

  const handleChange = (
    driverId: number | undefined = undefined,
    busId: number | undefined = undefined
  ) => {
    if (driverId) setDriver(drivers?.find((driver) => driver.id == driverId))
    if (driverId) setBus(buses?.find((bus) => bus.id == busId))
    callback(driver, bus)
  }

  return (
    <Row className='mb-3'>
      <Form.Group as={Col} controlId='driver'>
        <Form.Label>Driver</Form.Label>
        <Form.Select
          onChange={(event) => handleChange(parseInt(event.target.value))}
          placeholder='Select a Driver'
        >
          {drivers?.map((driver) => (
            <option value={driver.id}>
              ${driver.firstName} ${driver.lastName}
            </option>
          ))}
        </Form.Select>
      </Form.Group>

      <Form.Group as={Col} controlId='bus'>
        <Form.Label>Bus</Form.Label>
        <Form.Select
          onChange={(event) =>
            handleChange(undefined, parseInt(event.target.value))
          }
          placeholder='Select a Bus'
        >
          {buses?.map((bus) => (
            <option value={bus.id}>
              ${bus.make} ${bus.model}
            </option>
          ))}
        </Form.Select>
      </Form.Group>
    </Row>
  )
}
