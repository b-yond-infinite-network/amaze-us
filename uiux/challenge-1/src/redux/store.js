import { createStore, applyMiddleware, compose } from 'redux';
import createSagaMiddleware from 'redux-saga';
import rootReducer from './rootReducer';
import rootSage from './rootSaga';

const sagaMiddleware = createSagaMiddleware();

const enhancers = compose(applyMiddleware(sagaMiddleware), window.devToolsExtension ? window.devToolsExtension() : f => f);

const store = createStore(rootReducer, enhancers);

sagaMiddleware.run(rootSage);


export default store;