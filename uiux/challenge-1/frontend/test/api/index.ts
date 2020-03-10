import { expect } from "chai";

import { searchForArtists } from "../../src/api/artist";
import { searchForTracks } from "../../src/api/track";
import { searchForLyrics } from "../../src/api/lyrics";

import {
  IArtist,
  IArtistMusixMatchAPIParams,
  ITrackMusixMatchAPIParams,
  ITrack,
  ILyrics,
  ILyricsMusixMatchAPIParams
} from "../../../shared";

export default () => {
  describe("Testing API methods", () => {
    describe("Testing searchForArtists method", () => {
      it("should return artist search result in an array and must match IArtist type props", done => {
        const validParams: IArtistMusixMatchAPIParams = {
          name: "Justin",
          page: 0,
          pageSize: 10
        };
        searchForArtists(validParams).then(response => {
          // Assuming "Justin" artist search query returns at least 10 results
          expect(response.length).to.be.at.least(10);
          response.forEach((each: IArtist) => {
            expect(each).to.have.property("artistID");
            expect(each).to.have.property("artistName");
            expect(each).to.have.property("artistCountry");
            expect(each).to.have.property("artistRating");
            expect(each).to.have.property("artistTwitterURL");
          });
        });
        done();
      });
    });
    describe("Testing searchForTracks method", () => {
      it("should return track search result in an array and must match ITrack type props", done => {
        const validParams: ITrackMusixMatchAPIParams = {
          name: "Happy",
          page: 0,
          pageSize: 10,
          lyricsRequired: "true"
        };
        searchForTracks(validParams).then(response => {
          // Assuming "Happy" track search query returns at least 10 results
          expect(response.length).to.be.at.least(10);
          response.forEach((each: ITrack) => {
            expect(each).to.have.property("name");
            expect(each).to.have.property("rating");
            expect(each).to.have.property("explicit");
            expect(each).to.have.property("artistID");
            expect(each).to.have.property("artistName");
            expect(each).to.have.property("hasLyrics");
            expect(each).to.have.property("numFavorite");
          });
        });
        done();
      });
    });
    describe("Testing searchForLyrics method", () => {
      it("should return lyrics search result in an array and must match ILyrics type props", done => {
        const validParams: ILyricsMusixMatchAPIParams = {
          trackID: "15953433"
        };
        searchForLyrics(validParams).then(response => {
          expect(response).to.have.property("lyricsContent");
        });
        done();
      });
    });
  });
};
