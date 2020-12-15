import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { faUserTag, faLock } from '@fortawesome/free-solid-svg-icons';
import { Store } from '@ngrx/store';
import { FormValidator } from 'src/app/shared/helpers/formValidator.helper';
import { login } from '../../store/actions/auth.actions';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(
    private formValidator: FormValidator,
    private store: Store,
    private router: Router
  ) { }

  public loginForm = new FormGroup({
    recognitionNumber: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  });

  // Icons
  public faLock = faLock;
  public faUserTag = faUserTag;

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.router.navigate(['/auth/home']);
    }
  }

  get recognitionNumberControl() {
    return this.loginForm.get('recognitionNumber');
  }

  get passwordControl() {
    return this.loginForm.get('password');
  }

  get recognitionNumberError() {
    return this.recognitionNumberControl.touched && !this.recognitionNumberControl.valid; 
  }

  get passwordFieldError() {
    return this.passwordControl.touched && !this.passwordControl.valid; 
  }

  login() {
    if (!this.formValidator.isFormValid(this.loginForm)) { return; }
    const credentials = {
      recognitionNumber: this.recognitionNumberControl.value,
      password: this.passwordControl.value
    }
    this.store.dispatch(login({ credentials }))
  }
}
