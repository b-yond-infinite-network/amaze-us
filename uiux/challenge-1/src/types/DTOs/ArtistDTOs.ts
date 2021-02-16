export interface ArtistGetResponse {
    message: Message;
  }
  export interface Message {
    header: Header;
    body: Body;
  }
  export interface Header {
    status_code: number;
    execute_time: number;
  }
  export interface Body {
    artist: Artist;
  }
  export interface Artist {
    artist_id: number;
    artist_name: string;
    artist_name_translation_list?: (ArtistNameTranslationListEntity)[] | null;
    artist_comment: string;
    artist_country: string;
    artist_alias_list?: (ArtistAliasListEntity)[] | null;
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
    artist_list?: (null)[] | null;
  }
  