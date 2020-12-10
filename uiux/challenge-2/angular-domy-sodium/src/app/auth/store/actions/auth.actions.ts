import { createAction, props } from '@ngrx/store';

export const loginSuccess = createAction(
  '[Auth/Login] Login Success',
  props<{ user: any,  thirdPartyParams: any }>()
);

export const loginFailure = createAction(
  '[Auth/Login] Login Failure',
  props<{ error: any }>()
);

export const login = createAction(
  '[Auth/Login] Login',
  props<{ error: any }>()
);

export const register = createAction(
  '[Auth/Register] Register',
  props<{ auth: any }>()
);

export const registerSuccess = createAction(
  '[Auth/Register] Register Success',
  props<{ auth: any }>()
);

export const registerFailure = createAction(
  '[Auth/Register] Register Failure',
  props<{ error: any }>()
);

export const updateLanguage = createAction(
  '[Auth/Update Language] Update Language',
  props<{ language: string  }>()
);

export const updateLanguageFailure = createAction(
  '[Auth/Update Language] Update Language Success',
  props<{ language: string  }>()
);

export const updateLanguageSuccess = createAction(
  '[Auth/Update Language] Update Language Success',
  props<{ language: string  }>()
);

export const logout = createAction(
  '[Auth/Logout] Logout'
);

export const logoutSuccess = createAction(
  '[Auth/Logout] Logout Success'
);

export const logoutFailure = createAction(
  '[Auth/Logout] Logout Failure',
  props<{ error: any }>()
);