import { TrackLyricsGetResponse } from "../types/DTOs/LyricsDTOs"
import { TrackGetResponse } from "../types/DTOs/TrackDTO"
import { TrackSearchResponse } from "../types/DTOs/TrackSearchDTOs"

const baseUrl = "https://cors-anywhere.herokuapp.com/" + "https://api.musixmatch.com/ws/1.1/"

export const searchArtistTracks = async (artist_id: number, pageSize: number = 20) => {
    try {
        const urlParams = new URLSearchParams({ f_artist_id: artist_id.toString(), page_size: pageSize.toString(), apikey: process.env.REACT_APP_API_KEY ?? "" })

        const url = baseUrl + "track.search?" + urlParams

        const res = await fetch(url, { method: "GET" })

        return (await res.json()) as TrackSearchResponse
    } catch (e) {
        console.log("ERROR OCURRED ON FETCH");

        console.log(e);
    }
}

export const getTrack = async (track_id: number) => {
    try {
        const urlParams = new URLSearchParams({ commontrack_id: track_id.toString(), apikey: process.env.REACT_APP_API_KEY ?? "" })

        const url = baseUrl + "track.get?" + urlParams

        const res = await fetch(url, { method: "GET" })

        return (await res.json()) as TrackGetResponse
    } catch (e) {
        console.log("ERROR OCURRED ON FETCH");

        console.log(e);
    }
}

export const getLyrics = async (track_id: number) => {
    try {
        const urlParams = new URLSearchParams({ commontrack_id: track_id.toString(), apikey: process.env.REACT_APP_API_KEY ?? "" })

        const url = baseUrl + "track.lyrics.get?" + urlParams

        const res = await fetch(url, { method: "GET" })

        return (await res.json()) as TrackLyricsGetResponse
    } catch (e) {
        console.log("ERROR OCURRED ON FETCH");

        console.log(e);
    }
}