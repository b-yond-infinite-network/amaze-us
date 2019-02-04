import { call } from "./index";
import Track, { Lyrics } from "../types/track";

export const searchTracks = async (id: string): Promise<Track[]> => {
  const response = await call(
    `track.search?f_artist_id=${id}&page_size=100&s_track_rating=desc`
  );

  // get only tracks with lyrics
  const tracksWithLyrics = response.track_list.filter(
    (track: Track) => track.track.has_lyrics === 1
  );

  return tracksWithLyrics;
};

export const getTrack = async (id: string): Promise<Track> => {
  const response = await call(`track.get?track_id=${id}`);
  return response;
};

export const getLyrics = async (id: string): Promise<Lyrics> => {
  const response = await call(`track.lyrics.get?track_id=${id}`);
  return response.lyrics;
};
