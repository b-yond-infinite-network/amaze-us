import ArticleList from './ArticleList';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from '../../store'

const initialState = {
    home: {
        tags: ['tag1', 'tag2']
    },
    common: {
        appLoaded: true,
        appName: 'test appName',
        currentUser: {
            username: 'Test User',
            image: ''
        },
        redirectTo: '/'
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

describe('ArticleList Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    describe('has articles', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)(initialState);

            const props = {
                articles: initialState.articleList.articles
            };

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <ArticleList {...props} />
                </MemoryRouter>
            </Provider>;
            wrapper = mount(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should show article previews', () => {
            const component = wrapper.find('ArticlePreview');
            expect(component.length).toBe(initialState.articleList.articles.length);
        });

    });

    describe('does not have articles', () => {

        beforeAll(() => {

            initialState.articles = null;
            mockStore = configureMockStore(middlewares)(initialState);

            const props = {
                articles: null
            };

            tree = <Provider store={mockStore}>
                <MemoryRouter initialEntries={["/"]}>
                    <ArticleList {...props} />
                </MemoryRouter>
            </Provider>;
            wrapper = mount(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should NOT show article previews', () => {
            const component = wrapper.find('ArticlePreview');
            expect(component.length).toBe(0);
        });

    });

});