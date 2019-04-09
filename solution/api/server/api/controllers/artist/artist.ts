import { Request, Response } from 'express';
import { requestNative, apiOptions, _, countryApi } from '../../../common/utils';
import l from '../../../common/logger';
import Artist from '../models/artist';

export class ArtistController {

  getById(req: Request, res: Response): void {

    let buildApi = apiOptions('artist.get');

    buildApi.qs.artist_id = req.params.id;

    return requestNative(_.pickBy(buildApi, _.identity))
      .then(data => {

        const artist = _.get(data, 'message.body.artist');

        if (!artist) {
          return Promise.reject(res.status(404).end());
        }

        // get country image for fun :)
        const country = artist.artist_country;

        if (_.isEmpty(country)) return artist

        const countryUri = countryApi(`alpha/${_.toLower(country)}`)

        return requestNative(countryUri).then(val => {
          return _.extend(artist, { flag: val && val.flag })
        })
      })
      .then((data: Artist) => {
        return res.json(new Artist(data))
      })
      .catch(function (err) {
        l.error(err)
      });
  }

  search(req: Request, res: Response): void {
    const { query, page } = req.query;

    let buildApi = apiOptions('artist.search');

    buildApi.qs.q_artist = _.toLower(query);
    buildApi.qs.page = page && (page/10 + 1);


    return requestNative(_.pickBy(buildApi, _.identity))
      .then(data => {
        const artists = _.get(data, 'message.body.artist_list');
        if (_.isEmpty(artists)) {
          return Promise.reject(res.status(404).end());
        }

        return res.json({
          data: {
            artists: artists.map(a => new Artist(a.artist)),
            total : _.get(data, 'message.header.available')
          }
        })
      })
      .catch(function (err) {
        l.error(err)
      });
  }

}

export default new ArtistController();
