import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from 'src/app/core/service';
import { Track } from 'src/app/core/models/track';

@Component({
  selector: 'app-search-container',
  templateUrl: './search-container.component.html'
})
export class SearchContainerComponent implements OnInit {
  query;
  tracks: Track[] = [];
  total: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private apiService: ApiService
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.query = params['s'];
      if (!this.query) {
        this.router.navigate(['/'])
        return;
      }
      this.getResult()
    });
  }

  navigateToLyric(trackId) {
    return this.router.navigate(['lyric', trackId])
  }

  getResult($event: any = {}) {
   const { page = 1 } = $event ;
    this.apiService.searchTrack({ query: this.query, page: (page+1) }).subscribe(({ data, total }) => {
      this.tracks = data;
      this.total = total
    })
  }

}
