import ArticleActions from './ArticleActions';
import { shallow } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from '../../../store'

const initialState = {
    articleList: {
        tags: [],
        articles: [
            {
                slug: 'test_slug1',
            },
            {
                slug: 'test_slug2',
            },
            {
                slug: 'test_slug3',
            }
        ]
    }
}

describe('Article Actions Component', () => {

    let mockStore;
    let article;

    let component;
    let wrapper;
    let render;

    describe('canModify === true', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)(initialState);
            article = initialState.articleList.articles[0];

            component = <ArticleActions store={mockStore} article={article} canModify={true} />;
            wrapper = shallow(component).childAt(0).dive();
            render = renderer.create(
                <MemoryRouter initialEntries={["/"]}>
                    {component}
                </MemoryRouter>)
                .toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should render without errors', () => {
            const component = wrapper.find('span');
            expect(component.length).toBe(1);
        });

        it('Should render edit button', () => {
            const component = wrapper.find('#article-actions-edit');
            expect(component.length).toBe(1);
        });

        it('Should render delete button', () => {
            const component = wrapper.find('#article-actions-delete');
            expect(component.length).toBe(1);
        });

    });

    describe('canModify === false', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)(initialState);
            article = initialState.articleList.articles[0];

            component = <ArticleActions store={mockStore} article={article} canModify={false} />;
            wrapper = shallow(component).childAt(0).dive();
            render = renderer.create(component).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should render without errors', () => {
            const component = wrapper.find('span');
            expect(component.length).toBe(1);
        });

        it('Should NOT render edit button', () => {
            const component = wrapper.find('#article-actions-edit');
            expect(component.length).toBe(0);
        });

        it('Should NOT render delete button', () => {
            const component = wrapper.find('#article-actions-delete');
            expect(component.length).toBe(0);
        });
    });

});