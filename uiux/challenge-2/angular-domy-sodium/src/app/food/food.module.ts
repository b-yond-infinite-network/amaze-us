import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Redux
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import * as fromFood from './store/reducers';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    StoreModule.forFeature(fromFood.authFeatureKey, fromFood.reducers),
    EffectsModule.forFeature([
    ])
  ]
})
export class FoodModule { }
