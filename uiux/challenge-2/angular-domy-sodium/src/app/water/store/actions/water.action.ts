import { createAction, props } from '@ngrx/store';

export const loadWaterCollector = createAction(
  '[Water/Get] Get Water Collector'
);

export const loadWaterCollectorSuccess = createAction(
  '[Water/Get] Get Water Collector Success',
  props<{ collector: any }>()
);

export const loadWaterCollectorFailure = createAction(
  '[Water/Get] Get Water Collector Failure',
  props<{ error: any }>()
);