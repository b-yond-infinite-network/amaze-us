import ArticleMeta from './ArticleMeta';
import { shallow } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from './../../store'

const initialState = {
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
        ]
    }
};

describe('Article Meta Component', () => {

    let mockStore;
    let article;

    let tree;
    let wrapper;
    let render;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)({ initialState });
        article = initialState.articleList.articles[0];

        tree = <Provider store={mockStore}>
            <MemoryRouter initialEntries={["/"]}>
                <ArticleMeta article={article} canModify={true} />
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
        const component = wrapper.find('#article-meta');
        expect(component.length).toBe(1);
    });

    it('Should render author image', () => {
        const component = wrapper.find('#article-meta-author-image');
        expect(component.length).toBe(1);
    });

    it('Should render author info', () => {
        const component = wrapper.find('#article-meta-author-info');
        expect(component.length).toBe(1);
    });

});