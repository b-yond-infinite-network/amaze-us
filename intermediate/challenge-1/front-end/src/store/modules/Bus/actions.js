import * as types from './types';

export const toGetBuses = payload => ({
  type: types.GET_BUSES,
  payload
});

export const toCreateBus = payload => ({
  type: types.CREATE_BUS,
  payload
});
