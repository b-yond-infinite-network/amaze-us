import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { ApiService } from 'src/app/core/service';
import { Lyrics } from 'src/app/core/models/lyrics';

@Component({
  selector: 'app-lyric',
  templateUrl: './lyric.component.html'
})
export class LyricComponent implements OnInit {

  constructor(private route: ActivatedRoute, private apiService: ApiService) { }
  lyrics: Lyrics;

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get("id")
      return this.apiService.getLyricsByTrackId(id).subscribe(val => {
        if (val) this.lyrics = new Lyrics(val);

      })
    })
  }

}
