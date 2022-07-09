import { createReducer } from 'store/utils';
import * as types from './types';

const initialState = {
  buses: []
};

const busReducer = createReducer(initialState)({
  [types.GET_BUSES]: (state, { payload }) => ({
    ...state,
    buses: payload
  }),
  [types.CREATE_BUS]: (state, { payload }) => ({
    ...state,
    buses: [...state.buses, payload]
  })
});

export default busReducer;
