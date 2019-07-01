import Settings from './Settings';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from './../store'

const initialState = {
    common: {
        currentUser: {
            username: 'test username',
            image: ''
        }
    }
};

describe('Settings Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)(initialState);

        const props = {
            currentUser: {
                username: 'test user',
                image: '',
                bio: 'test bio',
                email: 'test email'
            }
        };

        tree = <Provider store={mockStore}>
            <MemoryRouter initialEntries={["/"]}>
                <Settings {...props} />
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
        const component = wrapper.find('form');
        expect(component.length).toBe(1);
    });

});