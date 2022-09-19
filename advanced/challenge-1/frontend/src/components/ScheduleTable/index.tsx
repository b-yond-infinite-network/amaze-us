import React, { useContext } from 'react'
import { Table } from 'react-bootstrap'
import { AppContext } from '../../types/AppContext'

type ScheduleTableProps = {
  identifier?: string
}

export const ScheduleTable = ({ identifier }: ScheduleTableProps) => {
  const appContext = useContext(AppContext)
  let totalDistance = 0
  appContext.context.schedules?.forEach(
    (schedule) => (totalDistance += schedule.distance)
  )

  return (
    <Table>
      <caption>Schedules for {identifier}</caption>
      <thead>
        <tr>
          <th scope='col'>ID</th>
          <th scope='col'>Date</th>
          <th scope='col'>Origin</th>
          <th scope='col'>Destination</th>
          <th scope='col'>Distance</th>
        </tr>
      </thead>
      <tbody>
        {appContext.context.schedules?.map((schedule) => (
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
  )
}
