import * as actionTypes from "./Types/ActionTypes";
import { get } from "./Axios";

function GetLyricsSuccess(lyrics, dispatch) {
  return dispatch({
    type: actionTypes.GET_LYRİC,
    payload: lyrics,
  });
}
function GetTracksSuccess(track_list, dispatch) {
  return dispatch({
    type: actionTypes.GET_TRACKS,
    payload: track_list,
  });
}

function GetTrackSuccess(track, dispatch) {
  return dispatch({
    type: actionTypes.GET_TRACK,
    payload: track,
  });
}

function GetTop10TrackSuccess(track_list, dispatch) {
  return dispatch({
    type: actionTypes.GET_TOP_10_TRACK,
    payload: track_list,
  });
}

function GetSearchRequest(keyword, searchType, sortType) {
  const url = `track.search?${searchType}=${keyword}&page_size=10&page=1&${sortType}=desc`;
  return get(url);
}

function GetTrackRequest(id) {
  const url = `track.get?track_id=${id}`;
  return get(url);
}

function GetTop10Request() {
  const url = `chart.tracks.get?page=1&page_size=10&_preload_content=False&format=json&f_has_lyrics=1`;
  return get(url);
}

function GetRequestToLyrics(track_id) {
  const url = `track.lyrics.get?track_id=${track_id}`;
  return get(url);
}

export function GetTracks(dispatch, keyword, searchType, sortType) {
  return GetSearchRequest(keyword, searchType, sortType).then((response) => {
    const { payload } = GetTracksSuccess(response.data.message.body, dispatch);
    return payload;
  });
}

export function GetTrack(dispatch, id) {
  return GetTrackRequest(id).then((response) => {
    const { payload } = GetTrackSuccess(response.data.message.body, dispatch);

    return payload;
  });
}

export function GetTop10Track(dispatch) {
  return GetTop10Request().then((response) => {
    const { payload } = GetTop10TrackSuccess(
      response.data.message.body,
      dispatch
    );
    return payload;
  });
}

export  function GetLyrics(dispatch, track_id) {
  return GetRequestToLyrics(track_id).then((response) => {
    const {payload}= GetLyricsSuccess(response.data.message.body, dispatch);
    return payload;
  });
}
