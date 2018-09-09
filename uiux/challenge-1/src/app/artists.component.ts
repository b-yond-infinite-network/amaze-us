import { Component, Input } from '@angular/core';
import { ArtistsService } from './artists.service';
import { LyricsService } from './lyrics.service';

@Component({
    selector: 'artists',
    templateUrl: './artists.component.html'
})
export class ArtistsComponent {
    artist;
    artists: any;
    lyrics: any;
    trackName:any;

    constructor(private artistsService: ArtistsService,
        private lyricsService: LyricsService) {

    }

    onKeyUp(artistName, sortOrder) {
        this.artistsService.getArtists(artistName, sortOrder).subscribe(
            data => { this.artists = data },
            err => console.error(err));
    }

    onClickTrack(trackId,trackName) {
        this.trackName =trackName;
        this.lyricsService.getLyrics(trackId).subscribe(
            data => { this.lyrics = data },
            err => console.error(err));
    }
}