import Comment from './Comment';
import { shallow } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from './../../store'

const initialState = {
    currentUser: {
        username: "test username"
    },
    comment: {
        id: '1234',
        body: 'Test body',
        author: {
            username: 'test username',
            image: ''
        },
        createdAt: '2019-06-29T18:36:23.216Z'
    }
};

describe('Comment Component', () => {

    let mockStore;
    let comment;
    let currentUser;

    let tree;
    let wrapper;
    let render;

    describe('currentUser === author', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)({ initialState });
            comment = initialState.comment;
            currentUser = initialState.currentUser;

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <Comment comment={comment} currentUser={currentUser} />
                </MemoryRouter>
            </Provider>;
            wrapper = shallow(tree).childAt(0).childAt(0).dive();
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should render without errors', () => {
            const component = wrapper.find('#comment-card');
            expect(component.length).toBe(1);
        });

        it('Should render author image', () => {
            const component = wrapper.find('#comment-author-image');
            expect(component.length).toBe(1);
        });

        it('Should render author info', () => {
            const component = wrapper.find('#comment-author');
            expect(component.length).toBe(1);
        });

        it('Should render delete button', () => {
            const component = wrapper.find('#comment-delete-button');
            expect(component.length).toBe(1);
        });

        it('Should show delete button', () => {
            const component = wrapper.find('#comment-delete-button');
            expect(component.props()['show']).toEqual(true);
        });

    });

    describe('currentUser !== author', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)({ initialState });
            comment = initialState.comment;
            currentUser = {};

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <Comment comment={comment} currentUser={currentUser} />
                </MemoryRouter>
            </Provider>;
            wrapper = shallow(tree).childAt(0).childAt(0).dive();
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should render without errors', () => {
            const component = wrapper.find('#comment-card');
            expect(component.length).toBe(1);
        });

        it('Should render author image', () => {
            const component = wrapper.find('#comment-author-image');
            expect(component.length).toBe(1);
        });

        it('Should render author info', () => {
            const component = wrapper.find('#comment-author');
            expect(component.length).toBe(1);
        });

        it('Should render delete button', () => {
            const component = wrapper.find('#comment-delete-button');
            expect(component.length).toBe(1);
        });

        it('Should NOT show delete button', () => {
            const component = wrapper.find('#comment-delete-button');
            expect(component.props()['show']).toEqual(false);
        });

    });

});