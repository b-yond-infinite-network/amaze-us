import React, { useContext, useState } from 'react'
import { Form } from 'react-bootstrap'

import { getTopDrivers } from '../../api'
import { AppContext } from '../../types/AppContext'
import { WeekSelector } from '../WeekSelector'

export const TopDriversChart = () => {
  const [size, setSize] = useState(5)
  const appContext = useContext(AppContext)

  return (
    <>
      <div className='row'>
        <div className='col-6 col-lg-3'>
          <Form.Label>Number of Drivers</Form.Label>
          <Form.Select
            onChange={(event) => setSize(parseInt(event.target.value))}
          >
            <option>5</option>
            <option>10</option>
            <option>25</option>
            <option>50</option>
            <option>100</option>
          </Form.Select>
        </div>
        <div className='col'>
          <WeekSelector
            onChange={(start: Date, end: Date) => {
              getTopDrivers(appContext, start, end, size)
            }}
          />
        </div>
      </div>

      <div className='row'>
        <div className='col'>
          {appContext.context.topDrivers?.map((record) => (
            <div>record.date</div>
          ))}
        </div>
      </div>
    </>
  )
}
