import Article from './Article';
import { shallow, mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from '../../store'

const state = {
    common: {
        currentUser: {
            username: "test username"
        }
    },
    match: {
        params: {
            id: '1234'
        }
    },
    currentUser: {
        username: "test username"
    },
    article: {
        title: 'Untitled Title',
        slug: 'a-title-7ua6fd',
        body: '# Nic\nbylo nebylo',
        createdAt: '2019-06-29T18:36:23.216Z',
        updatedAt: '2019-06-29T18:37:11.601Z',
        tagList: [
            'nothing',
            'nic'
        ],
        description: 'o ničem',
        author: {
            username: 'tester96',
            bio: 'b',
            image: '',
            following: false
        },
        favorited: false,
        favoritesCount: 1
    },
    comments: [],
    commentErrors: []
};

describe('Article Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)(state);

        const props = state;

        tree = <Provider store={mockStore}>
            <MemoryRouter initialEntries={["/"]}>
                <Article {...props} />
            </MemoryRouter>
        </Provider>
        wrapper = mount(tree);
        console.log(wrapper.debug());
        render = renderer.create(tree).toJSON();

    });

    it('Should match the snapshot', () => {
        expect(render).toMatchSnapshot();
    });

    it('Should render without errors', () => {
        const component = wrapper.find('#article-container');
        expect(component.length).toBe(1);
    });

});