import { Route } from '@angular/compiler/src/core';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faAppleAlt, faTint, faUser, faUsers } from '@fortawesome/free-solid-svg-icons';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { mergeMap, take } from 'rxjs/operators';
import * as fromAuth from 'src/app/auth/store/reducers';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(
    private store: Store,
    private router: Router
  ) { }

  private user$: Observable<any> = this.store.pipe(select(fromAuth.getUser));
  private features$: Observable<any> = this.store.pipe(select(fromAuth.getFeatures));
  private readonly SCREEN_MIN_WIDTH = 1024;

  public user;
  public faUser = faUser;
  public faAppleAlt = faAppleAlt;
  public faTint = faTint;
  public faUsers = faUsers;
  public displayPicture: boolean;

  ngOnInit(): void {
    this.displayPicture = window.innerWidth >= this.SCREEN_MIN_WIDTH;
    this.user$.pipe(take(100)).subscribe(user => { console.log(user); this.user = user });
    this.features$.pipe(take(100)).subscribe(features => {console.log(features); this.user = features});
  }

  redirect(url: string) {
    
    this.router.navigate(['/food/home']);
  }

}
 