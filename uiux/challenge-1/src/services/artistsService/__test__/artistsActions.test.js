import * as artistsActions from './../artistsActions';
import * as artistsActionTypes from './../artistsActionTypes';

describe("Artists actions", () => {
    it("Should create an action to get artists by the keyword", () => {
        const keyword = "Artistname";
        expect(artistsActions.getArtistsList(keyword)).toEqual({
            type: artistsActionTypes.GET_ARTISTS_LIST_REQUEST,
            keyword,
        });
    });

    it("Should create GET_ARTISTS_LIST_SUCCESS when fetching list of artists is done", () => {
        const artistList = [
            { artist_id: 1, artist_name: "Artist 1" },
            { artist_id: 2, artist_name: "Artist 2" },
            { artist_id: 3, artist_name: "Artist 3" }
        ];
        expect(artistsActions.getArtistsListSuccess(artistList)).toEqual({
            type: artistsActionTypes.GET_ARTISTS_LIST_SUCCESS,
            artistList,
        });
    });

    it("Should create an action GET_ARTISTS_LIST_ERROR with the errors if fetching of artists fails", () => {
        const errors = {code: 404, message: "Not found"}
        expect(artistsActions.getArtistsListError(errors)).toEqual({
            type: artistsActionTypes.GET_ARTISTS_LIST_ERROR,
            errors
        });
    });

});