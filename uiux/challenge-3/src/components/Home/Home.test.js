import Home from './index';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from './../../store'

const initialState = {
    home: {
        tags: ['tag1', 'tag2']
    },
    common: {
        appName: 'test appName',
        token: '1234'
    }
};

describe('Home Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    let mockOnClickTag;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)(initialState);
        mockOnClickTag = jest.fn();

        const props = {
            tags: ['tag1', 'tag2'],
            onClickTag: mockOnClickTag
        };

        tree = <Provider store={mockStore}>
            <MemoryRouter initialEntries={["/"]}>
                <Home {...props} />
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
        const component = wrapper.find('#home-page');
        expect(component.length).toBe(1);
    });

});