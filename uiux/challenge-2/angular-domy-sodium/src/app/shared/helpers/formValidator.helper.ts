import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Injectable({
    providedIn: 'root'
})
export class FormValidator {

    isFormValid(form: FormGroup) {
        this.touchControls(form);
        for (const key in form.controls) {
            const control = form.controls[key]
            if (!control.valid) { return false; }
        }
        return true;
    }

    touchControls(form: FormGroup) {
        for (const key in form.controls) {
            const control = form.controls[key];
            control.markAsTouched();
        }
    }
}