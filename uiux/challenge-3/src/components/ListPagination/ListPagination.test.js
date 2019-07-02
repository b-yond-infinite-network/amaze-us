import ListPagination from './ListPagination';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from '../../store'

const initialState = {
};

describe('ListPagination Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)(initialState);

        const props = {
            articlesCount: 100,
            currentPage: 0
        };

        tree = <Provider store={mockStore}>
            <MemoryRouter initialEntries={["/"]}>
                <ListPagination {...props} />
            </MemoryRouter>
        </Provider>;
        wrapper = mount(tree);
        console.log(wrapper.debug());
        render = renderer.create(tree).toJSON();

    });

    it('Should match the snapshot', () => {
        expect(render).toMatchSnapshot();
    });

    it('Should render without errors', () => {
        const component = wrapper.find('#pagination');
        expect(component.length).toBe(1);
    });

});