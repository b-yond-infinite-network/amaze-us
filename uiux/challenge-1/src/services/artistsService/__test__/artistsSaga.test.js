import { configure } from "enzyme";

import Adapter from 'enzyme-adapter-react-16';
import { expectSaga } from "redux-saga-test-plan";
import reducer from './../artistsReducer';
import * as artistSaga from './../artistsSaga';
import { mockAction } from './../../../__mocks__/axios';

configure({ adapter: new Adapter() });

describe("Artists saga", () => {
    it("GET_ARTISTS_LIST_REQUEST", () => {
        return expectSaga(artistSaga.getArtistsListWatcher)
            .withReducer(reducer)
            .dispatch({
                type: "GET_ARTISTS_LIST_REQUEST"
            })
            .run()
    });

    it("Should dispatch GET_ARTISTS_LIST_SUCCESS", () => {
        const artistList = [];

        return expectSaga(artistSaga.getArtistsListWorker, mockAction(artistList))
            .withReducer(reducer)
            .put({
                type: "GET_ARTISTS_LIST_SUCCESS",
                artistList,
            })
            .run(false);
    });
});