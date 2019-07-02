import Banner from './Banner';
import { shallow } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer'

describe('Banner Component', () => {

    let tree;
    let wrapper;
    let render;

    describe('has token', () => {

        beforeAll(() => {
            
            const props = {
                appName: 'test name',
                token: '1234'
            };

            tree = <Banner {...props} />
            wrapper = shallow(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should NOT render banner', () => {
            const component = wrapper.find('#banner');
            expect(component.length).toBe(0);
        });

    });

    describe('does NOT have token', () => {

        beforeAll(() => {
        
            const props = {
                appName: 'test name',
                token: null
            };

            tree = <Banner {...props} />
            wrapper = shallow(tree);
            console.log(wrapper.debug());
            render = renderer.create(tree).toJSON();

        });

        it('Should match the snapshot', () => {
            expect(render).toMatchSnapshot();
        });

        it('Should render banner', () => {
            const component = wrapper.find('#banner');
            expect(component.length).toBe(1);
        });

    });

});