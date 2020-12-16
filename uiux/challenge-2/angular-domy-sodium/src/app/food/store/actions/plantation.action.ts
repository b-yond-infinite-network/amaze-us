import { createAction, props } from '@ngrx/store';

export const loadPlantations = createAction(
  '[Plantation/Get] Get All Plantations'
);

export const loadPlantationsSuccess = createAction(
  '[Plantation/Get] Get All Plantations Success',
  props<{ plantations: any }>()
);

export const loadPlantationsFailure = createAction(
  '[Plantation/Get] Get All Plantations Success',
  props<{ error: any }>()
);

export const createPlantation = createAction(
  '[Plantation/Create] Create New Plantation',
  props<{ plantation: any }>()
);

export const createPlantationSuccess = createAction(
  '[Plantation/Create] Create New Success',
  props<{ plantation: any }>()
);

export const createPlantationFailure = createAction(
  '[Plantation/Create] Create New Plantation Failure',
  props<{ error: any }>()
);