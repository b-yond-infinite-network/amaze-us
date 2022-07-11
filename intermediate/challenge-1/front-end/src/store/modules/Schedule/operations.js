import request from 'utils/request';
import { toGetSchedules, toCreateSchedule } from './actions';

export const getSchedules = () => async dispatch => {
  try {
    const { data } = await request.get('/schedule/');
    dispatch(toGetSchedules(data));
  } catch (error) {
    console.log(error);
  }
};

export const createSchedule = payload => async dispatch => {
  try {
    const { data } = await request.post('/schedule/', payload);
    dispatch(toCreateSchedule(data));
  } catch (error) {
    console.log(error);
  }
};
