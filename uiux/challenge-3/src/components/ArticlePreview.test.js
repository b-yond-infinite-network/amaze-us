import ArticlePreview from './ArticlePreview';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from './../store'

describe('ArticlePreview Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)({});

        const props = {
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
            }
        };

        tree = <Provider store={mockStore}>
            <MemoryRouter initialEntries={["/"]}>
                <ArticlePreview {...props} />
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
        const component = wrapper.find('#article-preview');
        expect(component.length).toBe(1);
    });

});