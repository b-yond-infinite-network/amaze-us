import {RouterModule} from "@angular/router";
import {TracksComponent} from "./component_tracks";
import {LyricsComponent} from "./component_lyrics";

export const routing = RouterModule.forRoot([
    {
        path: "",
        component: TracksComponent
    },
    {
        path: "tracks/:artist_query",
        component: TracksComponent
    },
    {
        path: 'lyrics/:track_id',
        component: LyricsComponent
    },
]);
