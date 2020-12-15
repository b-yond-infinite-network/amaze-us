import { FeaturesActions } from '../actions';
import { Action, createReducer, on } from '@ngrx/store';

export interface State {
  features: any[] | null;
}

export const initialState: State = {
  features: null
};

const authReducer = createReducer(
  initialState,
  on(FeaturesActions.loadFeatures, (state, { features }) => ({ ...state, features })),
);

export function reducer(state: State | undefined, action: Action) {
  return authReducer(state, action);
}
