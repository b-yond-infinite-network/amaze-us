import { combineReducers } from "redux";
import userReducer from "./modules/Users/redux/Reducer";
import loader from "./modules/shared/components/redux/Reducer";

export default combineReducers({
  userReducer,
  loader,
});
