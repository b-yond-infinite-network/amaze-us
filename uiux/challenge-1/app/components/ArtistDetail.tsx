import React from "react"
import { Paper, Grid, Box, Typography } from "@material-ui/core"
import TrackCard from "./TrackCard"
import { Title } from "./layout/Title"
import { Artist, Track, Album } from "../models"

export interface ArtistDetailProps {
  artist: Artist
  albums: Album[]
  tracks: Track[]
}

const ArtistDetail = ({ artist, tracks }: ArtistDetailProps): JSX.Element => {
  return (
    <Paper>
      <Box p={2}>
        <Grid container direction="column">
          <Grid item>
            <Title>{`${artist.name} tracks`}</Title>
          </Grid>
          <Grid item>
            {tracks.length === 0 ? (
              <Typography variant="body1">{`No tracks are available for ${artist.name}`}</Typography>
            ) : (
              <Grid container spacing={2}>
                {tracks.map((track, ix) => (
                  <Grid item key={ix} xs={12} sm={6} md={4} xl={2}>
                    <TrackCard {...track} />
                  </Grid>
                ))}
              </Grid>
            )}
          </Grid>
        </Grid>
      </Box>
    </Paper>
  )
}

export default ArtistDetail
