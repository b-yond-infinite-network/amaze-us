import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import * as actions from "../../redux/actions/Actions";

export const Context = React.createContext();

export function ContextController({ children }) {
  let intialState = {
    track_list: [],
    heading: "",
    loading: true,
  };

  const { track_list } = useSelector((state) => ({
    ...state.GetTop10TrackReducer,
  }));

  const dispatch = useDispatch();
  const [state, setState] = useState(intialState);
  useEffect(() => {
    actions.GetTop10Track(dispatch).then((payload) => {
      setState({
        track_list:  payload.track_list,
        heading: "top",
        loading: false,
      });
    });
  }, []);

  return (
    <Context.Provider value={[state, setState]}>{children}</Context.Provider>
  );
}
