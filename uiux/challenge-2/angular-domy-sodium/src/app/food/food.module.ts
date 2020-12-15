import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Redux
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import * as fromFood from './store/reducers';

// Translate
import { TranslateModule } from '@ngx-translate/core';
import { CustomTranslationLoader } from 'src/factories/translate.factory';

// Components
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { SharedModule } from '../shared/shared.module';
import { FoodRoutingModule } from './food-routing.module';
import { FoodPanelComponent } from './components/food-panel/food-panel.component';

@NgModule({
  declarations: [
    FoodPanelComponent
  ],
  imports: [
    FoodRoutingModule,
    CommonModule,
    FontAwesomeModule,
    StoreModule.forFeature(fromFood.authFeatureKey, fromFood.reducers),
    EffectsModule.forFeature([
    ]),
    SharedModule,
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: { provide: TranslateModule, useClass: CustomTranslationLoader }
    }),
  ]
})
export class FoodModule { }
