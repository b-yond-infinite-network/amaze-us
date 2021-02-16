export interface ArtistsSearchResponse {
  message: Message;
}
export interface Message {
  header: Header;
  body: Body;
}
export interface Header {
  status_code: number;
  execute_time: number;
  available: number;
}
export interface Body {
  artist_list?: (ArtistListEntity)[] | null;
}

export interface ArtistListEntity {
  artist: Artist;
}
export interface Artist {
  artist_id: number;
  artist_name: string;
  artist_name_translation_list?: (ArtistNameTranslationListEntity | null)[] | null;
  artist_comment: string;
  artist_country: string;
  artist_alias_list?: (ArtistAliasListEntity | null)[] | null;
  artist_rating: number;
  artist_twitter_url: string;
  artist_credits: ArtistCredits;
  restricted: number;
  updated_time: string;
  begin_date: string;
  end_date: string;
}
export interface ArtistNameTranslationListEntity {
  artist_name_translation: ArtistNameTranslation;
}
export interface ArtistNameTranslation {
  language: string;
  translation: string;
}
export interface ArtistAliasListEntity {
  artist_alias: string;
}
export interface ArtistCredits {
  artist_list?: (ArtistListEntity1 | null)[] | null;
}
export interface ArtistListEntity1 {
  artist: Artist1;
}
export interface Artist1 {
  artist_id: number;
  artist_name: string;
  artist_name_translation_list?: (ArtistNameTranslationListEntity1 | null)[] | null;
  artist_comment: string;
  artist_country: string;
  artist_alias_list?: (ArtistAliasListEntity1)[] | null;
  artist_rating: number;
  artist_twitter_url: string;
  artist_credits: ArtistCredits1;
  restricted: number;
  updated_time: string;
  begin_date: string;
  end_date: string;
}
export interface ArtistNameTranslationListEntity1 {
  artist_name_translation: ArtistNameTranslation;
}
export interface ArtistAliasListEntity1 {
  artist_alias: string;
}
export interface ArtistCredits1 {
  artist_list?: (null)[] | null;
}

// export type Artist = {
//   artist_id: number;
//   artist_mbid: string;
//   artist_name: string;
//   artist_country: string;
//   artist_alias_list?: (ArtistAliasListEntity)[] | null;
//   artist_rating: number;
//   artist_twitter_url: string;
//   updated_time: string;
// }

// type ArtistAliasListEntity = {
//   artist_alias: string;
// }
