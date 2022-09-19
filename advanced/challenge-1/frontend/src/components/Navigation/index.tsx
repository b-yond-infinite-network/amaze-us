import React from 'react'
import { useNavigate } from 'react-router-dom'
import { Button } from 'react-bootstrap'

export const Navigation = () => {
  let navigate = useNavigate()

  return (
    <div className='row'>
      <div className='col-6 col-lg-3'>
        <Button onClick={() => navigate('/')}>Home</Button>
      </div>

      <div className='col'>
        <Button onClick={() => navigate('/details')}>Details</Button>
      </div>
    </div>
  )
}
