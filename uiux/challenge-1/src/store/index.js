import { createStore, applyMiddleware, compose, combineReducers } from 'redux'
import promiseMiddleware from 'redux-promise-middleware'
import thunk from 'redux-thunk'

import reducers from '../reducers'


const initStore = () => {
  const reduxCompose = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
  const composeEnhancers = reduxCompose || compose
  const store = createStore(
    combineReducers({
      ...reducers
    }),
    composeEnhancers(applyMiddleware(thunk))
  )

  return store
}

let store

const getStore = () => {
  if (!store) {
    store = initStore()
    return store
  }

  return store
}

export default getStore()
