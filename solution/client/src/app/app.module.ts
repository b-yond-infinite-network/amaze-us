import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { SearchModule } from './search/search.module';
import { ArtistModule } from './artist/artist.module';
import { LyricsModule } from './lyrics/lyrics.module';

import { SharedModule } from './shared/shared.module';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgHttpLoaderModule } from 'ng-http-loader';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    ArtistModule,
    LyricsModule,
    BrowserAnimationsModule,
    NgHttpLoaderModule.forRoot(),
    CoreModule,  // Singleton objects (services, components that are loaded only once)
    AppRoutingModule,
    // feature modules which can be lazy loaded
    SearchModule,
    SharedModule // reusable static objects
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
