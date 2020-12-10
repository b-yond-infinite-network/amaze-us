import {
    ActionReducer,
    ActionReducerMap
  } from '@ngrx/store';

  export interface State {}
  
  export const reducers: ActionReducerMap<State> = {
  
  };
  
  export function logger(reducer: ActionReducer<State>): ActionReducer<State> {
    return (state: State, action: any): any => {
      const result = reducer(state, action);
      console.groupCollapsed(action.type);
      console.log('prev state', state);
      console.log('action', action);
      console.log('next state', result);
      console.groupEnd();
  
      return result;
    };
  }