import React from 'react';
import { shallow } from 'enzyme';
import TrackList from '../Components/TrackList';
import TrackRow from '../Components/TrackRow';
import { track } from './testData';
import { Col } from 'reactstrap'; 

const tracks = [
  track,
]

const changeSortAttribute = jest.fn();

const getComponent = () => (
  <TrackList
    tracks={tracks}
    changeSortAttribute={changeSortAttribute}
    sortedBy="asc"
    classes={{}}
  />
)

describe('TrackList tests', () => {
  it('Will render without any issues', () => {
    const wrapper = shallow(getComponent());
    expect(wrapper).toBeDefined();
  });
  it ('Should have 3 Cols for headers and same number of TrackRows as Tracks', () => {
    const wrapper = shallow(getComponent());
    const headers = wrapper.find(Col);
    const trackRows = wrapper.find(TrackRow);
    expect(headers).toHaveLength(3);
    expect(trackRows).toHaveLength(tracks.length);
  })
  it("Will call changeSortAttribute when a header row is clicked on", () => {
    const wrapper = shallow(getComponent());
    const selectedCol = wrapper.find(Col).first();
    selectedCol.simulate('click');
    expect(changeSortAttribute).toHaveBeenCalled();
  });
})