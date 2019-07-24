import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StorageServiceModule } from 'ngx-webstorage-service';

import { LyricSearchBarComponent } from './common/lyric-search-bar/lyric-search-bar.component';
import { MusixmatchService } from './musixmatch.service';
import { LyricsSearchResultsComponent } from './common/lyrics-search-results/lyrics-search-results.component';

@NgModule({
  declarations: [
    AppComponent,
    LyricSearchBarComponent,
    LyricsSearchResultsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpClientJsonpModule,
    AppRoutingModule,
    ReactiveFormsModule,
    StorageServiceModule
  ],
  providers: [
    MusixmatchService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
