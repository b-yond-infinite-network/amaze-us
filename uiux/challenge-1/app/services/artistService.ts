import { Artist, parseRating } from "../models"
import fetchData from "./api"
import cacheManager from "cache-manager"

const artistCache = cacheManager.caching({
  store: "memory",
  ttl: 60 * 60 * 24,
})

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
    const parsedArtists = parseArtistsList(body.message.body.artist_list)

    parsedArtists.forEach((artist) => artistCache.set(artist.id, artist))
    return parsedArtists
  },

  getArtist: async (id: number): Promise<Artist> => {
    return await artistCache.wrap(id, () => {
      const service = "artist.get"
      const params = { artist_id: id }

      return fetchData(service, params).then((body) =>
        parseArtist(body.message.body.artist)
      )
    })
  },

  searchArtist: async (query: string, pageSize: number): Promise<Artist[]> => {
    const service = "artist.search"
    const params = { q_artist: query, page_size: pageSize }

    const body = await fetchData(service, params)
    const parsedArtists = parseArtistsList(body.message.body.artist_list)

    parsedArtists.forEach((artist) => artistCache.set(artist.id, artist))
    return parsedArtists
  },
}
