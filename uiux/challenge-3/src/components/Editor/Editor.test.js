import Editor from './Editor';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router'
import configureMockStore from 'redux-mock-store'
import middlewares from '../../store'

const initialState = {
    editor: {
    }
};

describe('Editor Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    beforeAll(() => {

        mockStore = configureMockStore(middlewares)(initialState);

        const props = {
            match: {
                params: {
                    slug: ''
                }
            },
            tagList: [
                "tag1", "tag2"
            ]
        };

        tree = <Provider store={mockStore}>
            <MemoryRouter initialEntries={["/"]}>
                <Editor {...props} />
            </MemoryRouter>
        </Provider>;
        wrapper = mount(tree);
        console.log(wrapper.debug());
        render = renderer.create(tree).toJSON();

    });

    afterEach(() => {
        mockStore.clearActions();
    });

    it('Should match the snapshot', () => {
        expect(render).toMatchSnapshot();
    });

    it('Should render without errors', () => {
        const component = wrapper.find('#editor-page');
        expect(component.length).toBe(1);
    });

    it('Should allow user to update title field', () => {

        const expectedActions = [
            { type: 'UPDATE_FIELD_EDITOR', key: 'title', value: 'test title' }
        ];

        const component = wrapper.find('input').find("[placeholder='Article Title']");
        mockStore.clearActions();
        component.simulate('change', { target: { value: 'test title' } });
    
        expect(mockStore.getActions()).toEqual(expectedActions);
    });

    it('Should allow user to update about field', () => {

        const expectedActions = [
            { type: 'UPDATE_FIELD_EDITOR', key: 'description', value: 'test description' }
        ];

        const component = wrapper.find('input').find('[placeholder="What\'s this article about?"]');
        mockStore.clearActions();
        component.simulate('change', { target: { value: 'test description' } });
    
        expect(mockStore.getActions()).toEqual(expectedActions);
    });

    it('Should allow user to update fields', () => {

        const expectedActions = [
            { type: 'UPDATE_FIELD_EDITOR', key: 'body', value: 'test body' }
        ];

        const component = wrapper.find('textarea').find("[placeholder='Write your article (in markdown)']");
        mockStore.clearActions();
        component.simulate('change', { target: { value: 'test body' } });
    
        expect(mockStore.getActions()).toEqual(expectedActions);
    });

    it('Should allow user to add tag', () => {

        const expectedActions = [
            { type: 'ADD_TAG' }
        ];

        const component = wrapper.find('input').find("[placeholder='Enter tags']");
        component.simulate('change', { target: { value: 'test tag' } });
        mockStore.clearActions();
        component.simulate('keyUp', { keyCode: 13 });

        expect(mockStore.getActions()).toEqual(expectedActions);
    });

    it('Should allow user to remove a tag', () => {

        const expectedActions = [
            { type: 'REMOVE_TAG', tag: 'tag1' }
        ];

        const component = wrapper.find('#remove-tag-icon').first();
        mockStore.clearActions();
        component.simulate('click');

        expect(mockStore.getActions()).toEqual(expectedActions);
    });

});