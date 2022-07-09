import request from 'utils/request';
import { toCreateDriver, toGetDrivers } from './actions';

export const getDrivers = () => async dispatch => {
  try {
    const { data } = await request.get('/driver/');
    dispatch(toGetDrivers(data));
  } catch (error) {
    console.log(error);
  }
};

export const createDriver = payload => async dispatch => {
  try {
    const { data } = await request.post('/driver/', payload);
    dispatch(toCreateDriver(data));
  } catch (error) {
    console.log(error);
  }
};
