import { ArtistGetResponse } from "../types/DTOs/ArtistDTOs"
import { ArtistsSearchResponse } from "../types/DTOs/ArtistSearchDTOs"

const baseUrl = "https://cors-anywhere.herokuapp.com/" + "https://api.musixmatch.com/ws/1.1/"

export const searchArtist = async (query: string, pageSize: number = 10) => {
    try {
        const urlParams = new URLSearchParams({ q_artist: query, page_size: pageSize.toString(), apikey: process.env.REACT_APP_API_KEY ?? "" })

        const url = baseUrl + "artist.search?" + urlParams

        const res = await fetch(url, { method: "GET" })

        return (await res.json()) as ArtistsSearchResponse
    } catch (e) {
        console.log("ERROR OCURRED ON FETCH");

        console.log(e);
    }
}

export const getArtist = async (artist_id: number) => {
    try {
        const urlParams = new URLSearchParams({ artist_id: artist_id.toString(), apikey: process.env.REACT_APP_API_KEY ?? "" })

        const url = baseUrl + "artist.get?" + urlParams

        const res = await fetch(url, { method: "GET" })

        return (await res.json()) as ArtistGetResponse
    } catch (e) {
        console.log("ERROR OCURRED ON FETCH");

        console.log(e);
    }
}