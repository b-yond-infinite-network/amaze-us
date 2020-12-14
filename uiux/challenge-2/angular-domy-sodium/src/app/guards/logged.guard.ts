import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class IsLoggedGuard implements CanActivate {

    constructor(
        public router: Router
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
        return new Observable((observer) => {
            const token = localStorage.getItem('token')
            if(!token) {
                this.router.navigate(['/auth/login']);
                observer.next(false);
            }
            observer.next(true);
        });
    }
}
