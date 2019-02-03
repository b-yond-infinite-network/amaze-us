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
  console.log("calling mocked call API: ", url);
  if (/artist.search/.test(url)) {
    // artist list
    response = Responses.ARTISTS;
  }
  // } else if (/^team\/[0-9]+$/.test(url)) {
  //   // team detail
  //   response = responses.team;
  // } else if (/user\/$/.test(url)) {
  //   // users
  //   response = responses.users;
  // } else if (/^user\/[0-9]+$/.test(url)) {
  //   // user detail
  //   response = responses.user;
  // }

  return Promise.resolve(response);
}
