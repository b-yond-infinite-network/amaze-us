import reducer from './../artistsReducer';
import * as artistsActions from './../artistsActions';

describe("Artists reducer", () => {
    it("Should return initial state", () => {
        const initalState = {};
        expect(reducer(undefined, {})).toEqual(initalState);
    });

    it("Should handle GET_ARTISTS_LIST_REQUEST", () => {
        const result = {
            isLoading: true,
        };
        expect(reducer([], artistsActions.getArtistsList(""))).toEqual(result);
    });

    it("Should handle GET_ARTISTS_LIST_SUCCESS", () => {
        const artistList = [
            { artist_id: 1, artist_name: "Artist 1" },
            { artist_id: 2, artist_name: "Artist 2" },
            { artist_id: 3, artist_name: "Artist 3" }
        ];
        const result = {
            isLoading: false,
            artistList
        };
        expect(reducer([], artistsActions.getArtistsListSuccess(artistList))).toEqual(result);
    });

    
    it("Should handle GET_ARTISTS_LIST_ERROR", () => {
        const errors = {code: 404, message: "Not found"}
        const result = {
            isLoading: false,
            errors
        };
        expect(reducer([], artistsActions.getArtistsListError(errors))).toEqual(result);
    });

});