import * as types from './types';

export const toGetDrivers = payload => ({
  type: types.GET_DRIVERS,
  payload
});

export const toCreateDriver = payload => ({
  type: types.CREATE_DRIVER,
  payload
});
