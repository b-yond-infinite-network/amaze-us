import React, { useContext, useState } from 'react'
import { Col, Form, Row } from 'react-bootstrap'

import { DriverBusSelector } from '../../components/DriverBusSelector'
import { MainContainer } from '../../components/MainContainer'
import { ScheduleTable } from '../../components/ScheduleTable'
import { Bus, Driver } from '../../types'

export const Details = () => {
  const [driver, setDriver] = useState<Driver | undefined>()
  const [bus, setBus] = useState<Bus | undefined>()

  const handleDriverBusChange = (
    newDriver: Driver | undefined = undefined,
    newBus: Bus | undefined = undefined
  ) => {
    if (newDriver) {
      setDriver(newDriver)
      setBus(undefined)
    } else {
      setDriver(undefined)
      setBus(newBus)
    }
  }

  return (
    <MainContainer>
      <DriverBusSelector callback={handleDriverBusChange} />

      <Row>
        <Col>
          <ScheduleTable
            identifier={
              driver
                ? `${driver.firstName} ${driver.lastName}`
                : bus
                ? `${bus.make} ${bus.model}`
                : ''
            }
          />
        </Col>
      </Row>
    </MainContainer>
  )
}
