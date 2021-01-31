import React from "react"
import { Grid, Box } from "@material-ui/core"
import ArtistCard from "./ArtistCard"
import { Title } from "./layout/Title"
import { Artist, Track } from "../models"
import TrackCard from "./TrackCard"

export interface DashboardProps {
  topArtists?: Artist[]
  topTracks?: Track[]
}

const Dashboard = ({
  topArtists = [],
  topTracks = [],
}: DashboardProps): JSX.Element => {
  return (
    <Box p={2}>
      <Grid container direction="column">
        {topTracks.length > 0 ? (
          <>
            <Grid item>
              <Title>{`Top ${topTracks.length} songs`}</Title>
            </Grid>
            <Grid item>
              <Grid container spacing={2}>
                {topTracks &&
                  topTracks.map((track, ix) => (
                    <Grid item key={ix} xs={12} sm={6} md={4} xl={2}>
                      <TrackCard {...track} />
                    </Grid>
                  ))}
              </Grid>
            </Grid>
          </>
        ) : null}
        {topArtists.length > 0 ? (
          <>
            <Box component={Grid} mt={4}>
              <Title>{`Top ${topArtists.length} artists`}</Title>
            </Box>
            <Grid item>
              <Grid container spacing={2}>
                {topArtists &&
                  topArtists.map((artist, ix) => (
                    <Grid item key={ix} xs={12} sm={6} md={2} xl={1}>
                      <ArtistCard {...artist} />
                    </Grid>
                  ))}
              </Grid>
            </Grid>
          </>
        ) : null}
      </Grid>
    </Box>
  )
}

export default Dashboard
