import CommentContainer from './CommentContainer';
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
    errors: "",
    slug: "test slug",
    comments: [
        {
            id: '1234',
            body: 'Test body',
            author: {
                username: 'test username',
                image: ''
            },
            createdAt: '2019-06-29T18:36:23.216Z'
        },
        {
            id: '12345',
            body: 'Test body 2',
            author: {
                username: 'test username 2',
                image: ''
            },
            createdAt: '2019-06-30T18:36:23.216Z'
        }
    ]
};

describe('CommentContainer Component', () => {

    let mockStore;
    let comment;
    let currentUser;

    let tree;
    let wrapper;
    let render;

    describe('has currentUser', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)({ initialState });
            comment = initialState.comment;
            
            const props = initialState;

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <CommentContainer {...props} />
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
            const component = wrapper.find('#comment-container');
            expect(component.length).toBe(1);
        });

        it('Should render comment input', () => {
            const component = wrapper.find('#comment-input');
            expect(component.length).toBe(1);
        });

        it('Should render comment list', () => {
            const component = wrapper.find('#comment-list');
            expect(component.length).toBe(1);
        });

    });

    describe('does NOT have currentUser', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)({ initialState });
            comment = initialState.comment;
            
            const props = initialState;
            props.currentUser = null;

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <CommentContainer {...props} />
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
            const component = wrapper.find('#comment-container');
            expect(component.length).toBe(1);
        });

        it('Should NOT render comment input', () => {
            const component = wrapper.find('#comment-input');
            expect(component.length).toBe(0);
        });

        it('Should render login', () => {
            const component = wrapper.find('#comment-login');
            expect(component.length).toBe(1);
        });

        it('Should render register', () => {
            const component = wrapper.find('#comment-register');
            expect(component.length).toBe(1);
        });

        it('Should render comment list', () => {
            const component = wrapper.find('#comment-list');
            expect(component.length).toBe(1);
        });

    });

});