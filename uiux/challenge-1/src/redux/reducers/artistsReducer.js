import { SET_ARTIST_NAME, GET_ARTISTS, SET_SONG_INFO, SET_LYRICS_INFO } from '../actions/artistsActionsConstants.js';

export default function artistReducer(state=[], { type , payload }) {
  switch (type) {
    case SET_ARTIST_NAME:
      return  { ...state, artistName: payload }
    case GET_ARTISTS:
      return  { ...state,
          artistInfo: payload,
      }
    case SET_SONG_INFO:
      return  { ...state, songInfo: payload }

    case SET_LYRICS_INFO:
      return  { ...state, song: payload }

    default:
      break;
  }
  return state;
}