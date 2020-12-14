import { AuthActions } from '../actions';
import { Action, createReducer, on } from '@ngrx/store';

export interface State {
  user: any | null;
  pending: boolean;
}

export const initialState: State = {
  user: null,
  pending: false
};

const authReducer = createReducer(
  initialState,
  on(AuthActions.login, (state) => ({ ...state, pending: true })),
  on(AuthActions.loginSuccess, (state, { user }) => ({ ...state, user, pending: false })),
  on(AuthActions.loginFailure, (state) => ({ ...state, pending: false })),
  on(AuthActions.logoutSuccess, (state) => { return ({ ...state, user: null }) }),
);

export function reducer(state: State | undefined, action: Action) {
  return authReducer(state, action);
}
