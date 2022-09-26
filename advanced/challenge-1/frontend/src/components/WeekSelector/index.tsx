import React, { useEffect, useState } from 'react'
import { Col, Form, Row } from 'react-bootstrap'

type WeekSelectorProps = {
  onChange: (start: Date, end: Date) => void
}

export const WEEK_IN_MILLIS = 7 * 24 * 60 * 60 * 1000

export const WeekSelector = ({ onChange }: WeekSelectorProps) => {
  let aux: Date = new Date()
  const weeks: Date[] = [aux]
  let limit: Date = new Date(aux.getTime() + 11 * WEEK_IN_MILLIS)
  while (aux.getTime() < limit.getTime()) {
    aux = new Date(aux.getTime() + WEEK_IN_MILLIS)
    weeks.push(aux)
  }
  const format = (date: Date) => date.toLocaleDateString('en-US')
  const options = weeks.map((week) => (
    <option key={week.getTime()} value={format(week)}>
      {format(week)}
    </option>
  ))
  options.unshift(<option key={0}>Select...</option>)

  const [valid, setValid] = useState(true)
  const [startWeek, setStartWeek] = useState(weeks[0])
  const [endWeek, setEndWeek] = useState(weeks[1])

  useEffect(() => {
    console.log(`${startWeek} < ${endWeek}? ${startWeek < endWeek}`)
    let valid = startWeek < endWeek
    if (valid) onChange(startWeek, endWeek)
    setValid(valid)
  }, [startWeek, endWeek])

  return (
    <Row>
      <Form.Group as={Col} controlId='start'>
        <Form.Label>From {format(startWeek)}</Form.Label>
        <Form.Select
          onChange={(event) => setStartWeek(new Date(event.target.value))}
        >
          {options}
        </Form.Select>
      </Form.Group>

      <Form.Group as={Col} controlId='end'>
        <Form.Label>To {format(endWeek)}</Form.Label>
        <Form.Select
          onChange={(event) => setEndWeek(new Date(event.target.value))}
          isValid={valid}
          isInvalid={!valid}
        >
          {options}
        </Form.Select>
      </Form.Group>
    </Row>
  )
}
