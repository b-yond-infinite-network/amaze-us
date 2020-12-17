import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Redux
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import * as fromWater from './store/reducers';
import { WaterPanelComponent } from './components/water-panel/water-panel.component';
import { WaterRoutingModule } from './water-routing.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { WaterEffect } from './store/effects/water.effec';
import { TranslateModule } from '@ngx-translate/core';
import { CustomTranslationLoader } from 'src/factories/translate.factory';

@NgModule({
  declarations: [
    WaterPanelComponent
  ],
  imports: [
    CommonModule,
    FontAwesomeModule,
    StoreModule.forFeature(fromWater.waterFeatureKey, fromWater.reducers),
    EffectsModule.forFeature([
      WaterEffect
    ]),
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: { provide: TranslateModule, useClass: CustomTranslationLoader }
    }),
    WaterRoutingModule
  ]
})
export class WaterModule { }
