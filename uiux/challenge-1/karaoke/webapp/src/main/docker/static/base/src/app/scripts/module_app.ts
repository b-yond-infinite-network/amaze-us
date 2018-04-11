import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from '@angular/common/http';

import {routing} from "./routing";

import {AppComponent} from "./component_app";
import {TracksComponent} from "./component_tracks";
import {LyricsComponent} from "./component_lyrics";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        routing
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
