import React, { useState } from 'react'
import { Form } from 'react-bootstrap'

import { MainContainer } from '../../components/MainContainer'
import { ScheduleTable } from '../../components/ScheduleTable'
import { Bus } from '../../types/Bus'
import { Driver } from '../../types/Driver'

export const Details = () => {
  const [driver, setDriver] = useState<Driver | undefined>()
  const [bus, setBus] = useState<Bus | undefined>()

  return (
    <MainContainer>
      <div className='row'>
        <div className='col-6'>
          <Form.Select
            onChange={(event) => setDriver(event.target.value)}
            placeholder='Select a Driver'
          ></Form.Select>
        </div>

        <div className='col-6'>
          <Form.Select
            onChange={(event) => setBus(event.target.value)}
            placeholder='Select a Bus'
          ></Form.Select>
        </div>
      </div>

      <div className='row'>
        <div className='col'>
          <ScheduleTable
            identifier={
              driver
                ? `${driver.firstName} ${driver.lastName}`
                : bus?.id.toString()
            }
          />
        </div>
      </div>
    </MainContainer>
  )
}
