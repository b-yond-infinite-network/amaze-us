import request from 'utils/request';
import { toGetBuses, toCreateBus } from './actions';

export const getBuses = () => async dispatch => {
  try {
    const { data } = await request.get('/bus/');
    dispatch(toGetBuses(data));
  } catch (error) {
    console.log(error);
  }
};

export const createBus = payload => async dispatch => {
  try {
    const { data } = await request.post('/bus/', payload);
    dispatch(toCreateBus(data));
  } catch (error) {
    console.log(error);
  }
};
