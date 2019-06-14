import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ChartsModule } from 'ng2-charts';
import { TopmoodsComponent } from './topmoods/topmoods.component';
import { MoodstatisticsComponent } from './moodstatistics/moodstatistics.component';

@NgModule({
  declarations: [
    AppComponent,
    TopmoodsComponent,
    MoodstatisticsComponent
  ],
  imports: [
    BrowserModule,
    ChartsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
