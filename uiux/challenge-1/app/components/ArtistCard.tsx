import React from "react"
import { Card, Box, Typography, Grid } from "@material-ui/core"
import RatingStars from "@material-ui/lab/Rating"
import { Artist } from "../models"

const ArtistCard = ({
  name,
  startYear,
  endYear,
  rating,
}: Artist): JSX.Element => (
  <Card>
    <Box width={300} boxShadow={2} p={1} height={150}>
      <Grid container justify="space-between" alignItems="center">
        <Grid item sm={8}>
          <Typography variant="h6">{name}</Typography>
        </Grid>
        <Grid item>
          {startYear}
          {endYear && `-${endYear}`}
        </Grid>
        <Grid item xs={6}>
          <RatingStars value={rating} readOnly />
        </Grid>
      </Grid>
    </Box>
  </Card>
)

export default ArtistCard
