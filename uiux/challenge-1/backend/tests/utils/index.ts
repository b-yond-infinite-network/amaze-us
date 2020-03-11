import chai, { assert, expect } from "chai";

import { IArtistMusixMatchAPIParams } from "../../../shared/";
import { ITrackMusixMatchAPIParams } from "../../../shared/";
import { ILyricsMusixMatchAPIParams } from "../../../shared/";

import {
  artistAPIBuilder,
  trackAPIBuilder,
  lyricsAPIBuilder
} from "../../src/utils";

export default () => {
  describe("Testing artistAPIBuilder method", () => {
    it("should return valid musixmatch artist.serach api url when valid query parameters are passed", done => {
      const expectedURL = `${process.env.MUSIXMATCH_API_BASE_URL}/artist.search?q_artist=justin&page=1&page_size=20&json=true&apikey=${process.env.MUSIXMATCH_SECRET_API}`;
      const validParamObject: IArtistMusixMatchAPIParams = {
        name: "justin",
        page: "1",
        pageSize: "20"
      };
      const actualURL = artistAPIBuilder(validParamObject);
      assert.isString(
        actualURL,
        "URL from artistAPIBuilder must be a string type"
      );
      expect(actualURL).to.be.equal(expectedURL);
      done();
    });
  });

  describe("Testing trackAPIBuilder method", () => {
    it("should return valid musixmatch track.search api url when valid query paramters (without artistID) are passed", done => {
      const expectedURL = `${process.env.MUSIXMATCH_API_BASE_URL}/track.search?q_track_artist=What is life?&f_has_lyrics=true&page=1&page_size=20&json=true&apikey=${process.env.MUSIXMATCH_SECRET_API}`;
      const validParamObject: ITrackMusixMatchAPIParams = {
        name: "What is life?",
        lyricsRequired: "true",
        page: "1",
        pageSize: "20"
      };
      const actualURL = trackAPIBuilder(validParamObject);
      assert.isString(
        actualURL,
        "URL from trackAPIBuilder must be a string type"
      );
      expect(actualURL).to.be.equal(expectedURL);
      done();
    });
    it("should return valid musixmatch track.search api url when valid query paramters (only artistID) are passed", done => {
<<<<<<< HEAD
      const expectedURL = `${process.env.MUSIXMATCH_API_BASE_URL}/track.search?f_artist_id=12345&f_lyrics=true&page_size=20&json=true&apikey=${process.env.MUSIXMATCH_SECRET_API}`;
=======
      const expectedURL = `${process.env.MUSIXMATCH_API_BASE_URL}/track.search?f_artist_id=12345&f_has_lyrics=true&page=1&page_size=20&json=true&apikey=${process.env.MUSIXMATCH_SECRET_API}`;
>>>>>>> implement-frontend-components
      const validParamObject: ITrackMusixMatchAPIParams = {
        artistID: "12345",
        lyricsRequired: "true",
        pageSize: "20"
      };
      const actualURL = trackAPIBuilder(validParamObject);
      assert.isString(
        actualURL,
        "URL from trackAPIBuilder must be a string type"
      );
      expect(actualURL).to.be.equal(expectedURL);
      done();
    });
  });

  describe("Testing lyricsAPIBuilder method", () => {
    it("should return valid musixmatch track.lyrics.get api url when valid query paramters are passed", done => {
      const expectedURL = `${process.env.MUSIXMATCH_API_BASE_URL}/track.lyrics.get?track_id=12345&json=true&apikey=${process.env.MUSIXMATCH_SECRET_API}`;
      const validParamObject: ILyricsMusixMatchAPIParams = {
        trackID: "12345"
      };
      const actualURL = lyricsAPIBuilder(validParamObject);
      assert.isString(
        actualURL,
        "URL from lyricAPIBuilder must be a string type"
      );
      expect(actualURL).to.be.equal(expectedURL);
      done();
    });
  });
};
