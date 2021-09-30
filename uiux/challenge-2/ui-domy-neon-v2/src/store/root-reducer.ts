import { Reducer } from 'react';
import { AnyAction, combineReducers } from '@reduxjs/toolkit';
import userReducer from './auth/user-slice';
import loadingReducer from './loading-slice';
import crewReducer from '../pages/crew/redux/slice';

type ExtractReducerState<T> = T extends Reducer<infer TState, any> ? TState : T;

export type AppState = Omit<ExtractReducerState<ReturnType<typeof makeReducer>>, '$CombinedState'>;

const makeReducer = () => combineReducers({
  user: userReducer,
  loading: loadingReducer,
  crew: crewReducer,
});

const createReducer = () => (state: AppState | undefined, action: AnyAction) => {
  const combinedReducer = makeReducer();
  // Reset the redux store when user logged out
  if (action.type === 'auth/user/userLoggedOut') {
    state = undefined;
  }

  return combinedReducer(state, action);
};

export default createReducer;
