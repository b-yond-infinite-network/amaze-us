import React from "react";
import CssBaseline from "@material-ui/core/CssBaseline";
import {
  Container,
  Divider,
  Grid,
  Typography
} from "@material-ui/core";
import propTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import { getUser } from "../services/UserServices";

const useStyles = (theme) => ({
  container: {
    padding: 30,
  },
  root: {
    display: "flex",
    "& > *": {
      margin: theme.spacing(1),
      width: theme.spacing(30),
      height: theme.spacing(30),
      borderRadius: "4px",
    },
    justifyContent: "center",
  },
  button: {
    margin: theme.spacing(1),
    textTransform: "none",
  },
  twitter: {
    margin: theme.spacing(1),
    textTransform: "none",
    backgroundColor: "#1DA1F2",
  },
  a: {
    textDecoration: "none",
    color: "white",
  },
});

class UserMoreInfo extends React.Component {
  constructor(props) {
    super();

    this.state = {
      user: undefined,
    };
  }

  componentDidMount() {
    getUser(this.props.match.params.id).then(({ data, error }) => {
      if (data) {
        this.setState((state) => ({
          user: data,
        }));
      } else if (error) {
        console.log("error obtaining User");
      }
    });
  }

  render() {
    const { classes } = this.props;
    const { user } = this.state;

    return (
      <React.Fragment>
        <CssBaseline />
        <Divider variant="middle" />
        <Container maxWidth="lg" className={classes.container}>
          <Grid container spacing={3}>
            <Grid item xs={7}>
              <Typography component="h1" variant="caption" gutterBottom>
                User
              </Typography>
              <Typography component="h2" variant="h4" gutterBottom>
                {user?.name}
              </Typography>
              <Typography variant="button" color="textSecondary" gutterBottom>
                Descripción
              </Typography>
              <Typography variant="body1" paragraph>
                {user?.description}
              </Typography>
              <Typography variant="button" color="textSecondary" gutterBottom>
                Email
              </Typography>
              <Typography variant="body2" paragraph>
                {user?.email}
              </Typography>
            </Grid>
          </Grid>
        </Container>
      </React.Fragment>
    );
  }
}

UserMoreInfo.propTypes = {
  user: propTypes.object,
};

UserMoreInfo.defaultProps = {
  user: undefined,
};

export default withStyles(useStyles)(UserMoreInfo);
