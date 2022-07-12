import React, { useState, useEffect, useContext } from "react";

import { Context } from "../layout/context";
import { useDispatch, useSelector } from "react-redux";
import * as actions from "../../redux/actions/Actions";
const Search = () => {
  const { track_list } = useSelector((state) => ({
    ...state.GetTracksReducer,
  }));
  const dispatch = useDispatch();
  const [state, setState] = useContext(Context);
  const [userInput, setUserInput] = useState("");
  const [trackTitle, setTrackTitle] = useState("");
  const [searchType, setSearchType] = useState("q");
  const [sortType, setSortType] = useState("s_artist_rating");
  useEffect(() => {
    if (trackTitle) {
      actions
        .GetTracks(dispatch, trackTitle, searchType, sortType)
        .then((payload) => {
          setState({
            track_list:  payload.track_list,
            heading: "Results",
            loading: false,
          });
        });
    }
  }, [trackTitle, searchType, sortType]);

  const findTrack = (e) => {
    e.preventDefault();
    setTrackTitle(userInput);
  };

  const onChange = (e) => {
    setUserInput(e.target.value);
  };
  const onChangeSearchTypeValue = (event) => {
    console.log(event.target.value);
    setSearchType(event.target.value);
  };
  const onChangeSortTypeValue = (event) => {
    console.log(event.target.value);
    setSortType(event.target.value);
  };
  return (
    <div className="wrapper-search">
      <h1 className="display-4 text-center">
        <i className="fas fa-music" /> Search For A Song
      </h1>
      <p className="lead text-center">Get the Lyrics For any Song</p>
      <form onSubmit={findTrack}>
        <div
          onChange={onChangeSearchTypeValue}
          className="form-group wrapper-search-type"
          value={searchType}
        >
          {" "}
          Search Type :
          <label class="wrap-radio">
            All
            <input
              type="radio"
              value="q"
              name="all"
              checked={searchType === "q"}
              onChange={onChangeSearchTypeValue}
            />
            <span class="checkmark"></span>
          </label>
          <label class="wrap-radio">
            Artist
            <input
              type="radio"
              value="q_artist"
              name="artist"
              checked={searchType === "q_artist"}
              onChange={onChangeSearchTypeValue}
            />
            <span class="checkmark"></span>
          </label>
          <label class="wrap-radio">
            Song
            <input
              type="radio"
              value="q_track"
              name="song"
              checked={searchType === "q_track"}
              onChange={onChangeSearchTypeValue}
            />
            <span class="checkmark"></span>
          </label>
          <label class="wrap-radio">
            Lyrics
            <input
              type="radio"
              value="q_lyrics"
              name="lyrics"
              checked={searchType === "q_lyrics"}
              onChange={onChangeSearchTypeValue}
            />
            <span class="checkmark"></span>
          </label>
        </div>
        <div className="form-group wrap-search-input">
          <input
            type="text"
            className="form-control form-control-lg"
            placeholder="Search Song ..."
            name="userInput"
            value={userInput}
            onChange={onChange}
          />

          <button
            className="btn btn-primary btn-lg btn-block mb-5 btn-search-submit"
            type="submit"
          >
            <i class="fas fa-search fa-search-custom"></i> Get Track Lyrics
          </button>
          <div className="wrap-sort-filters">
            <select
              name="sorts"
              id="sorts"
              onChange={onChangeSortTypeValue}
              value={sortType}
            >
              <option value="s_artist_rating">Artist Rating</option>
              <option value="s_track_rating">Track Rating</option>
            </select>
          </div>
        </div>
      </form>
    </div>
  );
};

export default Search;
