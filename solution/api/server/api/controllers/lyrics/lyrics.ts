import { Request, Response } from 'express';
import { requestNative, apiOptions, _ } from '../../../common/utils';
import l from '../../../common/logger';
import Lyrics from './../models/lyrics';

export class LyricsController {

  getById(req: Request, res: Response): void {

    let buildApi = apiOptions('track.lyrics.get');

    buildApi.qs.track_id = req.params.id;

    return requestNative(_.pickBy(buildApi, _.identity))
      .then((data: any) => {
       if(_.get(data, 'message.body.lyrics')){
         const lyrics = new Lyrics(_.get(data, 'message.body.lyrics'));
         return res.json(lyrics)
       }
        return res.status(404).end();
      })
      .catch(function (err) {
        l.error(err)
      });
  }

}

export default new LyricsController();
