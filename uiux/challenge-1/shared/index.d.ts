export interface IArtist {
  artistID: number;
  artistName: string;
  artistCountry: string;
  artistRating: number;
  artistTwitterURL?: string;
}

export interface IArtistMusixMatchAPIParams {
  name: string;
  page: string;
  pageSize: string;
}

export interface ILyrics {
  lyricsContent: string;
  language?: string;
}

export interface ILyricsMusixMatchAPIParams {
  // trackID is mandatory
  trackID: string;
}

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
