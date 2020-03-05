export interface ILyrics {
  lyricsContent: string;
  language: string;
}

export interface ILyricsMusixMatchAPIParams {
  // trackID is mandatory
  trackID: string;
}
