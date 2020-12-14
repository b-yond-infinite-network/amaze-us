import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { faLock } from '@fortawesome/free-solid-svg-icons';
import { Store } from '@ngrx/store';
import { FormValidator } from 'src/app/shared/helpers/formValidator.helper';
import { AuthenticationService } from '../../services/authentication.service';
import { register } from '../../store/actions/auth.actions';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public registerForm = new FormGroup({
    recognitionNumber: new FormControl(''),
    password: new FormControl('')
  });

  // Icons
  public faLock = faLock;

  public isValidated = false;

  constructor(
    private formValidator: FormValidator,
    private authService: AuthenticationService,
    private store: Store
  ) { }

  get recognitionNumberControl() {
    return this.registerForm.get('recognitionNumber');
  }

  get passwordControl() {
    return this.registerForm.get('password');
  }

  async validate() {
    if (!this.formValidator.isFormValid(this.registerForm)) { return; }
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
