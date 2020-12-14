import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-field',
  templateUrl: './field.component.html',
  styleUrls: ['./field.component.scss']
})
export class FieldComponent {

  @Input()
  type: string;

  @Input()
  icon: any;

  @Input()
  placeholder: string;

  @Input()
  controller: any;

  @Output()
  onInputChange = new EventEmitter();

  public inputHasChange(e) {
    console.log(e);
  }
}
