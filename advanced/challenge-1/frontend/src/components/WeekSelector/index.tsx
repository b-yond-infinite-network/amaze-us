import React, { useState } from 'react'
import { Col, Form, Row } from 'react-bootstrap'

type WeekSelectorProps = {
  onChange: (start: Date, end: Date) => void
}

export const WEEK_IN_MILLIS = 7 * 24 * 60 * 60 * 1000

export const WeekSelector = ({ onChange }: WeekSelectorProps) => {
  let valid: boolean = true
  let aux: Date = new Date()
  const weeks: Date[] = [aux]
  let limit: Date = new Date(new Date().getTime() + 11 * WEEK_IN_MILLIS)

  while (aux.getTime() < limit.getTime()) {
    aux = new Date(aux.getTime() + WEEK_IN_MILLIS)
    weeks.push(aux)
  }

  const [startWeek, setStartWeek] = useState(new Date())
  const [endWeek, setEndWeek] = useState(
    new Date(new Date().getTime() + WEEK_IN_MILLIS)
  )

  const changeHandler = (start: Date | null, end: Date | null) => {
    if (start) setStartWeek(start)
    if (end) setEndWeek(end)

    if (endWeek < startWeek) {
      valid = false
      return
    }
    onChange(startWeek, endWeek)
  }

  const format = (date: Date) => date.toISOString()

  return (
    <Row>
      <Col>
        <Form.Label htmlFor='start'>From {format(startWeek)}</Form.Label>
        <Form.Select
          id='start'
          onChange={(event) =>
            changeHandler(new Date(event.target.value), null)
          }
        >
          {weeks.map((week) => (
            <option key={week.toString()}>{week.toString()}</option>
          ))}
        </Form.Select>
      </Col>

      <Col>
        <Form.Label htmlFor='end'>To {format(endWeek)}</Form.Label>
        <Form.Select
          id='end'
          onChange={(event) =>
            changeHandler(null, new Date(event.target.value))
          }
        >
          {weeks.map((week) => (
            <option key={week.toString()}>{week.toString()}</option>
          ))}
        </Form.Select>
      </Col>
    </Row>
  )
}
