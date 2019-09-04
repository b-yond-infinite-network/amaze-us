import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';

import { FormServiceService } from '../form-service.service';
import { FormModel } from '../form-model';

@Component({
  selector: 'app-contactus',
  templateUrl: './contactus.component.html',
  styleUrls: ['./contactus.component.css']
})
export class ContactusComponent implements OnInit {

  exception: boolean;
  formmodel : FormModel;
  contactUsForm: FormGroup;

  constructor(private router: Router, private formService: FormServiceService) { }

  reset() {
    this.contactUsForm = new FormGroup({
      name: new FormControl(''),
      email: new FormControl(''),
      description: new FormControl('')
    });
  }

  ngOnInit() {
    this.exception = false;
    this.reset();

  }

  submittingForm() {
    this.exception = false;
    this.formmodel = new FormModel(this.contactUsForm.value);
    console.log(this.formmodel);
    this.formService.saveFormData(this.formmodel).subscribe(() => {
      this.router.navigate(['/home']);
    }, error => {
      this.router.navigate(['/home']);
      this.exception = false;
    });

  }

 

}
