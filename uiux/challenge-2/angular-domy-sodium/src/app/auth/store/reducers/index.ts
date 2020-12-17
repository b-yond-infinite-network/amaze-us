import * as fromRoot from '../../../reducer';
import * as fromAuth from './auth.reducer';
import * as fromFeatures from './features.reducer';

import { ActionReducerMap, createFeatureSelector, createSelector } from '@ngrx/store';

export const authFeatureKey = 'auth';

export interface AuthState {
    auth: fromAuth.State;
    features: fromFeatures.State;
}

export interface State extends fromRoot.State {
    auth: AuthState;
}

export const reducers: ActionReducerMap<AuthState> = {
    auth: fromAuth.reducer,
    features: fromFeatures.reducer
};

const selectAuthState = createFeatureSelector<State, AuthState>('auth');
export const getUser = createSelector(selectAuthState, (state: AuthState) => state.auth?.user);
export const getUserError = createSelector(selectAuthState, (state: AuthState) => state.auth?.error);
export const getFeatures = createSelector(selectAuthState, (state: AuthState) => state.features);

