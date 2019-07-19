import React from 'react';
import { shallow } from 'enzyme';
import TrackList from '../../Components/TrackList';
import TrackRow from '../../Components/TrackRow';
import { track } from '../testData';
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

  it('Will render without any issues', () => {
    expect(wrapper).toBeDefined();
  });
  it ('Should have 3 Cols for headers and same number of TrackRows as Tracks', () => {
    const headers = wrapper.find(Col);
    const trackRows = wrapper.find(TrackRow);
    expect(headers).toHaveLength(3);
    expect(trackRows).toHaveLength(tracks.length);
  })
  it("Will call changeSortAttribute when a header row is clicked on", () => {
    const selectedCol = wrapper.find(Col).first();
    selectedCol.simulate('click');
    expect(changeSortAttribute).toHaveBeenCalled();
  });
  it('Should set state with track when track is clicked on', () => {
    const track = wrapper.find(TrackRow).first();
    track.props().onClick(1);
    expect(setState).toHaveBeenCalledWith(1);
  })
})