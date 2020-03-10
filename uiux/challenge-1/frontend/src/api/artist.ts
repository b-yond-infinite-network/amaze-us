import axios from "axios";
import qs from "qs";

import { IArtist, IArtistMusixMatchAPIParams } from "../../../shared/";

export const searchForArtists = async (
  params: IArtistMusixMatchAPIParams
): Promise<IArtist[]> => {
  return new Promise<IArtist[]>(async (resolve, reject) => {
    try {
      const fromBackend = await axios.get(
        "http://localhost:5000/api/search/artist",
        {
          params: {
            ...params
          },
          paramsSerializer: function(params) {
            return qs.stringify(params, { indices: false });
          }
        }
      );
      const result: Array<IArtist> = fromBackend.data;
      resolve(result);
    } catch (error) {
      reject("Cannot get response from backend");
    }
  });
};
