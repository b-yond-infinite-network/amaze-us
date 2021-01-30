import { parseRating, Track, TrackLyrics } from "../models"
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
    artistId: trackData.artist_id,
  }
}

function parseTrackList(tracks: any): Track[] {
  return tracks.map(({ track }) => parseTrack(track))
}

function parseTrackLyrics(lyricsData: any): TrackLyrics {
  return {
    id: lyricsData.lyrics_id,
    explicit: lyricsData.explicit,
    body: lyricsData.lyrics_body,
    trackingScript: lyricsData.script_tracking_url,
    trakingPixel: lyricsData.pixel_tracking_url,
    copyright: lyricsData.lyrics_copyright,
  }
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

  getTrack: async (trackId: number): Promise<Track> => {
    const service = "track.get"
    const params = { track_id: trackId }

    const body = await fetchData(service, params)
    return parseTrack(body.message.body.track)
  },

  getTrackLyrics: async (trackId: number): Promise<TrackLyrics> => {
    const service = "track.lyrics.get"
    const params = { track_id: trackId }

    const body = await fetchData(service, params)
    return parseTrackLyrics(body.message.body.lyrics)
  },
}
