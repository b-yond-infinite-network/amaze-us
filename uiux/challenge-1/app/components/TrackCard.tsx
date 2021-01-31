import React, { useState } from "react"
import {
  Card,
  Typography,
  CardContent,
  makeStyles,
  Link,
  Grid,
  Box,
  Chip,
} from "@material-ui/core"
import RatingStars from "@material-ui/lab/Rating"
import HeartIcon from "@material-ui/icons/FavoriteBorderRounded"
import { Track } from "../models"

const useStyles = makeStyles({
  card: {
    height: "100%",
  },
  noLyricsContainer: {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    height: "100%",
  },
})

const TrackCard = ({
  id,
  name,
  rating,
  hasLyrics,
  numFavourite,
  artistName,
  albumName,
}: Track): JSX.Element => {
  const classes = useStyles()
  const [hover, setHover] = useState(false)

  return (
    <Link href={hasLyrics ? `/tracks/${id}` : undefined}>
      <Card
        onMouseOver={() => setHover(true)}
        onMouseLeave={() => setHover(false)}
        raised={hover}
        className={classes.card}
      >
        <CardContent className="test-track-card">
          {hover && !hasLyrics ? (
            <Box className={classes.noLyricsContainer}>
              <Typography variant="h6" className="no-lyrics-found">
                No lyrics available
              </Typography>
            </Box>
          ) : (
            <Grid container justify="space-between" alignItems="flex-start">
              <Grid item lg={10}>
                <Typography variant="h6">{name}</Typography>
              </Grid>
              <Grid item lg={2}>
                <Chip
                  label={numFavourite}
                  icon={<HeartIcon />}
                  color="secondary"
                  variant="outlined"
                />
              </Grid>
              <Grid item xs={12}>
                <Typography variant="subtitle1">
                  {`${artistName} - ${albumName}`}
                </Typography>
              </Grid>

              <Grid item xs={12}>
                <RatingStars value={rating} readOnly />
              </Grid>
            </Grid>
          )}
        </CardContent>
      </Card>
    </Link>
  )
}

export default TrackCard
