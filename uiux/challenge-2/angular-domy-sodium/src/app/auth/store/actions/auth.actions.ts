import { createAction, props } from '@ngrx/store';

export const login = createAction(
  '[Auth/Login] Login',
  props<{ credentials: { recognitionNumber: string, password: string } }>()
);

export const loginFailure = createAction(
  '[Auth/Login] Login Failure',
  props<{ error: any }>()
);

export const loginSuccess = createAction(
  '[Auth/Login] Login Success',
  props<{ user: any }>()
);

export const loadToken = createAction(
  '[Auth/Login] Load New Token',
  props<{ token: string }>()
) 

export const register = createAction(
  '[Auth/Register] Register',
  props<{ auth: { recognition_number: string, password: string } }>()
);

export const registerSuccess = createAction(
  '[Auth/Register] Register Success',
  props<{ user: any }>()
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