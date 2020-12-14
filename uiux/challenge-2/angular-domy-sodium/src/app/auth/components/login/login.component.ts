import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { faUserTag, faLock } from '@fortawesome/free-solid-svg-icons';
import { Store } from '@ngrx/store';
import { FormValidator } from 'src/app/shared/helpers/formValidator.helper';
import { login } from '../../store/actions/auth.actions';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(
    private formValidator: FormValidator,
    private store: Store
  ) { }

  public loginForm = new FormGroup({
    recognitionNumber: new FormControl(''),
    password: new FormControl('')
  });

  // Icons
  public faLock = faLock;
  public faUserTag = faUserTag;

  get recognitionNumberControl() {
    return this.loginForm.get('recognitionNumber');
  }

  get passwordControl() {
    return this.loginForm.get('password');
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
