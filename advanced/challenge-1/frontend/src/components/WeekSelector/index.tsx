import React from 'react'
import ReactDOM from 'react-dom'

type WeekSelectorProps = {
  onChange: function
}

export const WeekSelector = ({ onChange }: WeekSelectorProps) => {
  let valid: boolean = true
  let startWeek: Date = new Date()
  let endWeek: Date = new Date() + 7

  const changeHandler = (start: Date, end: Date) => {
    startWeek = start ?? startWeek
    endWeek = end ?? endWeek

    if (endWeek < startWeek) {
      valid = false
      return
    }
    onChange(startWeek, endWeek)
  }

  const format = (date: Date) => date.toString()

  return (
    <div className="row">
      <div className="col">
        <Label for="start">From {format(startWeek)}</Label>
        <Select options={weeks} onChange={changeHandler(this.value, null)} />
      </div>

      <div className="col">
        <Label for="end">To {format(endWeek)}</Label>
        <Select options={weeks}  onChange={changeHandler(null, this.value)} />
      </div>
    </div>
  )
}
