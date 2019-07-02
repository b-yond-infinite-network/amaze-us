import DeleteButton from './DeleteButton';
import { mount } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'
import configureMockStore from 'redux-mock-store'
import middlewares from '../../../store'

describe('DeleteButton Component', () => {

    let mockStore;

    let tree;
    let wrapper;
    let render;

    describe('show === true', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)({});

            const props = {
                show: true
            };

            tree = <DeleteButton store={mockStore} {...props} />;
            wrapper = mount(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should show the delete button', () => {
            const component = wrapper.find('#delete-button');
            expect(component.length).toBe(1);
        });

    });

    describe('show === false', () => {

        beforeAll(() => {

            mockStore = configureMockStore(middlewares)({});

            const props = {
                show: false
            };

            tree = <DeleteButton store={mockStore} {...props} />;
            wrapper = mount(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should NOT show the delete button', () => {
            const component = wrapper.find('#delete-button');
            expect(component.length).toBe(0);
        });

    });

});