// Basics
import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { mergeMap, catchError, map, tap, take } from 'rxjs/operators';

// Services
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';

// Actions
import { AuthActions } from '../actions';

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
            mergeMap(action => this.authenticationService.login(action.credentials).pipe(
                map((response) => {
                    localStorage.setItem('token', response.metadata.token);
                    this.router.navigate(['/auth/home']);
                    return AuthActions.loginSuccess({ user: response.user });
                }),
                catchError(error => of(AuthActions.loginFailure(error)))
            ))
        )
    );

}