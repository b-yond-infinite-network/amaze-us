import * as fromRoot from '../../../reducer';

import { ActionReducerMap, createFeatureSelector, createSelector } from '@ngrx/store';

export const authFeatureKey = 'auth';

export interface AuthState {
}

export interface State extends fromRoot.State {
}

export const reducers: ActionReducerMap<AuthState> = {
};

