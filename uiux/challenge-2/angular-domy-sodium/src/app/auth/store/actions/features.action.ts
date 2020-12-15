import { createAction, props } from '@ngrx/store';

export const loadFeatures = createAction(
  '[Features/Load] Load User Features',
  props<{ features: any }>()
);