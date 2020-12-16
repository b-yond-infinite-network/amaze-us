// Basics
import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { mergeMap, catchError, map, tap, take } from 'rxjs/operators';

// Services
import { PlantationService } from '../../services/plantation.service';
import { Router } from '@angular/router';

// Actions
import { FoodActions, PlantationActions } from '../actions';
import { CollectorService } from 'src/app/shared/services/collector.service';

@Injectable()
export class FoodEffect {

    constructor(
        private actions$: Actions,
        private collectorService: CollectorService
    ) { }

    @Effect()
    getFoodCollector$ = createEffect(
        () => this.actions$.pipe(
            ofType(FoodActions.loadFoodCollector),
            mergeMap(props => this.collectorService.getFoodCollector().pipe(
                map((response) => {
                    return FoodActions.loadFoodCollectorSuccess({ collector: response.sotrage });
                }),
                catchError(error => of(FoodActions.loadFoodCollectorFailure(error)))
            ))
        )
    );

}