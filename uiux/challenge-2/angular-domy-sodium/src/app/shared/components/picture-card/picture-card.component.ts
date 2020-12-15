import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-picture-card',
  templateUrl: './picture-card.component.html',
  styleUrls: ['./picture-card.component.scss']
})
export class PictureCardComponent implements OnInit {

  @Input() title: string;
  @Input() image: string;
  @Input() icon;

  constructor() { }

  ngOnInit(): void {
  }

}
