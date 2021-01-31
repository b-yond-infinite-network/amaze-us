import React from "react"
import { Paper, Box, Typography } from "@material-ui/core"
import { Track, TrackLyrics } from "../models"
import { Title } from "./layout/Title"

interface LyricsProps {
  track: Track
  trackLyrics: TrackLyrics
}

const Lyrics = ({ track, trackLyrics }: LyricsProps) => {
  const bodyParts = trackLyrics.body.split("\n")

  return (
    <Box p={2}>
      <Title>
        {track.name} - {track.artistName}
      </Title>
      <Box p={2}>
        {bodyParts.map((part, ix) =>
          part === "" ? (
            <br />
          ) : (
            <Typography variant="body1" key={ix}>
              {part}
            </Typography>
          )
        )}
      </Box>
      <Box display="flex" justifyContent="flex-end">
        <Typography variant="caption">{trackLyrics.copyright}</Typography>
      </Box>
    </Box>
  )
}

export default Lyrics
