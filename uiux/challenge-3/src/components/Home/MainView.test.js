import MainView from './MainView';
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

describe('MainView Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    let mockOnTabClick;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)(initialState);
        mockOnTabClick = jest.fn();

        const props = {
            mockOnTabClick: mockOnTabClick
        };

        tree = <Provider store={mockStore}>
            <MemoryRouter initialEntries={["/"]}>
                <MainView {...props} />
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
        const component = wrapper.find('#main-view');
        expect(component.length).toBe(1);
    });

});