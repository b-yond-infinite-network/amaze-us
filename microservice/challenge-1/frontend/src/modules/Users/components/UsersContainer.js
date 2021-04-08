import { connect } from "react-redux";
import Home from "./Users";
import { withRouter } from "react-router-dom";

import { callGetAll } from "../redux/Actions";

const mapStateToProps = (state, ownProps) => ({
  users: state.userReducer.users,
});

const mapDispatchToProps = (dispatch, ownProps) => ({
  callGetAll: () => {
    dispatch(callGetAll());
  }
});

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Home));
