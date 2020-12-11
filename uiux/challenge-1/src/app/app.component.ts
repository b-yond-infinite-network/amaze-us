import { Component, OnInit, Input } from '@angular/core';
import { environment } from '../environments/environment';
import { TrackModel } from './models/track.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'lyricsapp';
  appKey = environment.API_KEY;
  tracks: TrackModel[] = [];
  @Input() tacksByArtist: any;
  @Input() typedArtist: any;
  currentArtist = '';

  ngOnInit(): void {

  }

  onTypedArtist(event) {
    this.currentArtist = event;
  }

  onSearchTracks(event) {
    this.tracks = event ? event : [];
  }
}
