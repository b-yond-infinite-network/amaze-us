import { takeLatest, put, call } from "@redux-saga/core/effects";
import { GET_ARTISTS_LIST_REQUEST } from './artistsActionTypes';
import { getArtistsListSuccess, getArtistsListError } from './artistsActions';
import { fetchArtistsList } from './artistsApi';

export function* getArtistsListWatcher() {
    try {
        yield takeLatest(GET_ARTISTS_LIST_REQUEST, getArtistsListWorker);
    } catch (error) {
        yield put(getArtistsListError(error));
    }
}
export function* getArtistsListWorker(action) {
    try {
        const { keyword } = action;
        const response = yield call(fetchArtistsList, keyword);

        if (response.status === 200 && response.data.message.body.artist_list) {
            const artistList = yield response.data.message.body.artist_list.map((value => value.artist));
            yield put(getArtistsListSuccess(artistList));
        } else {
            yield put(getArtistsListSuccess([]));
        }
    } catch (error) {
        yield put(getArtistsListError(error));
    }
}