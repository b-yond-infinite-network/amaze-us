import React, { useContext, useState } from 'react'
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'
import { Pie } from 'react-chartjs-2'
import { Col, Form, Row } from 'react-bootstrap'

import { getTopDrivers } from '../../api'
import { AppContext } from '../../types/AppContext'
import { WeekSelector } from '../WeekSelector'

ChartJS.register(ArcElement, Tooltip, Legend)

export const TopDriversChart = () => {
  const [size, setSize] = useState(5)
  const appContext = useContext(AppContext)

  const data = {
    labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
    datasets: [
      {
        label: '# of Votes',
        data: [12, 19, 3, 5, 2, 3],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(255, 159, 64, 0.2)'
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1
      }
    ]
  }

  return (
    <>
      <Row>
        <Col xs={6} lg={3}>
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
        </Col>
        <Col>
          <WeekSelector
            onChange={(start: Date, end: Date) => {
              getTopDrivers(appContext, start, end, size)
            }}
          />
        </Col>
      </Row>

      <Row>
        <Col>
          <Pie data={data} />
        </Col>
      </Row>
    </>
  )
}
