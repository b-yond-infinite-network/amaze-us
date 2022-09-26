import React, { useContext, useEffect, useState } from 'react'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
} from 'chart.js'
import { Bar } from 'react-chartjs-2'
import { Col, Form, Row } from 'react-bootstrap'

import { getTopDrivers } from '../../api'
import { AppContext, AuthContext } from '../../types/AppContext'
import { WeekSelector, WEEK_IN_MILLIS } from '../WeekSelector'
import { DriverSummary } from '../../types'

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

export const TopDriversChart = () => {
  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top' as const
      },
      title: {
        display: true,
        text: 'Top Drivers'
      }
    }
  }

  const [data, setData] = useState({
    labels: ['Total Distance'],
    datasets: [
      {
        label: '',
        data: [0]
      }
    ]
  })

  const updateChart = (newData: DriverSummary[]) => {
    const parsedData = {
      labels: ['Total Distance'],
      datasets: newData.map((driver) => {
        return {
          label: `${driver.first_name} ${driver.last_name}`,
          data: [driver.total_distance]
        }
      })
    }
    setData(parsedData)
  }

  const fetchRank = () => {
    getTopDrivers(authContext, start, end, size, updateChart)
  }

  const [size, setSize] = useState(5)
  const [start, setStart] = useState(new Date())
  const [end, setEnd] = useState(
    new Date(new Date().getTime() + WEEK_IN_MILLIS)
  )
  const { authContext } = useContext(AuthContext)
  useEffect(() => fetchRank(), [])
  useEffect(() => fetchRank(), [start, end, size])

  return (
    <>
      <h4>Top Drivers</h4>

      <Row className='my-3'>
        <Col xs={6} lg={3}>
          <Form.Group controlId='size'>
            <Form.Label>Number of Drivers</Form.Label>
            <Form.Select
              onChange={(event) => {
                setSize(parseInt(event.target.value))
              }}
            >
              <option>5</option>
              <option>10</option>
              <option>25</option>
              <option>50</option>
              <option>100</option>
            </Form.Select>
          </Form.Group>
        </Col>
        <Col>
          <WeekSelector
            onChange={(start: Date, end: Date) => {
              setStart(start)
              setEnd(end)
            }}
          />
        </Col>
      </Row>

      <Row className='justify-content-md-center'>
        <Col>
          <Bar options={options} updateMode={'resize'} data={data} />
        </Col>
      </Row>
    </>
  )
}
