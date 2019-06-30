import CommentList from './CommentList';
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

describe('CommentList Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)({ initialState });
            
            const props = initialState;

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <CommentList {...props} />
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
            const component = wrapper.find('div');
            expect(component.length).toBe(1);
        });

        it('Should render correct number of comments', () => {
            const component = wrapper.find('Comment');
            expect(component.length).toBe(initialState.comments.length);
        });

});