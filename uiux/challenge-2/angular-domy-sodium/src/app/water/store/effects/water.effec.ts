// Basics
import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { mergeMap, catchError, map, tap, take } from 'rxjs/operators';

// Services
import { CollectorService } from 'src/app/shared/services/collector.service';

// Actions
import { fromWaterCollector } from '../actions';

@Injectable()
export class WaterEffect {

    constructor(
        private actions$: Actions,
        private collectorService: CollectorService
    ) { }

    @Effect()
    getWaterCollector$ = createEffect(
        () => this.actions$.pipe(
            ofType(fromWaterCollector.loadWaterCollector),
            mergeMap(() => this.collectorService.getWaterCollector().pipe(
                map((response) => {
                    return fromWaterCollector.loadWaterCollectorSuccess({ collector: response.sotrage });
                }),
                catchError(error => of(fromWaterCollector.loadWaterCollectorFailure(error)))
            ))
        )
    );

}