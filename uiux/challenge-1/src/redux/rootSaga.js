import { all } from 'redux-saga/effects';
import { getArtistsListWatcher } from './../services/artistsService/artistsSaga';
import { getTracksListWatcher } from './../services/tracksService/tracksSaga';
import {getLyricsWatcher} from './../services/lyricsService/lyricsSaga';

export default function* rootSage() {
    yield all([
        getArtistsListWatcher(),
        getTracksListWatcher(),
        getLyricsWatcher(),
    ])
}