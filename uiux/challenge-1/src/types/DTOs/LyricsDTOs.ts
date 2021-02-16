export interface TrackLyricsGetResponse {
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
    lyrics: Lyrics;
}
export interface Lyrics {
    lyrics_id: number;
    explicit: number;
    lyrics_body: string;
    script_tracking_url: string;
    pixel_tracking_url: string;
    lyrics_copyright: string;
    updated_time: string;
}
