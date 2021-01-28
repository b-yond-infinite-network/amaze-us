import React from "react"
import { Grid } from "@material-ui/core"
import sortBy from "lodash/sortBy"
import reverse from "lodash/reverse"
import filter from "lodash/filter"
import TrackCard from "./TrackCard"
import { Track, TrackOrdering } from "../models"

interface TrackListProps {
  tracks: Track[]
  order?: TrackOrdering
}

const orderTracks = (tracks: Track[], order: TrackOrdering): Track[] => {
  switch (order) {
    case TrackOrdering.ALBUM:
    case TrackOrdering.NAME:
      return sortBy(tracks, [order])
    case TrackOrdering.RATING:
    case TrackOrdering.LIKES:
      return reverse(sortBy(tracks, [order]))
    case TrackOrdering.HAS_LYRICS:
      return filter(tracks, (track) => track.hasLyrics)
  }
}

const TrackList = ({
  tracks,
  order = TrackOrdering.ALBUM,
}: TrackListProps): JSX.Element => {
  const orderedTracks = orderTracks(tracks, order)

  return (
    <Grid container spacing={2}>
      {orderedTracks.map((track, ix) => (
        <Grid item key={ix} xs={12} sm={6} md={4} xl={2}>
          <TrackCard {...track} />
        </Grid>
      ))}
    </Grid>
  )
}

export default TrackList
