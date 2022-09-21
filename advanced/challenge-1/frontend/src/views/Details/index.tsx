import React, { useContext, useState } from 'react'
import { Form } from 'react-bootstrap'

import { MainContainer } from '../../components/MainContainer'
import { ScheduleTable } from '../../components/ScheduleTable'
import { Bus, Driver } from '../../types'
import { AppContext } from '../../types/AppContext'

export const Details = () => {
  const [driver, setDriver] = useState<Driver | undefined>()
  const [bus, setBus] = useState<Bus | undefined>()
  const appContext = useContext(AppContext)

  const drivers = appContext.context.drivers
  const buses = appContext.context.buses

  return (
    <MainContainer>
      <div className='row'>
        <div className='col-6'>
          <Form.Select
            onChange={(event) =>
              setDriver(
                drivers?.find(
                  (driver) => driver.id == parseInt(event.target.value)
                )
              )
            }
            placeholder='Select a Driver'
          >
            {drivers?.map((driver) => (
              <option value={driver.id}>
                ${driver.firstName} ${driver.lastName}
              </option>
            ))}
          </Form.Select>
        </div>

        <div className='col-6'>
          <Form.Select
            onChange={(event) =>
              setBus(
                buses?.find((bus) => bus.id == parseInt(event.target.value))
              )
            }
            placeholder='Select a Bus'
          >
            {buses?.map((bus) => (
              <option value={bus.id}>
                ${bus.make} ${bus.model}
              </option>
            ))}
          </Form.Select>
        </div>
      </div>

      <div className='row'>
        <div className='col'>
          <ScheduleTable
            identifier={
              driver
                ? `${driver.firstName} ${driver.lastName}`
                : bus
                ? `${bus.make} ${bus.model}`
                : ''
            }
          />
        </div>
      </div>
    </MainContainer>
  )
}
