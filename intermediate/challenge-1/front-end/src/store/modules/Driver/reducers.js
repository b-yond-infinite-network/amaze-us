import { createReducer } from 'store/utils';
import * as types from './types';

const initialState = {
  drivers: []
};

const driverReducer = createReducer(initialState)({
  [types.GET_DRIVERS]: (state, { payload }) => ({
    ...state,
    drivers: payload
  }),
  [types.CREATE_DRIVER]: (state, { payload }) => ({
    ...state,
    drivers: [...state.drivers, payload]
  })
});

export default driverReducer;
