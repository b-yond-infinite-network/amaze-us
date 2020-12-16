import { FoodActions } from '../actions';
import { Action, createReducer, on } from '@ngrx/store';

export interface State {
  collector: any[] | null;
  pending: boolean;
  error: boolean;
}

export const initialState: State = {
  collector: null,
  pending: false,
  error: false
};

const foodReducer = createReducer(
  initialState,
  on(FoodActions.loadFoodCollector, (state) => ({ ...state, pending: true, error: false })),
  on(FoodActions.loadFoodCollectorSuccess, (state, { collector }) => ({ ...state, collector })),
  on(FoodActions.loadFoodCollectorFailure, (state) => ({ ...state, pending: false, error: true })),
);

export function reducer(state: State | undefined, action: Action) {
  return foodReducer(state, action);
}
