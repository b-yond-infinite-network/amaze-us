
export default class Lyrics {

    id: number;
    lyrics_body: string;
    lyrics_language: string;
    pixel_tracking_url: string;

    constructor(obj) {
      console.log(obj)
      this.id = obj.lyrics_id;
      this.lyrics_body = obj.lyrics_body;
      this.lyrics_language = obj.lyrics_language;
      this.pixel_tracking_url = obj.pixel_tracking_url;

    }
  }
