
export default class Artist {

  id: number;
  name: string;
  country: string;
  rating: number;
  twitter: string;
  flag:string;

  constructor(obj) {
    this.id = obj.artist_id;
    this.name = obj.artist_name;
    this.country = obj.artist_country;
    this.rating = obj.artist_rating;
    this.twitter = obj.artist_twitter_url;
    this.flag = obj.flag;

  }
}
