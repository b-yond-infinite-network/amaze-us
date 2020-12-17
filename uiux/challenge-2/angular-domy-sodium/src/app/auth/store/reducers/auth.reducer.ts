import { AuthActions } from '../actions';
import { Action, createReducer, on } from '@ngrx/store';

export interface State {
  user: any | null;
  pending: boolean;
  error: boolean;
}

export const initialState: State = {
  user: null,
  pending: false,
  error: false
};

const authReducer = createReducer(
  initialState,
  on(AuthActions.login, (state) => ({ ...state, pending: true, error: false })),
  on(AuthActions.loginSuccess, (state, { user }) => ({ ...state, user, pending: false })),
  on(AuthActions.loginFailure, (state) => ({ ...state, pending: false, error: true })),
  on(AuthActions.logoutSuccess, (state) => { return ({ ...state, user: null }) }),
  on(AuthActions.register, (state) =>  ({ ...state, pending: true })),
  on(AuthActions.registerSuccess, (state, { user }) => ({ ...state, user, pending: false })),
  on(AuthActions.registerFailure, (state) => ({ ...state, pending: false }))
);

export function reducer(state: State | undefined, action: Action) {
  return authReducer(state, action);
}
