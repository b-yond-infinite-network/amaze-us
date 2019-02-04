import { call } from "./index";
import Track from "../types/tracks";

export const searchTracks = async (id: string) => {
  const response = await call(
    `track.search?f_artist_id=${id}&page_size=100&s_track_rating=desc`
  );

  // get only tracks with lyrics
  const tracksWithLyrics = response.track_list.filter(
    (track: Track) => track.track.has_lyrics === 1
  );

  return tracksWithLyrics;
};
