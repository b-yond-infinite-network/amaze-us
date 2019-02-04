import { call } from "./index";
import Artist from "../types/artist";

export const artistSearch = async (artist: string): Promise<Artist[]> => {
  const response = await call(`artist.search?q_artist=${artist}&page_size=20`);

  return response.artist_list;
};

export const getArtist = async (id: number): Promise<Artist> => {
  const response = await call(`artist.get?artist_id=${id}`);
  return response;
};
