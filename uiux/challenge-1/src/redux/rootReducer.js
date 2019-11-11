import { combineReducers } from 'redux';
import artistsReducer from './../services/artistsService/artistsReducer';
import tracksReducer from './../services/tracksService/tracksReducer';
import lyricsReducer from './../services/lyricsService/lyricsReducer';

export default combineReducers({
  artistsReducer,
  tracksReducer,
  lyricsReducer,
});