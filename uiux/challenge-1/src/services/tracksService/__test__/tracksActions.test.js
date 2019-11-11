import * as tracksActions from './../tracksActions';
import * as tracksActionTypes from './../tracksActionTypes';

describe("Tracks actions", () => {
    it("Should create an action to get tracks by artist_id", () => {
        const artist_id = 1234;
        expect(tracksActions.getTracksList(artist_id)).toEqual({
            type: tracksActionTypes.GET_TRACKS_LIST_REQUEST,
            artist_id,
        });
    });

    it("Should create GET_TRACKS_LIST_SUCCESS when fetching list of tracks is done", () => {
        const trackList = [
            { track_id: 1, track_name: "Track 1" },
            { track_id: 2, track_name: "Track 2" },
            { track_id: 3, track_name: "Track 3" }
        ];
        expect(tracksActions.getTracksListSuccess(trackList)).toEqual({
            type: tracksActionTypes.GET_TRACKS_LIST_SUCCESS,
            trackList,
        });
    });

    it("Should create an action GET_TRACKS_LIST_ERROR with the errors if fetching of tracks fails", () => {
        const errors = {}
        expect(tracksActions.getTracksListError(errors)).toEqual({
            type: tracksActionTypes.GET_TRACKS_LIST_ERROR,
            errors
        });
    });

});