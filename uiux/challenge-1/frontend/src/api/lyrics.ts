import axios from "axios";

import { ILyrics, ILyricsMusixMatchAPIParams } from "../../../shared/";

export const searchForLyrics = async (
  params: ILyricsMusixMatchAPIParams
): Promise<ILyrics> => {
  return new Promise<ILyrics>(async (resolve, reject) => {
    try {
      const fromBackend = await axios.get(
        `http://localhost:5000/api/lyrics/${params.trackID}`
      );
      resolve(fromBackend.data);
    } catch (error) {
      reject("Cannot get response from backend " + error.stack);
    }
  });
};
