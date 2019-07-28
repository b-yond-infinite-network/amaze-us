import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StorageServiceModule } from 'ngx-webstorage-service';
import { MatButtonModule, 
  MatInputModule,
  MatToolbarModule,
  MatTableModule,
  MatSortModule,
  MatProgressBarModule, 
  MatPaginatorModule, 
  MatIconModule,
  MatSnackBarModule,
  MatDialogModule} from '@angular/material';

import { LyricSearchBarComponent } from './common/lyric-search-bar/lyric-search-bar.component';
import { MusixmatchService } from './musixmatch.service';
import { LyricsSearchResultsComponent } from './common/lyrics-search-results/lyrics-search-results.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LyricViewerComponent } from './common/lyric-viewer/lyric-viewer.component';

@NgModule({
  declarations: [
    AppComponent,
    LyricSearchBarComponent,
    LyricsSearchResultsComponent,
    LyricViewerComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpClientJsonpModule,
    AppRoutingModule,
    ReactiveFormsModule,
    StorageServiceModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatInputModule,
    MatToolbarModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressBarModule,
    MatDialogModule
  ],
  providers: [
    MusixmatchService,
  ],
  entryComponents: [
    LyricViewerComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
