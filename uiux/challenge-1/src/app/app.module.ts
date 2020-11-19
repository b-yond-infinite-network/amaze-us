import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SearchLyricsComponent } from './components/search-lyrics/search-lyrics.component';
import { HttpClientModule , HttpClientJsonpModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { TracksTableComponent } from './components/tracks-table/tracks-table.component';
import { DataTablesModule } from 'angular-datatables';
import { ModalLyricsComponent } from './components/modal-lyrics/modal-lyrics.component';

@NgModule({
  declarations: [
    AppComponent,
    SearchLyricsComponent,
    TracksTableComponent,
    ModalLyricsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    HttpClientJsonpModule,
    FormsModule,
    DataTablesModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
