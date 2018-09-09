import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ArtistsComponent } from './artists.component';
import { ArtistsService } from './artists.service';
import { HttpClientModule } from '@angular/common/http';
import { LyricsService } from './lyrics.service';
// import { LyricsComponent } from './lyrics.component';

@NgModule({
  declarations: [
    AppComponent,
    ArtistsComponent,
    // LyricsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    ArtistsService,
    LyricsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
