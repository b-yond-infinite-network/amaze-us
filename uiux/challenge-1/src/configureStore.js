import artistsReducer from './redux/reducers/artistsReducer';
import userStepsReducer from './redux/reducers/userStepsReducer';
import { compose, createStore, combineReducers, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
// import initialState from './redux/reducers/initialState';

const allReducers = combineReducers({
    artists: artistsReducer,
    userSteps: userStepsReducer
});

const allStoreEnhancers = compose(
  applyMiddleware(thunk),
  window.devToolsExtension && window.devToolsExtension()
);

const store = createStore(
    allReducers,
    {},
    allStoreEnhancers
  );

export default store;