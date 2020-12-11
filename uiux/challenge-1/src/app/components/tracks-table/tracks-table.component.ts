import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { TrackModel } from 'src/app/models/track.model';
import { MusixmatchService } from 'src/app/services/musixmatch.service';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-tracks-table',
  templateUrl: './tracks-table.component.html',
  styleUrls: ['./tracks-table.component.scss']
})

export class TracksTableComponent implements OnInit {

  dataTable: any;
  dtOptions: any;
  tableData = [];
  @ViewChild('dataTable', {static: true}) table;

  @ViewChild(DataTableDirective, {static : false})
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();

  constructor(private musicMatchService: MusixmatchService) {}

  tacksByArtist: TrackModel[] = [];

  @Input('tacksByArtist')
  set _selected(traks: any[]) {
    this.tacksByArtist = traks;
  }

  ngOnDestroy() {
    this.dtTrigger.unsubscribe();
  }
  ngAfterViewInit() {
    this.dtTrigger.next();
  }

  selectedTrack: TrackModel = null;
  selectedLyrics = '';
  showingModal = false;

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: false
    };
  }

  ngOnChanges() {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: false
    };
    this.tableData = this.tacksByArtist;
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
