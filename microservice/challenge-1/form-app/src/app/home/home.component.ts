import { Component, OnInit } from '@angular/core';

import { FormServiceService } from '../form-service.service';
import { FormModel } from '../form-model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  exception: boolean;
  formdata: Array<FormModel>;

  constructor(private formService: FormServiceService) { }

  ngOnInit() {
    this.exception = false;
    this.formService.getFormData().subscribe(formd => {
      this.exception = false;
      this.formdata = formd;
    }, error => {
      this.exception = true;
    })
  }

}
