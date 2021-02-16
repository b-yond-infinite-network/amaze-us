import React, { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import { LyricsHeader } from "../components/TrackContainer/LyricsHeader"
import { LyricsText } from "../components/TrackContainer/LyricsText"
import { getLyrics, getTrack } from "../MusicxMatchAPI/TracksAPI"
import { Lyrics } from "../types/DTOs/LyricsDTOs"
import { Track } from "../types/DTOs/TrackDTO"

export const TrackContainer = () => {
    const { artist_id, track_id } = useParams<{ artist_id: string, track_id: string }>()

    const [lyrics, setLyrics] = useState<Lyrics>()
    const [track, setTrack] = useState<Track>()

    const [isLoading, setIsLoading] = useState(true)

    const fetchLyrics = async () => {
        const res = await getLyrics(+track_id)
        setLyrics(res?.message.body.lyrics)
        setIsLoading(false)
    }

    const fetchTrack = async () => {
        const res = await getTrack(+track_id)
        setTrack(res?.message.body.track)
    }

    useEffect(() => {
        fetchLyrics()
        fetchTrack()
    }, [])

    return <>
        {isLoading ? "loading lyrics..." :
            <>
                <LyricsHeader track={track} artist_id={artist_id} />
                <LyricsText lyrics={lyrics} />
            </>
        }
    </>
}


