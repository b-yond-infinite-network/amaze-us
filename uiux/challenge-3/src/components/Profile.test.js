import Profile from './Profile';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from './../store'

const initialState = {
    profile: {
        username: 'test username',
        image: '',
        bio: 'test bio'
    },
    common: {
        currentUser: {
            username: 'test username',
            image: ''
        }
    },
    articleList: {
        tags: [],
        articles: [
            {
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
            }
        ],
        articleCount: 50,
        currentPage: 0
    }
};

describe('Profile Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)(initialState);

        const props = {
            match: {
                params: {
                    username: ''
                }
            }
        };

        tree = <Provider store={mockStore}>
            <MemoryRouter initialEntries={["/"]}>
                <Profile {...props} />
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
        const component = wrapper.find('#profile-page');
        expect(component.length).toBe(1);
    });

});