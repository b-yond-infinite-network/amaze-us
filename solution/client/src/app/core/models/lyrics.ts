
export class Lyrics {
    id: string;
    pixel_tracking_url: string;
    lyrics_body: string;

    constructor(obj: any = {}) {
        this.id = obj.id;
        this.lyrics_body = obj.lyrics_body;
        this.pixel_tracking_url = obj.pixel_tracking_url;

    }
}