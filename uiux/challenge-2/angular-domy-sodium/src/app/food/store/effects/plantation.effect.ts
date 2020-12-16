// Basics
import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { of, pipe } from 'rxjs';
import { mergeMap, catchError, map, tap, take } from 'rxjs/operators';

// Services
import { PlantationService } from '../../services/plantation.service';

// Actions
import { PlantationActions } from '../actions';

@Injectable()
export class PlantationEffect {

    constructor(
        private actions$: Actions,
        private plantationService: PlantationService,
    ) { }

    @Effect()
    getPlantations$ = createEffect(
        () => this.actions$.pipe(
            ofType(PlantationActions.loadPlantations),
            mergeMap(props => this.plantationService.getAllPlantations().pipe(
                map((response) => {
                    return PlantationActions.loadPlantationsSuccess({ plantations: response.plantation });
                }),
                catchError(error => of(PlantationActions.loadPlantationsFailure(error)))
            ))
        )
    );

    @Effect()
    createPlantation$ = createEffect(
        () => this.actions$.pipe(
            ofType(PlantationActions.createPlantation),
            mergeMap(props => this.plantationService.createPlantation(props.plantation).pipe(
                map(response => {
                    return PlantationActions.createPlantationSuccess(props);
                }),
                catchError(error => of(PlantationActions.createPlantationFailure(error)))
            ))
        )
    )

}