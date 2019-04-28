import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html'
})
export class SearchBarComponent {

  noSpecial: RegExp = /^[a-z\d\-_\s]+$/;
  query: string;

  constructor(private router: Router) { }

  search() {
    return this.router.navigate(['/search/list'],  { queryParams: { s: this.query }});
  }
}
