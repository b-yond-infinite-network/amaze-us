import React, { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import { searchArtist } from "../MusicxMatchAPI/ArtistAPI"
import { Artist } from "../types/DTOs/ArtistSearchDTOs"

export const ArtistSearch = () => {
    const [search, setSearch] = useState("")
    const [artists, setArtists] = useState<Artist[]>([])

    const searchRequested = async () => {
        const res = await searchArtist(search)
        setArtists(res?.message.body.artist_list?.map(a => a.artist) ?? [])
    }

    useEffect(() => {
        document.title = "Lyrics App - Search Artists"
    }, [])

    return <>
    <h1>Search Artists</h1>
        <input autoFocus type="text" name="W" onChange={(e: React.ChangeEvent<HTMLInputElement>) => setSearch(e.target.value)} />
        <button className="btn btn-outline-primary" onClick={() => searchRequested()}>Search</button>
        <ul className="mt-2">
            {artists.map(a => <li key={a.artist_id}>
                <Link to={`/artist/${a.artist_id}`}
                    style={{
                        textDecoration: "none",
                        cursor: "pointer"
                    }}>
                    {a.artist_name}
                </Link>
            </li>)}
        </ul>
    </>
}