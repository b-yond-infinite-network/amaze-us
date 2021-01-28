import { parseRating, Track } from "../models"
import fetchData from "./api"

function parseTrack(trackData: any): Track {
  return {
    id: trackData.track_id,
    name: trackData.track_name,
    rating: parseRating(trackData.track_rating, 100),
    hasLyrics: trackData.has_lyrics,
    numFavourite: trackData.num_favourite,
    albumName: trackData.album_name,
    artistName: trackData.artist_name,
  }
}

function parseTrackList(tracks: any): Track[] {
  return tracks.map(({ track }) => parseTrack(track))
}

export default {
  getTopTracks: async (pageSize: number): Promise<Track[]> => {
    const service = "chart.tracks.get"
    const params = { page_size: pageSize }

    const body = await fetchData(service, params)
    return parseTrackList(body.message.body.track_list)
  },

  getAlbumTracks: async (albumId: number): Promise<Track[]> => {
    const service = "album.tracks.get"
    const params = { album_id: albumId }

    const body = await fetchData(service, params)
    return parseTrackList(body.message.body.track_list)
  },
}
