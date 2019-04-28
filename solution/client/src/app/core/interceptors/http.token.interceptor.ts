import { Injectable, Injector } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';


@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {
    constructor(private router: Router) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const headersConfig = {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        };

        const request = req.clone({ setHeaders: headersConfig });
        return next.handle(request).pipe(
            map((event: HttpEvent<any>) => {

              return event;
            }),

            catchError((err: HttpErrorResponse) => {
              if (err instanceof HttpErrorResponse) {
                this.handleErrors(err);
              }

              return throwError(err);
            })
          );;
    }

    private handleErrors(response: HttpErrorResponse) {
        if (response.status === 0) {
            this.router.navigate(['/server-error'])
        }
      }
}