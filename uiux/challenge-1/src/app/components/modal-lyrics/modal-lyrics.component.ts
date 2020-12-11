import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TrackModel } from 'src/app/models/track.model';

@Component({
  selector: 'app-modal-lyrics',
  templateUrl: './modal-lyrics.component.html',
  styleUrls: ['./modal-lyrics.component.scss']
})
export class ModalLyricsComponent implements OnInit {

  @Input() trackObj: TrackModel = null;
  @Input() showModal = false;
  @Input() lyrics = '';

  @Output() toggleShow = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit(): void {
  }

  hide() {
    this.showModal = false;
    this.toggleShow.emit(this.showModal);
  }
}
