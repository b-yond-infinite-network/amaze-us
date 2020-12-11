import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { TrackModel } from 'src/app/models/track.model';
import { MusixmatchService } from 'src/app/services/musixmatch.service';

@Component({
  selector: 'app-search-lyrics',
  templateUrl: './search-lyrics.component.html',
  styleUrls: ['./search-lyrics.component.scss']
})
export class SearchLyricsComponent implements OnInit {

  inputArtist = '';

  constructor( private musicMatchService: MusixmatchService) { }

  @Output() tacksByArtist = new EventEmitter<TrackModel>();
  @Output() typedArtist = new EventEmitter<string>();

  ngOnInit(): void {
  }

  onSearchTracks() {
    this.typedArtist.emit(this.inputArtist);
    if (this.inputArtist) {
      this.tacksByArtist.emit(null);
      this.musicMatchService.getTracksByArtist(this.inputArtist).subscribe((data) => {
        this.tacksByArtist.emit(data);
      });
    }
  }
}
