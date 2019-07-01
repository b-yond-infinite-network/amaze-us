import Login from './Login';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from './../store'

const initialState = {
};

describe('Login Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)(initialState);

        const props = {
        };

        tree = <Provider store={mockStore}>
            <MemoryRouter initialEntries={["/"]}>
                <Login {...props} />
            </MemoryRouter>
        </Provider>;
        wrapper = mount(tree);
        console.log(wrapper.debug());
        render = renderer.create(tree).toJSON();

    });

    afterEach(() => {
        mockStore.clearActions();
    });

    it('Should match the snapshot', () => {
        expect(render).toMatchSnapshot();
    });

    it('Should render without errors', () => {
        const component = wrapper.find('#auth-page');
        expect(component.length).toBe(1);
    });

    it('Should allow user to change email', () => {

        const expectedActions = [
            { type: 'UPDATE_FIELD_AUTH', key: 'email', value: 'test@email.com' }
        ];

        const component = wrapper.find('input').find("[type='email']");
        component.simulate('change', { target: { value: 'test@email.com' } });

        expect(mockStore.getActions()).toEqual(expectedActions);
    });

    it('Should allow user to change password', () => {

        const expectedActions = [
            { type: 'UPDATE_FIELD_AUTH', key: 'password', value: 'testPassword' }
        ];

        const component = wrapper.find('input').find("[type='password']");
        component.simulate('change', { target: { value: 'testPassword' } });

        expect(mockStore.getActions()).toEqual(expectedActions);
    });

});