import { createStore, applyMiddleware, compose } from "redux";
import combineReducers from './reducer'
import { persistStore, persistReducer } from 'redux-persist'
import session from 'redux-persist/lib/storage/session'
import reduxThunk from "redux-thunk";

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose

const persistConfig = {
  key: 'userSession',
  storage: session
}
  
const pReducer = persistReducer(persistConfig, combineReducers)

export const store = createStore(
  pReducer,
  composeEnhancers(applyMiddleware(reduxThunk))
);

export const persistor = persistStore(store)