import { Component, OnInit, Input } from '@angular/core';
import { TrackModel } from 'src/app/models/track.model';
import { MusixmatchService } from 'src/app/services/musixmatch.service';

@Component({
  selector: 'app-tracks-table',
  templateUrl: './tracks-table.component.html',
  styleUrls: ['./tracks-table.component.scss']
})
export class TracksTableComponent implements OnInit {

  constructor(private musicMatchService: MusixmatchService) {}

  @Input() tacksByArtist: TrackModel[] = [];
  dtOptions: DataTables.Settings = {};
  selectedTrack: TrackModel = null;
  selectedLyrics: string = "";
  showingModal = false;

  ngOnInit(): void {

    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: true
    };
  }

  selectTrack(track) {
    this.selectedTrack = track;
    this.showingModal = true;
    this.musicMatchService.getLyricsByTrack(track.track_id).subscribe((data) => {
      this.selectedLyrics = data;
    });
  }

  onHide(event) {
    this.showingModal = event;
  }
}
