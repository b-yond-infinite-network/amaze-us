import { Component, OnInit } from '@angular/core';
import { UsersService } from '../service/users.service';
import { User } from '../model/user';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.css']
})
export class ViewUsersComponent implements OnInit {

  userList: Array<User>;
  loading: boolean;
  error: boolean;

  constructor(private usersService: UsersService) {

  }

  ngOnInit() {
    this.loading = true;
    this.error = false;
    this.usersService.getUsers()
    .subscribe(users => {
        this.loading = false;
        this.error = false;
        this.userList = users
    },
    error => {
        this.loading = false;
        this.error = true;
    });
  }

}
