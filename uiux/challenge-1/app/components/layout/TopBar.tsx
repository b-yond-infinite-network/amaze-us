import React from "react"
import { useRouter } from "next/dist/client/router"
import {
  AppBar,
  Toolbar,
  Typography,
  Grid,
  IconButton,
} from "@material-ui/core"
import ArrowBackIcon from "@material-ui/icons/ArrowBack"
import ArtistSearchInput from "../ArtistSearchInput"

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
            <ArtistSearchInput defaultValue={query} />
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  )
}
