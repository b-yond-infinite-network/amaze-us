import { call } from "./index";

export const fetchArtist = async (artist: string) => {
  const response = await call(`artist.search?q_artist=${artist}&page_size=20`);
  console.log("response: ", response);
  return response.artist_list;
};
