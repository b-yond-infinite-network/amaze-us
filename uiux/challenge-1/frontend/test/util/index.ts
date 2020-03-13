import { expect, assert } from "chai";

import sortFunctions from "../../src/util/sort";
import {
  ARTIST_DATA,
  TRACK_DATA,
  ARTIST_DATA_ALPHABETICAL_ASCENDING,
  ARTIST_DATA_ALPHABETICAL_DESCENDING,
  ARTIST_DATA_RATING_ASCENDING,
  ARTIST_DATA_RATING_DESCENDING,
  TRACK_DATA_ALPHABETICAL_ASCENDING,
  TRACK_DATA_ALPHABETICAL_DESCENDING,
  TRACK_DATA_RATING_ASCENDING,
  TRACK_DATA_RATING_DESCENDING
} from "../mockdata";

export default () => {
  describe("Testing sort functions", () => {
    describe("Testing sortAlphabeticallyAZ method", () => {
      it("Should sort the artist and track names alphabetically in ascending order", () => {
        assert.deepEqual(
          ARTIST_DATA.sort(sortFunctions.NAME_ALPHABETICAL_ASCENDING),
          ARTIST_DATA_ALPHABETICAL_ASCENDING
        );
        assert.deepEqual(
          TRACK_DATA.sort(sortFunctions.NAME_ALPHABETICAL_ASCENDING),
          TRACK_DATA_ALPHABETICAL_ASCENDING
        );
      });
    });
    describe("Testing sortAlphabeticallyZA method", () => {
      it("Should sort the artist and track names alphabetically in descending order ", () => {
        assert.deepEqual(
          ARTIST_DATA.sort(sortFunctions.NAME_ALPHABETICAL_DESCENDING),
          ARTIST_DATA_ALPHABETICAL_DESCENDING
        );
        assert.deepEqual(
          TRACK_DATA.sort(sortFunctions.NAME_ALPHABETICAL_DESCENDING),
          TRACK_DATA_ALPHABETICAL_DESCENDING
        );
      });
    });
    describe("Testing sortRatingAscending method", () => {
      it("Should sort the artist and track ratings in ascending order", () => {
        assert.deepEqual(
          ARTIST_DATA.sort(sortFunctions.RATING_NUMERICAL_ASCENDING),
          ARTIST_DATA_RATING_ASCENDING
        );
        assert.deepEqual(
          TRACK_DATA.sort(sortFunctions.RATING_NUMERICAL_ASCENDING),
          TRACK_DATA_RATING_ASCENDING
        );
      });
    });
    describe("Testing sortRatingDescending method", () => {
      it("Should sort the artist and track ratings in descending order", () => {
        assert.deepEqual(
          TRACK_DATA.sort(sortFunctions.RATING_NUMERICAL_DESCENDING),
          TRACK_DATA_RATING_DESCENDING
        );
        assert.deepEqual(
          ARTIST_DATA.sort(sortFunctions.RATING_NUMERICAL_DESCENDING),
          ARTIST_DATA_RATING_DESCENDING
        );
      });
    });
  });
};
