import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, ObservableInput } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private Router: Router
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
    return next.handle(request).pipe(
      tap(() => {}),
      catchError((error: HttpErrorResponse, caught: Observable<any>): ObservableInput<any> => {
        if (error.status === 401 || error.status === 403) {
          localStorage.removeItem('token');
          return this.Router.navigate(['/auth/login']);
        }

        return next.handle(request);
      })
    )
  }
}