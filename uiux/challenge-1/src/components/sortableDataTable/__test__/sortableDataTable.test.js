import React from 'react';
import ReactDOM from 'react-dom';
import { configure, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import SortableDataTable from './../sortableDataTable';


//Setup
configure({ adapter: new Adapter() });

let component;
const artistList = [
    { artist_id: 1, artist_name: "Artist 1" },
    { artist_id: 2, artist_name: "Artist 2" },
    { artist_id: 3, artist_name: "Artist 3" }
];

describe('My Connected React-Redux Component', () => {
    beforeEach(() => {
        component = mount(
            <SortableDataTable
                defaultSort={{ field: 'artist_name', dir: 'asc' }}
                data={artistList} />
        );
    });

    it("Render matches snapshot", () => {
        expect(component).toMatchSnapshot();
    });

    it("Should render 3 <GridRow />", () => {
        expect(component.find("GridRow")).toHaveLength(3);
    });

    it("Should render 2 <GridHeaderCell />", () => {
        expect(component.find("GridHeaderCell")).toHaveLength(2);
    });

    it("Should render 2 <GridHeaderCell />", () => {
        expect(component.find("GridHeaderCell")).toHaveLength(2);
    });

});
