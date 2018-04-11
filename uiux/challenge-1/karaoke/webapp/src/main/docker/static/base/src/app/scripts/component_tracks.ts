import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {Track, TracksService} from "./service_tracks";
import {Router} from "@angular/router";
import {Subscription} from "rxjs/Subscription";

@Component({
    selector: "div[data-karaoke-tracks]",
    templateUrl: "../templates/component_home.pug",
    styleUrls: ["../styles/component_home.sass"],
    encapsulation: ViewEncapsulation.None,
    providers: [TracksService]
})
export class TracksComponent implements OnInit {
    page = 1;
    tracks: Track[] = [];
    filter = "";
    filterBackup = "";
    loading = false;
    orderByField = "title";
    ascending = true;
    runningQuery: Subscription;

    constructor(private tracksService: TracksService, private router: Router) {
    }

    getTracks(): void {
        if (this.runningQuery) {
            this.runningQuery.unsubscribe();
        }
        this.loading = true;
        if (this.filter && this.filter.trim() !== "") {
            this.runningQuery = this.tracksService.getTracks(
                this.filter.trim(),
                this.page,
                this.orderByField,
                this.ascending
            ).subscribe(tracks => {
                this.tracks = this.tracks.concat(tracks);
                this.loading = false;
            });
        } else {
            this.page = 1;
            this.tracks = [];
            this.loading = false;
        }
    }

    ngOnInit() {
        this.router.routerState.root.firstChild.params.subscribe(params => {
            if (params['artist_query']) {
                this.filter = params['artist_query'];
            }
            this.filterBackup = this.filter;
            this.getTracks();
        });
    }

    onEnter() {
        if (this.filterBackup === this.filter.slice()) {
            return; // nothing to load
        }
        this.page = 1;
        this.tracks = [];
        this.filterBackup = this.filter.slice();
        this.router.navigate(['tracks', this.filterBackup]);
    }

    loadMore() {
        this.page = this.page + 1;
        this.getTracks();
    }

    orderBy(fieldName: string) {
        if (this.orderByField !== fieldName) {
            this.ascending = true;
            this.orderByField = fieldName;
        } else {
            this.ascending = !this.ascending;

        }
        this.page = 1;
        this.tracks = [];
        this.getTracks();
    }
}
