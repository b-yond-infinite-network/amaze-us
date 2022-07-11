import { combineReducers } from 'redux';
import Bus from 'store/modules/Bus';
import Driver from 'store/modules/Driver';
import Schedule from 'store/modules/Schedule';

const rootReducer = combineReducers({ Bus, Driver, Schedule });

export default rootReducer;
