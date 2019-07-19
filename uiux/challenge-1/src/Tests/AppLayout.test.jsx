import React from 'react';
import { shallow } from './enzyme';
import AppLayout from '../Components/AppLayout';

const getComponent = () => (
  <AppLayout />
);

describe('AppLayout Tests', () => {
  it('Should render without any issues', () => {
    const wrapper = shallow(getComponent());
    expect(wrapper).toBeDefined();
  });
});