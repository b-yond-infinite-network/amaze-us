import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-lyric-viewer',
  templateUrl: './lyric-viewer.component.html',
  styleUrls: ['./lyric-viewer.component.scss']
})
export class LyricViewerComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
  }

}
