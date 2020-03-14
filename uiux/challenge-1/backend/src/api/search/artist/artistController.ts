import axios from "axios";
import { Request, Response } from "express";
import { IArtist, IArtistMusixMatchAPIParams } from "../../../../../shared";
import { HTTP500Error, HTTP503Error } from "../../../utils/httpErrors";
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
        name: artistObject.artist_name,
        country: artistObject.artist_country,
        rating: artistObject.artist_rating,
        twitterURL: artistObject.artist_twitter_url,
        totalAvailable: response.data.message.header.available
      });
    });

    // All good.
    res.status(200).send(result);
  } catch (err) {
    if (err.response && err.response.status === 503) {
      switch (err.response.status) {
        case 500:
          throw new HTTP500Error({
            message:
              "Musix Match Servers responded with 500. Please try again later."
          });
        case 503:
          throw new HTTP503Error({
            message:
              "Musix Match Servers responded with 503. Please try again later."
          });
      }
    } else {
      throw new HTTP500Error({
        message: `Internal server error ${err.stack}`
      });
    }
  }
};

export default artistController;
