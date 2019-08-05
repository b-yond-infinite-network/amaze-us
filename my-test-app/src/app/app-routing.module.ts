import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ViewUsersComponent }   from './view-users/view-users.component';
import { AddUserComponent }      from './add-user/add-user.component';

const routes: Routes = [
  { path: '', redirectTo: '/viewUsers', pathMatch: 'full' },
  { path: 'viewUsers', component: ViewUsersComponent },
  { path: 'addUser', component: AddUserComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
