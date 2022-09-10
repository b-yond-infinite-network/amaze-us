import React, { PropsWithChildren, useMemo, useState } from 'react'

import { AppContext, initialContext } from '../../types/AppContext'
import { Navigation } from '../Navigation'

export const MainContainer = ({ children }: PropsWithChildren) => {
  const [context, setContext] = useState(initialContext)
  const user = context.user

  return <AppContext.Provider
    value={useMemo(() => ({ context, setContext }), [context])}
  >
    <div className="container">
      {user ? <Navigation /> : null}
      {children}
    </div>
  </AppContext.Provider>
}
