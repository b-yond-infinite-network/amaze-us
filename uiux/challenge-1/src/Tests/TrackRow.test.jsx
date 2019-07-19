import React from 'react';
import { shallow } from 'enzyme';
import { Row } from 'reactstrap';
import TrackRow from '../Components/TrackRow';
import { track } from './testData';

const onClick = jest.fn();

const getComponent = (showLyrics) => (
  <TrackRow
    track={track}
    showLyrics={showLyrics}
    onClick={onClick}
    classes={{ }}
  />
)

describe('TrackRow Tests', () => {
  it('Should render without any issues', () => {
    const wrapper = shallow(getComponent(false));
    expect(wrapper).toBeDefined();
  });
  it('Should call onClick when track row is clicked', () => {
    const wrapper = shallow(getComponent(false));
    wrapper.find(Row).first().simulate('click');
    expect(onClick).toHaveBeenCalled();
  });
  it('Should properly show lyrics if correct prop is passed', () => {
    const wrapper = shallow(getComponent(true));
    const rows = wrapper.find(Row);
    expect(rows).toHaveLength(2);
  })
  it('Should not display lyrics when song is not selected', () => {
    const wrapper = shallow(getComponent(false));
    const rows = wrapper.find(Row);
    expect(rows).toHaveLength(1);
  })
})