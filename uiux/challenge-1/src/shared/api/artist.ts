import { call } from "./index";

export const fetchArtists = async (artist: string) => {
  const response = await call(`artist.search?q_artist=${artist}&page_size=20`);

  return response.artist_list;
};
