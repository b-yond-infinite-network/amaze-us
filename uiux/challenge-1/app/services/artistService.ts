import { Artist, parseRating } from "../models"
import fetchData from "./api"

function parseArtist(artistData: any): Artist {
  return {
    id: artistData.artist_id,
    name: artistData.artist_name,
    country: artistData.artist_country,
    rating: parseRating(artistData.artist_rating, 100),
  }
}

function parseArtistsList(list: any[]): Artist[] {
  return list.map(({ artist }) => parseArtist(artist))
}

export default {
  getTopArtists: async (pageSize: number): Promise<Artist[]> => {
    const service = "chart.artists.get"
    const params = { page_size: pageSize }

    const body = await fetchData(service, params)
    return parseArtistsList(body.message.body.artist_list)
  },

  getArtist: async (id: number): Promise<Artist> => {
    const service = "artist.get"
    const params = { artist_id: id }

    const body = await fetchData(service, params)
    return parseArtist(body.message.body.artist)
  },
}
