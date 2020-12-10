import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Redux
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import * as fromWater from './store/reducers';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    StoreModule.forFeature(fromWater.authFeatureKey, fromWater.reducers),
    EffectsModule.forFeature([
    ])
  ]
})
export class WaterModule { }
