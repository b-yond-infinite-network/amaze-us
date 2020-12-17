import { Route } from '@angular/compiler/src/core';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faAppleAlt, faTint, faUser, faUsers } from '@fortawesome/free-solid-svg-icons';
import { select, Store } from '@ngrx/store';
import { Observable, Subject } from 'rxjs';
import { mergeMap, take, takeUntil } from 'rxjs/operators';
import * as fromAuth from 'src/app/auth/store/reducers';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  constructor(
    private store: Store,
    private router: Router
  ) { }

  private user$: Observable<any> = this.store.pipe(select(fromAuth.getUser));
  private features$: Observable<any> = this.store.pipe(select(fromAuth.getFeatures));
  private readonly SCREEN_MIN_WIDTH = 1024;
  private ngUnsuscribe = new Subject();

  public user;
  public faUser = faUser;
  public faAppleAlt = faAppleAlt;
  public faTint = faTint;
  public faUsers = faUsers;
  public displayPicture: boolean;
  public title: string;

  ngOnInit(): void {
    this.displayPicture = window.innerWidth >= this.SCREEN_MIN_WIDTH;
    this.user$.pipe(takeUntil(this.ngUnsuscribe)).subscribe(user => { 
      this.user = user 
      this.title = `Welcome ${user.recognition_number}`;
    });
    this.features$.pipe(takeUntil(this.ngUnsuscribe)).subscribe(features => { this.user = features });
  }

  ngOnDestroy(): void {
    this.ngUnsuscribe.next();
  }

  redirect(url: string) {
    this.router.navigate([url]);
  }

}
 