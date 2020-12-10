import { AuthActions } from '../actions';
import { Action, createReducer, on } from '@ngrx/store';

export interface State {
  user: any | null;
}

export const initialState: State = {
  user: null,
};

const authReducer = createReducer(
  initialState,
  on(AuthActions.loginSuccess, (state, { user }) => ({ ...state, user })),
  on(AuthActions.logoutSuccess, (state) => { return ({ ...state, user: null }) }),
);

export function reducer(state: State | undefined, action: Action) {
  return authReducer(state, action);
}
