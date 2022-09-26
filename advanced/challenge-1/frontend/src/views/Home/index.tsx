import React, { useContext } from 'react'

import { MainContainer } from '../../components/MainContainer'
import { ScheduleManager } from '../../components/ScheduleManager'
import { TopDriversChart } from '../../components/TopDriversChart'
import { AuthContext } from '../../types/AppContext'

export const Home = () => {
  const { authContext } = useContext(AuthContext)

  return (
    <MainContainer>
      {authContext.user?.scopes == 'manager' ? <ScheduleManager /> : null}

      <TopDriversChart />
    </MainContainer>
  )
}
