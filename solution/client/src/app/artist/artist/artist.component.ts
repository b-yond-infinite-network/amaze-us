import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/core/service';

@Component({
  selector: 'app-artist',
  templateUrl: './artist.component.html'
})
export class ArtistComponent implements OnInit {

  constructor(private apiService: ApiService) { }

  ngOnInit() {
  }

  noSpecial: RegExp = /^[a-z\d\-_\s]+$/;
  query: string;
  showDialogue: boolean = false;
  data: any;
  total: number;


  getSearchResults($event) {

    if (this.query) {
      this.apiService.searchArtist({ query: this.query, page: $event && $event.first }).subscribe(({ data, total }) => {
        console.log(data)
        this.data = data;
        this.total = total
        this.showDialogue = true;
      })
    }
  }
}
