import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule }    from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { ViewUsersComponent } from './view-users/view-users.component';
import { AddUserComponent } from './add-user/add-user.component';
import { AppRoutingModule } from './/app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    ViewUsersComponent,
    AddUserComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
