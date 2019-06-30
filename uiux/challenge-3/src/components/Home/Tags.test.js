import Tags from './Tags';
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

describe('Tags Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    let mockOnClickTag;

    describe('has tags', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)(initialState);
            mockOnClickTag = jest.fn();

            const props = {
                tags: ['tag1', 'tag2'],
                onClickTag: mockOnClickTag
            };

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <Tags {...props} />
                </MemoryRouter>
            </Provider>;
            wrapper = mount(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should render tag list', () => {
            const component = wrapper.find('#tag-list');
            expect(component.length).toBe(1);
        });

    });

    describe('does not have tags', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)(initialState);
            mockOnClickTag = jest.fn();

            const props = {
                tags: null,
                onClickTag: mockOnClickTag
            };

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <Tags {...props} />
                </MemoryRouter>
            </Provider>;
            wrapper = mount(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should NOT render tag list', () => {
            const component = wrapper.find('#tag-list');
            expect(component.length).toBe(0);
        });

    });

});