import { takeLatest, put, call } from "@redux-saga/core/effects";
import { GET_LYRICS_REQUEST } from './lyricsActionTypes';
import { getLyricsSuccess, getLyricsError } from './lyricsActions';
import { fetchLyrics } from './lyricsApi';



export function* getLyricsWatcher() {
    try {
        yield takeLatest(GET_LYRICS_REQUEST, getLyricsWorker);
    } catch (error) {
        yield put(getLyricsError(error));
    }
}

function* getLyricsWorker(action) {
    try {
        const { track_id } = action;
        const response = yield call(fetchLyrics, track_id);

        if (response.status === 200 && response.data.message.body.lyrics.lyrics_body !== "") {
            const lyrics = response.data.message.body.lyrics.lyrics_body;
            yield put(getLyricsSuccess(lyrics));
        }
    } catch (error) {
        yield put(getLyricsError(error));
    }
}