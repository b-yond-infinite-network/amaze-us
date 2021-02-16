import React, { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import { TrackList } from "../components/ArtistContainer/TrackList"
import { getArtist } from "../MusicxMatchAPI/ArtistAPI"
import { searchArtistTracks } from "../MusicxMatchAPI/TracksAPI"
import { Artist } from "../types/DTOs/ArtistDTOs"
import { Track } from "../types/DTOs/TrackSearchDTOs"

export const ArtistContainer = () => {
    const { id } = useParams<{ id: string }>()

    const [tracks, setTracks] = useState<Track[]>([])
    const [artist, setArtist] = useState<Artist>()

    const [sortCriteria, setSortCriteria] = useState("lyrics")

    const [isLoading, setIsLoading] = useState(true)

    const fetchTracks = async () => {
        const res = await searchArtistTracks(+id)
        setTracks(res?.message.body.track_list?.map(a => a.track) ?? [])
        setIsLoading(false)
    }

    const fetchArtist = async () => {
        const res = await getArtist(+id)
        setArtist(res?.message.body.artist)
    }

    useEffect(() => {
        fetchTracks()
        fetchArtist()
        document.title = "Lyrics App - Artist"
    }, [])

    return <>{isLoading ? "loading artist..." :
        <>
            <h4>{artist?.artist_name}</h4>

            {tracks && tracks.length > 0 &&
                <>
                    Sort By <select className="custom-select" name="trackFilter" id="trackfilter" onChange={(e: React.ChangeEvent<HTMLSelectElement>) => setSortCriteria(e.currentTarget.value)}>
                        <option value={"lyrics"}>Lyrics</option>
                        <option value={"title"}>Title</option>
                    </select>
                </>}
            <TrackList sortCriteria={sortCriteria} tracks={tracks} />

        </>}
    </>
}

