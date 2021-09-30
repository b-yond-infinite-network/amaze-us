import { put, call } from 'redux-saga/effects';
import { createSliceSaga, SagaType } from "redux-toolkit-saga";
import { PayloadAction } from "@reduxjs/toolkit";

import { setBirthRequests, setMembers } from './slice';
import { tryCallApi } from '../../../store/sagas/call-api';
import * as CrewApi from '../../../services/api/crew';
import { BabyMakingRequestStatus } from '../../../models/baby-making-request';
import { NewBirthRequest } from '../../../services/api/models';
import { showNotification } from '../../../utils/ui';

interface ReviewRequestPayload {
  status: BabyMakingRequestStatus;
  requestId: string;
  notes?: string;
};

interface NewRequestPayload {
  request: NewBirthRequest;
  callback?: () => void;
};

function* onFetchBirthRequests() {
  try {
    yield* tryCallApi({
      fn: function*() {
        const requests: any = yield call(CrewApi.fetchBirthRequests);
        yield put(setBirthRequests(requests));
      },
      loadingSection: 'crew'
    });
  } catch (error) {
    console.error('Failed fecthing crew members', error);
  }
}

function* onFetchCrewMembers(_: PayloadAction) {
  try {
    yield* tryCallApi({
      fn: function*() {
        const crewMembers: any = yield call(CrewApi.fetchCrewMembers);
        yield put(setMembers(crewMembers));
      },
      loadingSection: 'crew'
    });
  } catch (error) {
    console.error('Failed fecthing crew members', error);
  }
}

function* onReviewBirthRequest(action: PayloadAction<ReviewRequestPayload>) {
  try {
    yield* tryCallApi({
      fn: function*() {
        const { requestId, status, notes } = action.payload;
        yield call(CrewApi.reviewBirthRequest, requestId, status, notes);
        showNotification({
          title: 'Review completed',
          type: 'success',
          content: 'Operation successfull'
        });
        yield call(onFetchBirthRequests);
      },
      loadingSection: 'crew'
    });
  } catch (error) {
    console.error('Failed reviewing birth request', error);
  }
}

function* onCreateBirthRequest(action: PayloadAction<NewRequestPayload>) {
  try {
    yield* tryCallApi({
      fn: function*() {
        yield call(CrewApi.createBirthRequests, action.payload.request);
        yield call(onFetchBirthRequests);
        if (action.payload.callback) {
          yield call(action.payload.callback);
        }
        showNotification({
          title: 'Request created',
          type: 'success',
          content: 'Operation successfull'
        });
      },
      loadingSection: 'crew'
    });
  } catch (error) {
    console.error('Failed creating birth request', error);
  }
}

const sagaSlice = createSliceSaga({
  name: "crew",
  caseSagas: {
    fetchBirthRequests: {
      sagaType: SagaType.TakeLatest,
      *fn(_: PayloadAction) {
        yield call(onFetchBirthRequests);
      }
    },
    fetchCrewMembers: {
      sagaType: SagaType.TakeLatest,
      *fn(action: PayloadAction) {
        yield call(onFetchCrewMembers, action);
      }
    },
    reviewBirthRequest: {
      sagaType: SagaType.TakeEvery,
      *fn(action: PayloadAction<ReviewRequestPayload>) {
        yield call(onReviewBirthRequest, action);
      }
    },
    createBirthRequest: {
      sagaType: SagaType.TakeEvery,
      *fn(action: PayloadAction<NewRequestPayload>) {
        yield call(onCreateBirthRequest, action);
      }
    }
  },
  sagaType: SagaType.Watch,
});

export default sagaSlice.saga;

export const {
  fetchBirthRequests,
  fetchCrewMembers,
  reviewBirthRequest,
  createBirthRequest,
} = sagaSlice.actions;