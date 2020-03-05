export interface ITrack {
  name: string;
  rating: number;
  explicit: boolean;
  artistID: boolean;
  artistName: string;
  hasLyrics: boolean;
  numFavorite: number;
}

export interface ITrackMusixMatchAPIParams {
  // name is optional when track results are pulled with artist id alone
  name?: string;

  // artistID is optional when track results are pulled with name (not very accurate)
  artistID?: string;

  // filter out results based on the availability of lyrics
  lyricsRequired: string;

  page: string;
  pageSize: string;
}
