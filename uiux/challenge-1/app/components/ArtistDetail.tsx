import React, { useState } from "react"
import {
  Grid,
  Box,
  Typography,
  FormControl,
  Select,
  MenuItem,
  InputLabel,
} from "@material-ui/core"
import { Title } from "./layout/Title"
import { Artist, Track, Album, TrackOrdering } from "../models"
import TrackList from "./TrackList"

export interface ArtistDetailProps {
  artist: Artist
  albums: Album[]
  tracks: Track[]
}

const ArtistDetail = ({ artist, tracks }: ArtistDetailProps): JSX.Element => {
  const [order, setOrder] = useState(TrackOrdering.LIKES)

  const handleOrderChange = (event: React.ChangeEvent<{ value: string }>) => {
    setOrder(event.target.value as TrackOrdering)
  }

  return (
    <Box p={2}>
      <Grid container direction="row" justify="space-between">
        <Grid item>
          <Title>{`${artist.name} songs`}</Title>
        </Grid>
        <Grid item xs={12} sm={3} md={2}>
          <FormControl fullWidth variant="outlined">
            <InputLabel id="sort-label">Order songs by</InputLabel>
            <Select
              label="Order songs by"
              labelId="sort-label"
              value={order}
              onChange={handleOrderChange}
              className="test-sort-select"
            >
              <MenuItem
                value={TrackOrdering.LIKES}
                className="test-sort-menu-item"
              >
                Likes
              </MenuItem>
              <MenuItem
                value={TrackOrdering.ALBUM}
                className="test-sort-menu-item"
              >
                Album
              </MenuItem>
              <MenuItem
                value={TrackOrdering.RATING}
                className="test-sort-menu-item"
              >
                Rating
              </MenuItem>
              <MenuItem
                value={TrackOrdering.NAME}
                className="test-sort-menu-item"
              >
                Song title
              </MenuItem>
              <MenuItem
                value={TrackOrdering.HAS_LYRICS}
                className="test-sort-menu-item"
              >
                Has lyrics
              </MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12}>
          {tracks.length === 0 ? (
            <Typography variant="body1">{`No tracks are available for ${artist.name}`}</Typography>
          ) : (
            <TrackList tracks={tracks} order={order} />
          )}
        </Grid>
      </Grid>
    </Box>
  )
}

export default ArtistDetail
