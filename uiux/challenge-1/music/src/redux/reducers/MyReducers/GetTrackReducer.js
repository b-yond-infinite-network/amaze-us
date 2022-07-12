import * as actionTypes from "../../actions/Types/ActionTypes";
import InitialState from "../InitialState";

export default function GetTrackReducer(state = InitialState, action) {
  console.log('action',action)
  switch (action.type) {
    case actionTypes.GET_TRACK:
      return {
        ...state.track,
        track_list: action.payload.track
      };

    default:
      return state;
  }
}
