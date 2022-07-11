import { createReducer } from 'store/utils';
import * as types from './types';

const initialState = {
  schedules: []
};

const scheduleReducer = createReducer(initialState)({
  [types.GET_SCHEDULES]: (state, { payload }) => ({
    ...state,
    schedules: payload
  }),
  [types.CREATE_SCHEDULE]: (state, { payload }) => ({
    ...state,
    schedules: [...state.schedules, payload]
  })
});

export default scheduleReducer;
