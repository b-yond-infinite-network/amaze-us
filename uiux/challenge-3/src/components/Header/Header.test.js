import Header from './Header';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from '../../store'

const initialState = {
};

describe('Header Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    describe('has currentUser', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)(initialState);

            const props = {
                appName: 'test appName',
                currentUser: {
                    username: 'Test user',
                    image: ''
                }
            };

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <Header {...props} />
                </MemoryRouter>
            </Provider>;
            wrapper = mount(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should NOT render the login link', () => {
            const component = wrapper.find('#header-login');
            expect(component.length).toBe(0);
        });

    });

    describe('does NOT hav currentUser', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)(initialState);

            const props = {
                appName: 'test appName',
                currentUser: null
            };

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <Header {...props} />
                </MemoryRouter>
            </Provider>;
            wrapper = mount(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should render the login link', () => {
            const component = wrapper.find('#header-login');
            expect(component.length).toBe(1);
        });

    });

});