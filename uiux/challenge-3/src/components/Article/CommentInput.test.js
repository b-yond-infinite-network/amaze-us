import CommentInput from './CommentInput';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import configureMockStore from 'redux-mock-store'
import middlewares from './../../store'

const initialState = {
    currentUser: {
        username: "test username"
    }
};

describe('CommentInput Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)({ initialState });

        const props = initialState;

        tree = <CommentInput store={mockStore} {...props} />;
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