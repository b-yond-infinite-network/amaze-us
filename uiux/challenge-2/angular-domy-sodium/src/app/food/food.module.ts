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
import { PlantationEffect } from './store/effects/plantation.effect';
import { PlantationFormComponent } from './components/plantation-form/plantation-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FoodEffect } from './store/effects/food.effec';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    FoodPanelComponent,
    PlantationFormComponent
  ],
  imports: [
    FoodRoutingModule,
    CommonModule,
    FontAwesomeModule,
    NgbModule,
    ReactiveFormsModule,
    StoreModule.forFeature(fromFood.plantationFeatureKey, fromFood.reducers),
    EffectsModule.forFeature([
      PlantationEffect,
      FoodEffect
    ]),
    SharedModule,
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: { provide: TranslateModule, useClass: CustomTranslationLoader }
    }),
  ]
})
export class FoodModule { }
