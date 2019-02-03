import ArtistType from "../../types/artist";

const ArtistsResponse: ArtistType[] = [
  { artist_id: 1, artist_name: "foo" },
  { artist_id: 2, artist_name: "bar" }
];

const Responses = {
  ARTISTS: { artist_list: ArtistsResponse }
};

export function call(url: string) {
  let response: any;

  if (/artist.search/.test(url)) {
    // artist list
    response = Responses.ARTISTS;
  }

  return Promise.resolve(response);
}
