import { put, call } from 'redux-saga/effects';
import jwtDecode, { JwtPayload } from 'jwt-decode';
import { createSliceSaga, SagaType } from "redux-toolkit-saga";
import { PayloadAction } from "@reduxjs/toolkit";

import { setUser, setUserToken, setAuthStatus, userLoggedOut } from '../auth/user-slice';
import { ACCESS_TOKEN_STORAGE_KEY } from '../../utils/constants';
import history from '../../services/history';
import { tryCallApi } from './call-api';
import * as AuthApi from '../../services/api/auth';
import { LoginRequest } from '../../services/api/models';
import { User } from '../../models/user';

interface TokenPayload extends JwtPayload, User { }

function* onInitUserAuth() {
  const accessToken = localStorage.getItem(ACCESS_TOKEN_STORAGE_KEY);
  if (!accessToken) {
    yield put(setAuthStatus(false));
    return;
  }

  const payload = jwtDecode<TokenPayload>(accessToken);

  if ((payload.exp ?? 0) * 1000 <= (new Date()).getTime()) {
    yield put(setAuthStatus(false));
    localStorage.removeItem(ACCESS_TOKEN_STORAGE_KEY);
  }

  yield put(setUserToken(accessToken));
  yield put(setUser({
    id: payload.sub!,
    firstName: payload.firstName,
    lastName: payload.lastName,
    birthDate: payload.birthDate,
    occupation: payload.occupation,
  }));

  try {
    history.push('/');
  } catch (error) {
    console.error('Failed initializing user state', error);
  }
}

function* onLogin(action: PayloadAction<LoginRequest>) {
  try {
    yield* tryCallApi({
      fn: function*() {
        const { access_token }: any = yield call(AuthApi.login, action.payload);
        localStorage.setItem(ACCESS_TOKEN_STORAGE_KEY, access_token);
        yield call(onInitUserAuth);
      }
    });
  } catch (error) {
    console.error('Failed user login', error);
  }
}

function* onLogoutUser() {
  yield put(userLoggedOut());
  history.push('/login');
}

const sagaSlice = createSliceSaga({
  name: "auth",
  caseSagas: {
    initAuth: {
      sagaType: SagaType.TakeEvery,
      *fn(_: PayloadAction) {
        yield call(onInitUserAuth);
      }
    },
    login: {
      sagaType: SagaType.TakeLatest,
      *fn(action: PayloadAction<LoginRequest>) {
        yield call(onLogin, action);
      }
    },
    logout: onLogoutUser,
  },
  sagaType: SagaType.Watch,
});

export default sagaSlice.saga;

export const { initAuth, login, logout } = sagaSlice.actions;