import axios from "axios";
import { Request, Response } from "express";
import { ITrack, ITrackMusixMatchAPIParams } from "../../../models/track";
import { HTTP500Error } from "../../../utils/httpErrors";
import { trackAPIBuilder } from "../../../utils";

const trackController = async (req: Request, res: Response) => {
  let trackAPIParams: ITrackMusixMatchAPIParams = {
    name: req.query.name,
    artistID: req.query.artistID,
    lyricsRequired: req.query.lyricsRequired,
    page: req.query.page,
    pageSize: req.query.pageSize
  };

  const trackAPIURL = trackAPIBuilder(trackAPIParams);
  try {
    const response = await axios.get(trackAPIURL);
    if (!response.data.message.body.track_list) {
      const error = new HTTP500Error({
        message: "Third party servers are not responding."
      });
      throw error;
    }

    let result: ITrack[] = [];
    response.data.message.body.track_list.forEach((each: any) => {
      const trackObject = each.track;
      result.push({
        name: trackObject.track_name,
        rating: trackObject.track_rating,
        explicit: trackObject.explicit ? true : false,
        artistID: trackObject.artist_id,
        artistName: trackObject.artist_name,
        hasLyrics: trackObject.has_lyrics,
        numFavorite: trackObject.num_favourite
      });
    });
    // All good
    res.status(200).send(result);
  } catch (err) {
    console.log(err);
    const error = new HTTP500Error({
      message: "Internal server error."
    });
    throw error;
  }
};

export default trackController;
