import React from "react"
import { Paper, Grid, Box } from "@material-ui/core"
import ArtistCard from "./ArtistCard"
import { Title } from "./layout/Title"
import { Artist } from "../models"

export interface DashboardProps {
  topArtists: Artist[]
}

const Dashboard = ({ topArtists }: DashboardProps): JSX.Element => {
  return (
    <Paper>
      <Box p={2}>
        <Grid container direction="column">
          <Grid item>
            <Title>{`Top ${topArtists.length} artists`}</Title>
          </Grid>
          <Grid item>
            <Grid container spacing={2}>
              {topArtists &&
                topArtists.map((artist, ix) => (
                  <Grid item key={ix}>
                    <ArtistCard {...artist} />
                  </Grid>
                ))}
            </Grid>
          </Grid>
        </Grid>
      </Box>
    </Paper>
  )
}

export default Dashboard
