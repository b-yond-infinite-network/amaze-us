import createSagaMiddleware from 'redux-saga';
import { configureStore } from '@reduxjs/toolkit';
import createReducer from './root-reducer';
import rootSaga from './root-saga';

if (process.env.NODE_ENV === 'development' && module.hot) {
  module.hot.accept('./root-reducer', () => {
    const newRootReducer = require('./root-reducer').default;
    store.replaceReducer(newRootReducer.createReducer());
  });
}

const sagaMiddleware = createSagaMiddleware();
const middlewares: any[] = [sagaMiddleware];

const store = configureStore({
  reducer: createReducer(),
  middleware: getDefaultMiddleware =>
    getDefaultMiddleware({
      immutableCheck: false,
    }).concat(middlewares),
  devTools: process.env.NODE_ENV === 'development'
});

sagaMiddleware.run(rootSaga);

export default store;
