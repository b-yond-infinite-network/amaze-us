import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { faLock, faUserTag } from '@fortawesome/free-solid-svg-icons';
import { Store } from '@ngrx/store';
import { FormValidator } from 'src/app/shared/helpers/formValidator.helper';
import { AuthenticationService } from '../../services/authentication.service';
import { register } from '../../store/actions/auth.actions';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  public registerForm = new FormGroup({
    recognitionNumber: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  });

  // Icons
  public faUserTag = faUserTag;

  public isValidated = false;

  constructor(
    private formValidator: FormValidator,
    private authService: AuthenticationService,
    private store: Store,
    private router: Router
  ) { }

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.router.navigate(['/auth/home']);
    }
  }

  get recognitionNumberControl() {
    return this.registerForm.get('recognitionNumber');
  }

  get passwordControl() {
    return this.registerForm.get('password');
  }

  get recognitionNumberError() {
    return this.recognitionNumberControl.touched && !this.recognitionNumberControl.valid; 
  }

  get passwordFieldError() {
    return this.passwordControl.touched && !this.passwordControl.valid; 
  }

  async validate() {
    this.recognitionNumberControl.markAsTouched();
    if (this.recognitionNumberError) { return; }
    try {
      const response = await this.authService.checkPreRegisterUser(this.recognitionNumberControl.value).toPromise();
      if (!response.success) { return; }

      this.isValidated = true;
    } catch (e) {
      return e;
    }
  }

  async register() {
    if (!this.formValidator.isFormValid(this.registerForm)) { return; }

    const auth = { recognition_number: this.recognitionNumberControl.value, password: this.passwordControl.value };
    this.store.dispatch(register({ auth }));
  }

}
