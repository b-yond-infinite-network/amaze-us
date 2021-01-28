import React from "react"
import { useRouter } from "next/dist/client/router"
import {
  AppBar,
  Toolbar,
  Typography,
  TextField,
  Grid,
  InputAdornment,
  IconButton,
} from "@material-ui/core"
import SearchIcon from "@material-ui/icons/Search"
import ArrowBackIcon from "@material-ui/icons/ArrowBack"

export interface TopBarProps {
  backUrl?: string
  query?: string
}

export const TopBar = ({ backUrl, query }: TopBarProps): JSX.Element => {
  const router = useRouter()
  function handleBack() {
    router.push(backUrl)
  }

  return (
    <AppBar position="static">
      <Toolbar>
        <Grid container justify="space-between" alignItems="center">
          <Grid item>
            <Grid container alignItems="center">
              <Grid item>
                {!!backUrl && (
                  <IconButton
                    aria-label="Back"
                    onClick={handleBack}
                    color="inherit"
                  >
                    <ArrowBackIcon />
                  </IconButton>
                )}
              </Grid>
              <Grid item>
                <Typography variant="h6">Sing It With Me!</Typography>
              </Grid>
            </Grid>
          </Grid>
          <Grid item>
            <TextField
              label="Look for an artist"
              variant="filled"
              size="small"
              defaultValue={query}
              InputProps={{
                endAdornment: (
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
}
