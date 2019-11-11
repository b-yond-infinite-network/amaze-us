import { takeLatest, put, call } from "@redux-saga/core/effects";
import { GET_TRACKS_LIST_REQUEST } from './tracksActionTypes';
import { getTracksListSuccess, getTracksListError } from './tracksActions';
import { fetchTracksList } from './tracksApi';



export function* getTracksListWatcher() {
    try {
        yield takeLatest(GET_TRACKS_LIST_REQUEST, getTracksListWorker);
    } catch (error) {
        yield put(getTracksListError(error));
    }
}

function* getTracksListWorker(action) {
    try {
        const { artist_id } = action;
        const response = yield call(fetchTracksList, artist_id);

        if (response.status === 200 && response.data.message.body.track_list) {
            const trackList = yield response.data.message.body.track_list.map((value => value.track)).filter((track => track.has_lyrics >= 1));
            yield put(getTracksListSuccess(trackList));
        }
    } catch (error) {
        yield put(getTracksListError(error));
    }
}