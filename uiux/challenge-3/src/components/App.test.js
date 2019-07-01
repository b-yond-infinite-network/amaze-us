import App from './App';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from './../store'

const initialState = {
    home: {
        tags: ['tag1', 'tag2']
    },
    common: {
        appLoaded: true,
        appName: 'test appName',
        currentUser: {
            username: 'Test User',
            image: ''
        },
        redirectTo: '/'
    }
};

describe('App Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    describe('appLoaded === true', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)(initialState);

            const props = {
            };

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <App {...props} />
                </MemoryRouter>
            </Provider>;
            wrapper = mount(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

    });

    describe('appLoaded === false', () => {

        beforeAll(() => {

            initialState.common.appLoaded = false;
            mockStore = configureMockStore(middlewares)(initialState);

            const props = {
            };

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <App {...props} />
                </MemoryRouter>
            </Provider>;
            wrapper = mount(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

    });

});