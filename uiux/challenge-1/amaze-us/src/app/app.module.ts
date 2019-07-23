import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { LyricSearchBarComponent } from './common/lyric-search-bar/lyric-search-bar.component';
import { MusixmatchService } from './musixmatch.service';


@NgModule({
  declarations: [
    AppComponent,
    LyricSearchBarComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpClientJsonpModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [
    MusixmatchService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
