import React from 'react'

import { Button } from 'react-bootstrap'

export const Navigation = () => (
  <div className="row">
    <div className="col-6 col-lg-3">
      <Button onClick={navigateTo('/home')}>Home</Button>
    </div>

    <div className="col">
      <Button onClick={navigateTo('/details')}>Details</Button>
    </div>
  </div>
)
