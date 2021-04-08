import React from "react";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import { Link } from "react-router-dom";

const useStyles = makeStyles({
  card: {
    display: "flex",
    margin: 2,
  },
  cardDetails: {
    flex: 1,
  },
  cardMedia: {
    width: 200,
  },
  link: {
    color: "inherit",
    textDecoration: "none",
  },
});

export default function UserInfo(props) {
  const classes = useStyles();
  const { user } = props;

  const truncate2 = (str) => {
    return str.length > 30 ? str.substring(0, 30) + "..." : str;
  };

  return (
    <Grid item xs={10}>
      <Card className={classes.card}>
        <div className={classes.cardDetails}>
          <CardContent>
            <Typography component="h1" variant="caption" gutterBottom>
              User
            </Typography>
            <Typography component="h2" variant="h5">
              {truncate2(user.name)}
            </Typography>
            <Typography variant="body2" color="textSecondary" gutterBottom>
              {user.description}
            </Typography>
            <Typography variant="body2" gutterBottom>
              {user.email}
            </Typography>
            <div>
              <Link to={`/users/${user.id}`} className={classes.link}>
                <Button color="secondary">See detail</Button>
              </Link>
            </div>
          </CardContent>
        </div>
      </Card>
    </Grid>
  );
}

UserInfo.propTypes = {
  user: PropTypes.object.isRequired,
};
