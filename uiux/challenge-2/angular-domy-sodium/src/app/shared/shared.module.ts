import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Redux
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import * as fromShared from './store/reducers';

// Components
import { FieldComponent } from './components/field/field.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PictureCardComponent } from './components/picture-card/picture-card.component';
import { TranslateModule } from '@ngx-translate/core';
import { CustomTranslationLoader } from 'src/factories/translate.factory';
import { DoughnutChartComponent } from './components/doughnut-chart/doughnut-chart.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

@NgModule({
  declarations: [
    DoughnutChartComponent,
    FieldComponent,
    NotFoundComponent,
    PictureCardComponent
  ],
  exports: [
    DoughnutChartComponent,
    FieldComponent,
    NotFoundComponent,
    PictureCardComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    StoreModule.forFeature(fromShared.authFeatureKey, fromShared.reducers),
    EffectsModule.forFeature([
    ]),
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: { provide: TranslateModule, useClass: CustomTranslationLoader }
    }),
  ]
})
export class SharedModule { }
