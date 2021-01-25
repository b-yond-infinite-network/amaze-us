import { Artist, parseRatingFromBase } from "../models"
import appConfig from "../config"

function parseArtistsList(list: any[]): Artist[] {
  return list.map(({ artist }) => ({
    id: artist.artist_id,
    name: artist.artist_name,
    country: artist.artist_country,
    rating: parseRatingFromBase(artist.artist_rating, 100),
  }))
}

export default {
  getTopArtists: async (pageSize: number): Promise<Artist[]> => {
    const service = "chart.artists.get"

    const url = `${appConfig.apiUrl}/${service}?page_size=${pageSize}&apikey=${appConfig.apikey}`

    const response = await fetch(url)
    const jsonBody = await response.json()

    return parseArtistsList(jsonBody.message.body.artist_list)
  },
}
