import * as React from 'react';
import * as sinon from 'sinon';
import { withStyles } from '@material-ui/core';
import { mount } from 'enzyme';

import Header from '../header';

describe('<Header />', () => {
    it('should contains a title', () => {
        const wrapper = mount(<Header />);
        expect(wrapper.find('h1')).toHaveLength(1);
        const title = wrapper.find('h1');
        expect(title.text()).toEqual('Birthday-oke');
    });
});
