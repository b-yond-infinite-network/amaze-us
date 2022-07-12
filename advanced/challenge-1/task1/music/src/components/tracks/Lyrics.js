import React, { useState, useEffect, useContext } from "react";
import { Link } from "react-router-dom";
import Spinner from "../layout/Spinner";
import Moment from "react-moment";
import { useDispatch, useSelector } from "react-redux";
import * as actions from "../../redux/actions/Actions";
const Lyrics = (props) => {
  const [trackResult, setTrackResult] = useState(undefined);
  const [lyricsResult, setLyricsResult] = useState(undefined);
  
  let { track } = useSelector((state) => ({
    ...state.GetTrackReducer,
  }));

  let { lyrics } = useSelector((state) => ({
    ...state.GetLyricsReducer,
  }));

  const dispatch = useDispatch();


  useEffect(() => {
    actions.GetLyrics(dispatch, props.match.params.id).then((payload) => {
      lyrics = payload.lyrics;
      setLyricsResult({ lyrics });
   
    });
    actions.GetTrack(dispatch, props.match.params.id).then((payload) => {
      track = payload.track;
      setTrackResult({ track });

    });

  }, [props.match.params.id]);

  
  if (
    trackResult === undefined ||
    lyricsResult === undefined ||
    Object.keys(trackResult).length === 0 ||
    Object.keys(lyricsResult).length === 0
  ) {
    return <Spinner />;
  } else {
    return (
      <>
        <Link to="/" className="btn-back">
          <i class="far fa-arrow-alt-circle-left"></i> Back
        </Link>
        <div className="card card-artist-description">
          <h5 className="card-header">
            {trackResult.track.track_name} by{" "}
            <span className="text-secondary">{trackResult.track.artist_name}</span>
          </h5>
          <div className="card-body">
            <p className="card-text">{lyricsResult.lyrics.lyrics_body}</p>
          </div>
        </div>

        <ul className="list-group mt-3">
          <li className="list-group-item">
            <strong>Album ID</strong>: {trackResult.track.album_id}
          </li>
          <li className="list-group-item">
            <strong>Song Genre</strong>:{" "}
            {trackResult.track.primary_genres.music_genre_list.length === 0
              ? "NO GENRE AVAILABLE"
              : trackResult.track.primary_genres.music_genre_list[0].music_genre
                  .music_genre_name}
          </li>
          <li className="list-group-item">
            <strong>Explicit Words</strong>:{" "}
            {trackResult.track.explicit === 0 ? "No" : "Yes"}
          </li>
          <li className="list-group-item">
            <strong>Release Date</strong>:{" "}
            <Moment format="MM/DD/YYYY">
              {trackResult.track.first_release_date}
            </Moment>
          </li>
        </ul>
      </>
    );
  }
};

export default Lyrics;
