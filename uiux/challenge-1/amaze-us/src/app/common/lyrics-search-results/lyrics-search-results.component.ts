import { Component, OnInit } from '@angular/core';

import { MusixmatchService } from 'src/app/musixmatch.service';

@Component({
  selector: 'app-lyrics-search-results',
  templateUrl: './lyrics-search-results.component.html',
  styleUrls: ['./lyrics-search-results.component.scss']
})
export class LyricsSearchResultsComponent implements OnInit {

  results = [];
  
  constructor(private musixmatch:MusixmatchService) { }

  ngOnInit() {

    let results$ = this.musixmatch.subscribe(results => {
      this.results = results;
    });    
  }

}
