import React from "react";
import { Link } from "react-router-dom";
import Moment from "react-moment";

import Artist from "../../assets/artist.svg";
import Tracks from "../../assets/track.svg";
import Album from "../../assets/album.svg";
import Date from "../../assets/date.svg";
const Track = (props) => {
  const { track } = props;

  return (
    <div className="col-md-6">
      <div className="card mb-4 shadow-sm">
        <div className="card-body">
          <strong>
            <img src={Artist} alt="" class="icon-search-result-track"/>Artist
          </strong>
          : {track.artist_name}
          <br />
          
          
          <strong>
          <img src={Tracks} alt="" class="icon-search-result-track"/> Track
          </strong>
          : {track.track_name}
          <br />
            <strong>
            <img src={Album} alt="" class="icon-search-result-track"/> Album
            </strong>
            : {track.album_name}
            <br />
            <strong>
            <img src={Date} alt="" class="icon-search-result-track"/> Date
            </strong>
            : <Moment format="MM/DD/YYYY">
              {track.updated_time}
            </Moment>
          
          <Link
            to={`lyrics/track/${track.track_id}`}
            className="btn btn-block btn-view-track"
          >
             Lyrics <i className="fas fa-chevron-right" /> 
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Track;
