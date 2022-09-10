import React from 'react'
import ReactDOM from 'react-dom'

export const Details = () => (
  <MainContainer>
    <div className="row">
      <div className="col-6">
        <Select options={drivers} onChange={selected = this.value} placeholder="Select a Driver" />
      </div>

      <div className="col-6">
        <Select options={buses} onChange={selected = this.value} placeholder="Select a Bus" />
      </div>
    </div>

    <div className="row">
      <div className="col">
        <ScheduleTable name={selected.name ?? selected.id} data={data} />
      </div>
    </div>
  </MainContainer>
)
