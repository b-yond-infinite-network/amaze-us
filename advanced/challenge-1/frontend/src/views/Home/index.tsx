import React from 'react'

import { MainContainer } from '../../components/MainContainer'
import { TopDriversChart } from '../../components/TopDriversChart'

export const Home = () => {
  return <MainContainer>
    {user.role == 'manager' ? <ScheduleManager /> : null}
    <TopDriversChart />
  </MainContainer>
}
