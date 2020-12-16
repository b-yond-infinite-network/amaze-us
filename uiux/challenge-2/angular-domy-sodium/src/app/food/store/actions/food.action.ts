import { createAction, props } from '@ngrx/store';

export const loadFoodCollector = createAction(
  '[Food/Get] Get Food Collector'
);

export const loadFoodCollectorSuccess = createAction(
  '[Food/Get] Get Food Collector Success',
  props<{ collector: any }>()
);

export const loadFoodCollectorFailure = createAction(
  '[Food/Get] Get Food Collector Failure',
  props<{ error: any }>()
);