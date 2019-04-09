import { Request, Response } from 'express';
import { requestNative, apiOptions, _ } from '../../../common/utils';
import l from '../../../common/logger';
import Track from '../models/track';

export class TrackController {

  search(req: Request, res: Response): void {
    const { query, page } = req.query;

    let buildApi = apiOptions('track.search');

    buildApi.qs.q = _.toLower(query);
    buildApi.qs.page = page && (page/10 + 1);

    return requestNative(_.pickBy(buildApi, _.identity))
      .then((data: any) => {
        let track = _.get(data, 'message.body.track_list') ? _.get(data, 'message.body.track_list') : [];
        track = track.map(t => new Track(t.track))
        return res.json({
          data: {
            total: _.get(data, 'message.header.available'),
            track: track
          }
        })
      })
      .catch(function (err) {
        l.error(err)
        return res.status(400).end('server error')
      });
  }

}

export default new TrackController();
