import { connect } from 'react-redux';
import LyricsContainer from './lyricsContainer';
import { getLyrics } from './../../services/lyricsService/lyricsActions';

export default connect((state) => ({
    lyrics: state.lyricsReducer.lyrics,
    isLoading: state.lyricsReducer.isLoading,
}), {
    getLyrics
})(LyricsContainer);
