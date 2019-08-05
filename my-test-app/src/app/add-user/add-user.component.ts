import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { UsersService } from '../service/users.service';
import { User } from '../model/user';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

    user : User;
    error: boolean;
    userForm: FormGroup;

    constructor(private usersService: UsersService,
                private router: Router) {

    }

     ngOnInit() {
        this.error = false;
        this.userForm = new FormGroup({
                name: new FormControl(''),
                email: new FormControl(''),
                description: new FormControl(''),
              });
     }



    onSubmit() {
        this.error = false;
        this.user = new User(this.userForm.value);
        this.usersService.addUser(this.user).subscribe(() => {
            this.router.navigate(['/viewUsers']);
        },
        error => this.error = true);
    }

}
