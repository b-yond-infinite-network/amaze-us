import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { TrackModel } from 'src/app/models/track.model';
import { MusixmatchService } from 'src/app/services/musixmatch.service';

@Component({
  selector: 'app-search-lyrics',
  templateUrl: './search-lyrics.component.html',
  styleUrls: ['./search-lyrics.component.scss']
})
export class SearchLyricsComponent implements OnInit {

  inputArtist : String = '';
  constructor( private musicMatchService: MusixmatchService) { }

  @Output() tacksByArtist = new EventEmitter<TrackModel>();

  ngOnInit(): void {
  }

  onSearchTracks(){
    if(this.inputArtist){
      this.musicMatchService.getTracksByArtist(this.inputArtist).subscribe((data) => {
        this.tacksByArtist.emit(data);
      });
    }
  }
}
