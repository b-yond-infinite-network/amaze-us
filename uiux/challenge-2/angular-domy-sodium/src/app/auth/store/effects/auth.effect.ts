// Basics
import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { mergeMap, catchError, map, tap, take } from 'rxjs/operators';

// Services
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';

// Actions
import { AuthActions, FeaturesActions } from '../actions';

@Injectable()
export class AuthEffects {

    constructor(
        private actions$: Actions,
        private authenticationService: AuthenticationService,
        private router: Router
    ) { }

    @Effect()
    login$ = createEffect(
        () => this.actions$.pipe(
            ofType(AuthActions.login),
            mergeMap(props => this.authenticationService.login(props.credentials).pipe(
                map((response) => {
                    localStorage.setItem('token', response.metadata.token);
                    FeaturesActions.loadFeatures({ features: response.features });
                    this.router.navigate(['/auth/home']);
                    return AuthActions.loginSuccess({ user: response.user });
                }),
                catchError(error => of(AuthActions.loginFailure(error)))
            ))
        )
    );

    @Effect()
    register$ = createEffect(
        () => this.actions$.pipe(
            ofType(AuthActions.register),
            mergeMap(props => this.authenticationService.registerConfiramtion(props.auth.recognition_number, props.auth.password).pipe(
                map((response) => {
                    this.router.navigate(['/auth/login']);
                    return AuthActions.registerSuccess({ user: {} });
                }),
                catchError(error => of(AuthActions.registerFailure))
            ))
        )
    )

}