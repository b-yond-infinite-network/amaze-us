import React, { useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import NewDriver from './components/NewDriver';
import NewBus from './components/NewBus';
import DriverList from './components/DriversList';
import BusList from './components/BusList';

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1
  },
  block: {
    width: '100%'
  },
  control: {
    padding: theme.spacing(2)
  }
}));

const Home = ({ getDrivers, getBuses }) => {
  const classes = useStyles();

  useEffect(() => {
    (async () => {
      await getDrivers();
      await getBuses();
    })();
  }, []);

  return (
    <Grid container className={classes.root} spacing={2}>
      <Grid item xs={12}>
        <Grid container justifyContent="center" spacing={2}>
          <Grid item className={classes.block}>
            <NewDriver />
            <DriverList />
          </Grid>
          <Grid item className={classes.block}>
            <NewBus />
            <BusList />
          </Grid>
          <Grid item className={classes.block}>
            {/* <NewSchedule /> */}
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default Home;
