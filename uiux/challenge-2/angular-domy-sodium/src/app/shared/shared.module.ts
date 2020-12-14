import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Redux
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import * as fromShared from './store/reducers';

// Components
import { FieldComponent } from './components/field/field.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    FieldComponent
  ],
  exports: [
    FieldComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    StoreModule.forFeature(fromShared.authFeatureKey, fromShared.reducers),
    EffectsModule.forFeature([
    ])
  ]
})
export class SharedModule { }
