import * as fromRoot from '../../../reducer';
import * as fromPlantation from './plantation.reducer';
import * as fromFood from './food.reducer';

import { ActionReducerMap, createFeatureSelector, createSelector } from '@ngrx/store';

export const plantationFeatureKey = 'plantation';

export interface PlantationState {
    plantation: fromPlantation.State,
    collector: fromFood.State
}

export interface State extends fromRoot.State {
    plantation: PlantationState
}

export const reducers: ActionReducerMap<PlantationState> = {
    plantation: fromPlantation.reducer,
    collector: fromFood.reducer
};

const selectPlantationState = createFeatureSelector<State, PlantationState>('plantation');
export const getPlantations = createSelector(selectPlantationState, (state: PlantationState) => state.plantation?.plantations);
export const getPlantationsError = createSelector(selectPlantationState, (state: PlantationState) => state.plantation.error);
export const getCollector = createSelector(selectPlantationState, (state: PlantationState) => state.collector?.collector);
