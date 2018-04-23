import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {MatListModule} from '@angular/material/list';
import {MatChipsModule} from '@angular/material/chips';
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import {MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MomentModule} from 'ngx-moment';


import {routing} from "./routing";

import {AppComponent} from "./component_app";
import {TracksComponent} from "./component_tracks";
import {LyricsComponent} from "./component_lyrics";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        routing,
        BrowserAnimationsModule,
        MatInputModule,
        MatFormFieldModule,
        MatButtonModule,
        MatListModule,
        MatChipsModule,
        InfiniteScrollModule,
        MatCardModule,
        MatToolbarModule,
        MomentModule
    ],
    declarations: [
        AppComponent,
        TracksComponent,
        LyricsComponent
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {

}
