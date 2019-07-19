import React from 'react';
import { shallow } from 'enzyme';
import { Input, Button } from 'reactstrap';
import AppLayout from '../../Components/AppLayout';

const getComponent = () => (
  <AppLayout />
);

describe('AppLayout Tests', () => {
  let wrapper;
  const setState = jest.fn();
  const useStateSpy = jest.spyOn(React, 'useState')
  useStateSpy.mockImplementation((init) => [init, setState]);

  beforeEach(() => {
    wrapper = shallow(getComponent());
  })

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('Should render without any issues', () => {
    expect(wrapper).toBeDefined();
  });
  it('Should correctly update artist name and reset tracks and page', () => {
    const input = wrapper.find(Input).first();
    input.props().onChange({ target: { value: 'TEST' } });
    expect(setState).toHaveBeenCalledWith('TEST');
    expect(setState).toHaveBeenCalledWith([]);
    expect(setState).toHaveBeenCalledWith(0);
  })
  it('Should start loader when search button is clicked', () => {
    const button = wrapper.find(Button).first();
    button.props().onClick();
    expect(setState).toHaveBeenCalledWith(true);
  })
});