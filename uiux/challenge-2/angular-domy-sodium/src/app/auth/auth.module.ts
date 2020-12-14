import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';

//Routing
import { AuthRoutingModule } from './auth-routing.module';

// Redux
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import * as fromAuth from './store/reducers';
import { AuthEffects } from './store/effects/auth.effect';

// Components
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';

// Modules
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// Translate
import { TranslateModule } from '@ngx-translate/core';
import { CustomTranslationLoader } from 'src/factories/translate.factory';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    HomeComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    AuthRoutingModule,
    CommonModule,
    FontAwesomeModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    EffectsModule.forFeature([
      AuthEffects
    ]),
    StoreModule.forFeature(fromAuth.authFeatureKey, fromAuth.reducers),
    SharedModule,
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: { provide: TranslateModule, useClass: CustomTranslationLoader }
    }),
  ]
})
export class AuthModule { }
