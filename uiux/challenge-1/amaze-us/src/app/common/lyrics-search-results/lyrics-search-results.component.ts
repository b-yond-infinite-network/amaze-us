import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { MusixmatchService } from 'src/app/musixmatch.service';
import { LyricViewerComponent } from '../lyric-viewer/lyric-viewer.component';

@Component({
  selector: 'app-lyrics-search-results',
  templateUrl: './lyrics-search-results.component.html',
  styleUrls: ['./lyrics-search-results.component.scss']
})
export class LyricsSearchResultsComponent implements OnInit {

  @ViewChild(MatSort, { static: true }) sort: MatSort;

  displayedColumns: string[] = ['has_lyrics', 'track_name', 'artist_name', 'album_name'];
  results = new MatTableDataSource([]);
  dialogRef;
  isGettingLyric;
  currentTrack;

  constructor(private musixmatch: MusixmatchService, private dialog: MatDialog, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.musixmatch.subscribe(data => {
      this.results = new MatTableDataSource(data);
      this.results.sort = this.sort;
    });

    // Display lyric's modal
    this.musixmatch.subscribeLyric(lyric => {
      this.dialogRef = this.dialog.open(LyricViewerComponent, {
        data: {
          lyric: lyric.lyrics_body,
          trackName: this.currentTrack.track_name,
          artistName: this.currentTrack.artist_name
        }
      });
    })

    // Is loading status
    this.musixmatch.isGettingLyric(status => {
      this.isGettingLyric = status
    });
  }

  displayLyric(row) {
    if (this.isGettingLyric) {
      return;
    }

    if (!row.has_lyrics) {
      this.snackBar.open('No lyrics available.', 'Close', { duration: 1000 });
      return;
    }

    this.currentTrack = row;
    this.musixmatch.getLyricByTrackId(row.track_id);
  }
}