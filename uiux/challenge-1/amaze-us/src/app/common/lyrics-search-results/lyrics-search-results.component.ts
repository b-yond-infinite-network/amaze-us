import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { MusixmatchService } from 'src/app/musixmatch.service';

@Component({
  selector: 'app-lyrics-search-results',
  templateUrl: './lyrics-search-results.component.html',
  styleUrls: ['./lyrics-search-results.component.scss']
})
export class LyricsSearchResultsComponent implements OnInit {

  @ViewChild(MatSort, { static: true }) sort: MatSort;

  displayedColumns: string[] = ['has_lyrics', 'track_name', 'artist_name', 'album_name'];
  results = new MatTableDataSource([]);

  constructor(private musixmatch: MusixmatchService) { }

  ngOnInit() {
    let results$ = this.musixmatch.subscribe(data => {
      this.results = new MatTableDataSource(data);
      this.results.sort = this.sort;
    });
  }

}
