import { connect } from 'react-redux';
import TracksContainer from './tracksContainer';
import { getTracksList } from './../../services/tracksService/tracksActions';

export default connect((state) => ({
    trackList: state.tracksReducer.trackList,
    isLoading: state.tracksReducer.isLoading
}), {
    getTracksList,
})(TracksContainer);