import { Component, OnInit } from '@angular/core';
import { MusixmatchService } from 'src/app/musixmatch.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { distinctUntilChanged, debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-lyric-search-bar',
  templateUrl: './lyric-search-bar.component.html',
  styleUrls: ['./lyric-search-bar.component.scss']
})
export class LyricSearchBarComponent implements OnInit {

  searchLyricsForm: FormGroup;

  constructor(private musixmatch:MusixmatchService, private fb:FormBuilder) { }

  ngOnInit() {
    this.searchLyricsForm = this.fb.group({
      searchLyricsTerm: ''
    });

    this.searchLyricsForm.get('searchLyricsTerm').valueChanges.pipe(
      debounceTime(1000),
      distinctUntilChanged()
    ).subscribe(term => this.musixmatch.search(term));
  }

}
