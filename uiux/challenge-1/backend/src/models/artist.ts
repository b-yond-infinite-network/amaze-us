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
