import * as fromRoot from '../../../reducer';
import * as fromWaterCollector from './water.reducer';

import { ActionReducerMap, createFeatureSelector, createSelector } from '@ngrx/store';

export const waterFeatureKey = 'water';

export interface WaterState {
    collector: fromWaterCollector.State
}

export interface State extends fromRoot.State {
    water: WaterState
}

export const reducers: ActionReducerMap<WaterState> = {
    collector: fromWaterCollector.reducer
};

const selectPlantationState = createFeatureSelector<State, WaterState>('water');
export const getCollector = createSelector(selectPlantationState, (state: WaterState) => state.collector?.collector);

