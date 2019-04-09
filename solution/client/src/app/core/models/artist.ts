export class Artist {

    id: number;
    name: string;
    country: string;
    rating: number;
    twitter: string;
    flag:string;

    constructor(obj) {
      this.id = obj.id;
      this.name = obj.name;
      this.country = obj.country;
      this.rating = obj.rating;
      this.twitter = obj.twitter;
      this.flag = obj.flag;

    }
  }
