import React, { useContext } from 'react'

import { MainContainer } from '../../components/MainContainer'
import { ScheduleManager } from '../../components/ScheduleManager'
import { TopDriversChart } from '../../components/TopDriversChart'
import { AppContext } from '../../types/AppContext'

export const Home = () => {
  const appContext = useContext(AppContext)

  return (
    <MainContainer>
      {appContext.context.user?.scopes == 'manager' ? (
        <ScheduleManager />
      ) : null}

      <TopDriversChart />
    </MainContainer>
  )
}
