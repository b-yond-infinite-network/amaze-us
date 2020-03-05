import axios from "axios";
import { Request, Response } from "express";
import { IArtist, IArtistMusixMatchAPIParams } from "../../../models/artist";
import { HTTP500Error } from "../../../utils/httpErrors";
import { artistAPIBuilder } from "../../../utils";

const artistController = async (req: Request, res: Response) => {
  // Parse request query from the client
  let artistAPIParams: IArtistMusixMatchAPIParams = {
    name: req.query.name,
    page: req.query.page,
    pageSize: req.query.pageSize
  };

  const artistAPIURL = artistAPIBuilder(artistAPIParams);

  try {
    const response = await axios.get(artistAPIURL);

    // Check if the response is good.
    if (!response.data.message.body.artist_list) {
      const error = new HTTP500Error({
        message: "Third party servers are not responding."
      });
      throw error;
    }

    let result: IArtist[] = [];
    response.data.message.body.artist_list.forEach((each: any) => {
      const artistObject = each.artist;
      result.push({
        artistID: artistObject.artist_id,
        artistName: artistObject.artist_name,
        artistCountry: artistObject.artist_country,
        artistRating: artistObject.artist_rating,
        artistTwitterURL: artistObject.artist_twitter_url
      });
    });

    // All good.
    res.status(200).send(result);
  } catch (err) {
    console.log(err);
    const error = new HTTP500Error({
      message: "Internal server error."
    });
    throw error;
  }
};

export default artistController;
