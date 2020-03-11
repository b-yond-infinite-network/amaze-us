import axios from "axios";
import qs from "qs";

import { ITrack, ITrackMusixMatchAPIParams } from "../../../shared";

export const searchForTracks = async (
  params: ITrackMusixMatchAPIParams
): Promise<ITrack[]> => {
  return new Promise<ITrack[]>(async (resolve, reject) => {
    try {
      const fromBackend = await axios.get(
        "http://localhost:5000/api/search/track",
        {
          params: { ...params },
          paramsSerializer: function(params) {
            return qs.stringify(params, { indices: false });
          }
        }
      );
      const result: Array<ITrack> = fromBackend.data;
      resolve(result);
    } catch (error) {
      reject("Cannot get response from backend");
    }
  });
};
