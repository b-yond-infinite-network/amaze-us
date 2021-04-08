import React from 'react'
import { Route } from 'react-router-dom'

const AppRoute = (routeProps) => {
    const { component: Component, path, exact, extraPropsHeader } = routeProps
    let page = (
        <Route
            {...{ exact, path }}
            render={(props) => (
                <Component
                    {...props}
                    extraPropsHeader={(event) => extraPropsHeader(event)}
                />
            )}
        />
    )
    return page
}

export default AppRoute
