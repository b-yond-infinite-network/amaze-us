export interface IArtist {
  artistID: number;
  artistName: string;
  artistCountry: string;
  artistRating: number;
  artistTwitterURL?: string;
  totalAvailable: number;
}

export interface IArtistMusixMatchAPIParams {
  name: string;
  page: string | number;
  pageSize: string | number;
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
  trackID: number;
  artistName: string;
  hasLyrics: boolean;
  numFavorite: number;
  totalAvailable: number;
}

export interface ITrackMusixMatchAPIParams {
  // name is optional when track results are pulled with artist id alone
  name?: string;

  // artistID is optional when track results are pulled with name (not very accurate)
  artistID?: string;

  // filter out results based on the availability of lyrics
  lyricsRequired: string;

  page: string | number;
  pageSize: string | number;
}
