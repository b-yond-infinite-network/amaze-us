// import { Component, Input } from '@angular/core';
// import { LyricsService } from './lyrics.service';

// @Component({
//     selector: 'lyrics-display',
//     templateUrl: './lyrics.component.html'
// })

// export class LyricsComponent {

//     @Input('lyrics') lyricsResult: string;
//     lyrics:any;

//     constructor(private lyricsService:LyricsService) 
//     { 
//         this.lyrics = this.getLyrics();
//     }


//     getLyrics() {
//         this.lyricsService.getLyrics(this.trackId).subscribe(
//             data => { this.lyrics = data });
//     }
// }