import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Store } from '@ngrx/store';
import { FormValidator } from 'src/app/shared/helpers/formValidator.helper';
import { createPlantation } from '../../store/actions/plantation.action';

@Component({
  selector: 'app-plantation-form',
  templateUrl: './plantation-form.component.html',
  styleUrls: ['./plantation-form.component.scss']
})
export class PlantationFormComponent implements OnInit {

  constructor(
    private modal: NgbActiveModal,
    private formValidator: FormValidator,
    private store: Store
  ) { }

  public plantationForm = new FormGroup({
    seedName: new FormControl('', [Validators.required]),
    seedId: new FormControl('', [Validators.required]),
    amount: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]+$/)]),
    readyOnDays: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]+$/)]),
  });

  ngOnInit(): void {
  }


  close(cancel = false) {
    if (cancel) { return this.modal.dismiss(''); }

    if (!this.formValidator.isFormValid(this.plantationForm)) { return; }

    const data = {
      seedName: this.SeedNameControl.value,
      seedId: this.SeedIdControl.value,
      amount: this.amountControl.value,
      readyOnDays: this.readyOnDaysControl.value
    }
    this.store.dispatch(createPlantation({ plantation: data }))
    this.modal.dismiss(data);
  }

  get SeedNameControl() {
    return this.plantationForm.get('seedName');
  }

  get SeedIdControl() {
    return this.plantationForm.get('seedId');
  }

  get amountControl() {
    return this.plantationForm.get('amount');
  }

  get readyOnDaysControl() {
    return this.plantationForm.get('readyOnDays');
  }

}
