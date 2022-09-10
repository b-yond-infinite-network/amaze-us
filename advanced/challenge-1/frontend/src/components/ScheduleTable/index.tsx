import React from 'react'
import { Table } from 'react-bootstrap'

import { Schedule } from '../../types/Schedule'

type ScheduleTableProps = {
  identifier: string
  data: Schedule[]
}

export const ScheduleTable = ({identifier, data}: ScheduleTableProps) => {
  let totalDistance = 0
  data.forEach((schedule) => totalDistance += schedule.distance)

  return <Table>
    <caption>Schedules for {identifier}</caption>
    <thead>
      <tr>
        <th scope="col">ID</th>
        <th scope="col">Date</th>
        <th scope="col">Origin</th>
        <th scope="col">Destination</th>
        <th scope="col">Distance</th>
      </tr>
    </thead>
    <tbody>
      {data.map((schedule) => (
        <tr>
          <td>{schedule.id}</td>
          <td>{schedule.date.toString()}</td>
          <td>{schedule.origin}</td>
          <td>{schedule.destination}</td>
          <td>{schedule.distance}</td>
        </tr>
      ))}
    </tbody>
    <tfoot>
      <tr>
        <td colSpan={5}>{totalDistance}</td>
      </tr>
    </tfoot>
  </Table>
}
