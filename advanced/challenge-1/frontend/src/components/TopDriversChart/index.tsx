import React from 'react'
import ReactDOM from 'react-dom'

export const TopDriversChart = () => (
  <div className="row">
    <div className="col-6 col-lg-3">
      <Select onChange={size = this.value}>
        <option>5</option>
        <option>10</option>
        <option>25</option>
        <option>50</option>
        <option>100</option>
      </Select>
    </div>
    <div className="col">
      <WeekSelector onChange={(start, end) => {
        startWeek = start
        endWeek = end
      }} />
    </div>
  </div>

  <div className="row">
    <div className="col">
      <Chart data={data} />
    </div>
  </div>
)
