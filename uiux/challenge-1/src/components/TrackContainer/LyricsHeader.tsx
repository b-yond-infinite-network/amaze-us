import React from "react"
import { Link } from "react-router-dom"
import { Track } from "../../types/DTOs/TrackDTO"

type LyricsHeaderProps = { track?: Track, artist_id: string }

export const LyricsHeader = (props: LyricsHeaderProps) =>
    <>
        <Link to={`/artist/${props.artist_id}`}
            style={{
                textDecoration: "none",
                cursor: "pointer"
            }}>back to artist</Link>
        <h4>{props.track?.track_name}</h4>
    </>