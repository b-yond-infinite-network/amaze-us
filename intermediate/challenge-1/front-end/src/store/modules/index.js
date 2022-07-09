import { combineReducers } from 'redux';
import Bus from 'store/modules/Bus';
import Driver from 'store/modules/Driver';

const rootReducer = combineReducers({ Bus, Driver });

export default rootReducer;
