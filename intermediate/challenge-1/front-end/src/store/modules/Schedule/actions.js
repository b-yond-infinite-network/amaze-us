import * as types from './types';

export const toGetSchedules = payload => ({
  type: types.GET_SCHEDULES,
  payload
});

export const toCreateSchedule = payload => ({
  type: types.CREATE_SCHEDULE,
  payload
});
