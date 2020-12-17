import { fromWaterCollector } from '../actions';
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

const waterReducer = createReducer(
  initialState,
  on(fromWaterCollector.loadWaterCollector, (state) => ({ ...state, pending: true, error: false })),
  on(fromWaterCollector.loadWaterCollectorSuccess, (state, { collector }) => ({ ...state, collector, pending: false })),
  on(fromWaterCollector.loadWaterCollectorFailure, (state) => ({ ...state, pending: false, error: true })),
);

export function reducer(state: State | undefined, action: Action) {
  return waterReducer(state, action);
}
