import React, { useContext } from "react";
import { Context } from "../layout/context";
import Spinner from "../layout/Spinner";
import Track from "./Track";


const Tracks = () => {
  const [state] = useContext(Context);
  const { track_list, heading,loading } = state;

  if (track_list===undefined||loading) {
    return <Spinner />;
  } else {
    return (
      <>
        
        <h3 className="text-center mb-4 title-search-results">{heading}</h3>
        <div className="row">
          {track_list.map(item => (
            <Track key={item.track.track_id} track={item.track} />
          ))}
        </div>
      </>
    );
  }
};

export default Tracks;
