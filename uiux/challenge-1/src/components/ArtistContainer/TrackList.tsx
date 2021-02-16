import React from "react"
import { Link } from "react-router-dom"
import { Track } from "../../types/DTOs/TrackSearchDTOs"

type TrackListProps = { sortCriteria: string, tracks: Track[] }

export const TrackList = (props: TrackListProps) => {

    const sortByCriteria = (t1: Track, t2: Track) => {
        return props.sortCriteria === "title" ? (t1.track_name > t2.track_name ? 1 : t1.track_name < t2.track_name ? -1 : 0)
             : props.sortCriteria === "lyrics" ? (t1.has_lyrics > t2.has_lyrics ? 1 : t1.has_lyrics < t2.has_lyrics ? -1 : 0)
             : 0
    }

    return <ul style={{ listStyleType: "none" }} className="mt-2">{props.tracks
        .sort(sortByCriteria)
        .map(t => <li key={t.track_id}>
            {t.has_lyrics ? <Link to={`/artist/${t.artist_id}/${t.commontrack_id}`}
                style={{
                    textDecoration: "none",
                    cursor: "pointer"
                }}>
                {t.track_name}
            </Link>
                : t.track_name
            }

        </li>)}</ul>
}