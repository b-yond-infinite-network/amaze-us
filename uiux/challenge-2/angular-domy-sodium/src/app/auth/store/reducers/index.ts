import * as fromRoot from '../../../reducer';
import * as fromAuth from './auth.reducer';

import { ActionReducerMap, createFeatureSelector, createSelector } from '@ngrx/store';

export const authFeatureKey = 'auth';

export interface AuthState {
    auth: fromAuth.State;
}

export interface State extends fromRoot.State {
    auth: AuthState;
}

export const reducers: ActionReducerMap<AuthState> = {
    auth: fromAuth.reducer
};

const selectAuthState = createFeatureSelector<State, AuthState>('auth');
export const getUser = createSelector(selectAuthState, (state: AuthState) => state.auth.user);

