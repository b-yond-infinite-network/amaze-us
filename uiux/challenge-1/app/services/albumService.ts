import { Artist, parseRating } from "../models"
import { Album } from "../models/album"
import fetchData from "./api"

function parseAlbum(albumData: any): Album {
  return {
    id: albumData.album_id,
    name: albumData.album_name,
    rating: parseRating(albumData.album_rating, 100),
    release_date: albumData.album_release_date,
    copyright: albumData.album_copyright,
  }
}

function parseAlbumList(albums: any): Album[] {
  return albums.map(({ album }) => parseAlbum(album))
}

export default {
  getArtistAlbums: async (artistId: number): Promise<Album[]> => {
    const service = "artist.albums.get"
    const params = { artist_id: artistId }

    const body = await fetchData(service, params)
    return parseAlbumList(body.message.body.album_list)
  },
}
