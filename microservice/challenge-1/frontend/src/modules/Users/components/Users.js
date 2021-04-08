import React from "react";
import CssBaseline from "@material-ui/core/CssBaseline";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import Divider from "@material-ui/core/Divider";
import { withStyles } from "@material-ui/core/styles";
import UserInfo from "./UserInfo";
import Typography from "@material-ui/core/Typography";
import propTypes from "prop-types";

const useStyles = (theme) => ({
  container: {
    padding: 30,
  },
  select: {
    height: 40,
  },
});

class Users extends React.Component {
  constructor(props) {
    super(props);

    this.handleChange = this.handleChange.bind(this);

    this.state = {
      users: []
    };
  }

  componentDidMount() {
    this.props.callGetAll();
  }

  handleChange(e) {
    const value = e.target.value;
    this.setState({ [e.target.name]: value });
  }

  handleClear = () => {
    this.setState((state) => ({
      users: [],
      isSearching: false
    }));
    this.props.callGetAll();
  };

  render() {
    const { users, isSearching } = this.state;
    const { classes } = this.props;

    return (
      <React.Fragment>
        <CssBaseline />
        <Divider variant="middle" />
        <Container maxWidth="lg" className={classes.container}>
          <Grid container xs={12} spacing={2} justify="center">
            <Grid
              container
              xs={8}
              spacing={2}
              direction="row"
              justify="center"
              alignItems="center"
            >
              {!isSearching &&
                this.props.users &&
                this.props.users.map((user) => (
                  <UserInfo key={user.id} user={user} />
                ))}
              {isSearching &&
                users &&
                users.map((user) => <UserInfo key={user.id} user={user} />)}
              {isSearching && !users.length && (
                <Typography color="primary">No results.</Typography>
              )}
            </Grid>
          </Grid>
        </Container>
      </React.Fragment>
    );
  }
}

Users.propTypes = {
  callGetAll: propTypes.func
};

Users.defaultProps = {
  apiError: undefined,
  users: [],
};

export default withStyles(useStyles)(Users);
