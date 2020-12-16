import { PlantationActions } from '../actions';
import { Action, createReducer, on } from '@ngrx/store';

export interface State {
  plantations: any[] | null;
  pending: boolean;
  error: boolean;
}

export const initialState: State = {
  plantations: null,
  pending: false,
  error: false
};

const plantationReducer = createReducer(
  initialState,
  on(PlantationActions.loadPlantations, (state) => ({ ...state, pending: true, error: false })),
  on(PlantationActions.loadPlantationsSuccess, (state, { plantations }) => ({ ...state, plantations })),
  on(PlantationActions.loadPlantations, (state) => ({ ...state, pending: false, error: true })),
  on(PlantationActions.createPlantationSuccess, (state, { plantation}) => ({ ...state, plantations: [ ...state.plantations, plantation] }))
);

export function reducer(state: State | undefined, action: Action) {
  return plantationReducer(state, action);
}
