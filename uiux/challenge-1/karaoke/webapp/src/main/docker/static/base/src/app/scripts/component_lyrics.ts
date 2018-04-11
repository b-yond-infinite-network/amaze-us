import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {Lyrics, TracksService} from "./service_tracks";
import {Router} from '@angular/router';

@Component({
    selector: "div[data-karaoke-lyrics]",
    templateUrl: "../templates/component_lyrics.pug",
    styleUrls: ["../styles/component_lyrics.sass"],
    encapsulation: ViewEncapsulation.None,
    providers: [TracksService]
})
export class LyricsComponent implements OnInit {
    lyrics: Lyrics = new Lyrics();

    constructor(private tracksService: TracksService, private router: Router) {
    }

    getLyrics(): void {
        this.router.routerState.root.firstChild.params.subscribe(params => {
            this.tracksService.getLyrics(params['track_id']).subscribe(lyrics => this.lyrics = lyrics);
        });
    }

    ngOnInit() {
        this.getLyrics();
    }

}

