import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { faLock } from '@fortawesome/free-solid-svg-icons';
import { FormValidator } from 'src/app/shared/helpers/formValidator.helper';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public registerForm = new FormGroup({
    recognitionNumber: new FormControl(''),
  });

  // Icons
  public faLock = faLock;
  
  constructor(
    private formValidator: FormValidator,
    private authService: AuthenticationService,
    private router: Router
  ) { }

  get recognitionNumberControl() {
    return this.registerForm.get('recognitionNumber');
  }

  async register() {
    if (!this.formValidator.isFormValid(this.registerForm)) { return; }
    try {
      const response = await this.authService.checkPreRegisterUser(this.recognitionNumberControl.value).toPromise();
      if(!response.success) { return; }

      this.router.navigate(['/auth/register_confirmation', { recognitionNumber: this.recognitionNumberControl.value }]);
    } catch(e) {
      return e;
    }
  }

}
