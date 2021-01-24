import React from "react"
import {
  AppBar,
  Toolbar,
  Typography,
  TextField,
  Grid,
  InputAdornment,
} from "@material-ui/core"
import SearchIcon from "@material-ui/icons/Search"

export const TopBar = (): JSX.Element => (
  <AppBar position="static">
    <Toolbar>
      <Grid container justify="space-between" alignItems="center">
        <Grid item>
          <Typography variant="h6">Sing It With Me!</Typography>
        </Grid>
        <Grid item>
          <TextField
            label="Look for an artist"
            variant="filled"
            size="small"
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <SearchIcon />
                </InputAdornment>
              ),
            }}
          />
        </Grid>
      </Grid>
    </Toolbar>
  </AppBar>
)
