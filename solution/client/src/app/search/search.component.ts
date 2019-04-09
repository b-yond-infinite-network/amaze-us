import { Component, OnInit } from '@angular/core';
import { ApiService } from './../core/service/api.service'

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html'
})
export class SearchComponent implements OnInit {

  constructor(private apiService: ApiService) { }
  // search alphanumeric,-,_ and space
  noSpecial: RegExp = /^[a-z\d\-_\s]+$/;
  query: string;
  showDialogue: boolean = false;
  data: any;
  total: number;

  ngOnInit() {
  }

  getSearchResults($event) {
    console.log($event);

    if (this.query) {
      this.apiService.searchTrack({ query: this.query, page: $event && $event.first }).subscribe(({ data, total }) => {
        this.data = data;
        this.total = total
        this.showDialogue = true;
      })
    }
  }

}
