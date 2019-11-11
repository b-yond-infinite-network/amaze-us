import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import history from './../../../router/history';
import configureStore from 'redux-mock-store';
import { configure, shallow, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ArtistsContainer from './../artistsContainer';

//Setup
configure({ adapter: new Adapter() });
const mockStore = configureStore([]);

describe('My Connected React-Redux Component', () => {
    let store;
    let component;
    let getArtistsList;
    const artistList = [
        { artist_id: 1, artist_name: "Artist 1" },
        { artist_id: 2, artist_name: "Artist 2" },
        { artist_id: 3, artist_name: "Artist 3" }
    ];

    beforeEach(() => {
        store = mockStore({
            getArtistsList,
        });
        store.dispatch = jest.fn();
        getArtistsList = jest.fn()
        component = mount(
            <ArtistsContainer getArtistsList={getArtistsList} artistList={artistList} />
        );
    });

    it("Render matches snapshot", () => {
        expect(component).toMatchSnapshot();
    });

    it("Should call getArtistsList upon rendering", () => {
        expect(getArtistsList).toHaveBeenCalledTimes(1);
    });
});
