import { all } from 'redux-saga/effects';

import authSaga from './sagas/auth';
import crewSaga from '../pages/crew/redux/saga';

export default function* rootSaga() {
  yield all([
    authSaga(),
    crewSaga(),
  ]);
}
