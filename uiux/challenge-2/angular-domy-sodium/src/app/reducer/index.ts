import {
  ActionReducer,
  ActionReducerMap
} from '@ngrx/store';

import * as fromRouter from '@ngrx/router-store';
import { localStorageSync } from 'ngrx-store-localstorage';

export interface State {
  router: fromRouter.RouterReducerState;

}

export const reducers: ActionReducerMap<State> = {
  router: fromRouter.routerReducer
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

export function localStorageSyncReducer(reducer: ActionReducer<State>): ActionReducer<State> {
  return localStorageSync({
    keys: ['auth', 'population', 'plantation', 'water'],
    rehydrate: true,
    removeOnUndefined: true,
  })(reducer);
}