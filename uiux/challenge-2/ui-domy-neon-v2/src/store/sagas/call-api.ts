import { put, call } from 'redux-saga/effects';
import { ValidationError } from '../../services/api/models';
import { showNotification } from '../../utils/ui';

import { LoadingStates, startLoading, stopLoading } from '../loading-slice';
import { setRemoteValidationErrors } from '../validation-slice';

export interface ApiCallSagaConfig {
  fn: () => Generator;
  loadingSection?: keyof LoadingStates;
};

export function* callApi({
  fn,
  loadingSection,
} : ApiCallSagaConfig) : Generator {
  const section = loadingSection || 'global';
  yield put(startLoading(section));
  
  const result = yield call(fn);
  yield put(stopLoading(section));

  return result;
}

export function* tryCallApi(config : ApiCallSagaConfig) : Generator {
  
  yield put(setRemoteValidationErrors([]));

  try {
    const apiResponse: any = yield* callApi(config);
    return apiResponse;
  } catch(e: any) {
    showNotification({
      title: 'Api call failed',
      type: 'error',
      content: e.message,
    });

    if (e instanceof ValidationError) {
      yield put(setRemoteValidationErrors(e.errors));
    }
    
    throw e;
  } finally {
    yield put(stopLoading(config.loadingSection || 'global'));
  }
}
