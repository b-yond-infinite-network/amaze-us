import { Router, Express } from "express";
import { ILyricsMusixMatchAPIParams } from "../models/lyrics";
import { ITrackMusixMatchAPIParams } from "../models/track";
import { IArtistMusixMatchAPIParams } from "../models/artist";

type Wrapper = (router: Router) => void;

export const applyMiddleware = (middleware: Wrapper[], router: Router) => {
  for (const f of middleware) {
    f(router);
  }
};

export const applyRoutes = (app: Express, routes: Router[]) => {
  app.use("/api/", routes);
};

export const trackAPIBuilder = (params: ITrackMusixMatchAPIParams): string => {
  let result = `${process.env.MUSIXMATCH_API_BASE_URL}/track.search?`;
  if (params.artistID !== undefined) {
    result += `f_artist_id=${params.artistID}`;
  } else {
    result += `q_track=${params.name}&q_track_artist=${params.name}&q_lyrics=${params.name}`;
  }

  // Filter out results that doesn't have lyrics
  if (params.lyricsRequired) {
    result += "&f_lyrics=true";
  }

  result += `&page=${params.page}&page_size=${params.pageSize}&json=true`;
  result += `&apikey=${process.env.MUSIXMATCH_SECRET_API}`;
  return result;
};

export const artistAPIBuilder = (
  params: IArtistMusixMatchAPIParams
): string => {
  let result = `${process.env.MUSIXMATCH_API_BASE_URL}/artist.search?`;
  result += `q_artist=${params.name}&page=${params.page}&page_size=${params.pageSize}&json=true`;
  result += `&apikey=${process.env.MUSIXMATCH_SECRET_API}`;
  return result;
};

export const lyricsAPIBuilder = (
  params: ILyricsMusixMatchAPIParams
): string => {
  let result = `${process.env.MUSIXMATCH_API_BASE_URL}/track.lyrics.get?track_id=${params.trackID}&json=true`;
  result += `&apikey=${process.env.MUSIXMATCH_SECRET_API}`;
  return result;
};

export const musixMatchAPIBuiilder = (params: {}) => {};
