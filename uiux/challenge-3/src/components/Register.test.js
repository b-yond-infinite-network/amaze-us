import Register from './Register';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from './../store'

const initialState = {
};

describe('Register Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    let mockOnChangeEmail;
    let mockOnChangePassword;
    let mockOnChangeUsername;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)(initialState);
        mockOnChangeEmail = jest.fn();
        mockOnChangePassword = jest.fn();
        mockOnChangeUsername = jest.fn();

        const props = {
            onChangeEmail: mockOnChangeEmail,
            onChangePassword: mockOnChangePassword,
            onChangeUsername: mockOnChangeUsername
        };

        tree = <Provider store={mockStore}>
            <MemoryRouter initialEntries={["/"]}>
                <Register {...props} />
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
        const component = wrapper.find('#auth-page');
        expect(component.length).toBe(1);
    });

});