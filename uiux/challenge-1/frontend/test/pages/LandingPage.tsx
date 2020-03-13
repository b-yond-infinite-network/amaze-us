import React from "react";
import { expect, assert } from "chai";
import LandingPage, { LandingPageState } from "../../src/pages/LandingPage";
import { shallow, ShallowWrapper } from "enzyme";
import {
  IArtistMusixMatchAPIParams,
  ITrackMusixMatchAPIParams
} from "../../../shared";

export default () => {
  describe("Testing <LandingPage/> page", () => {
    it("should render correctly when it loads initially", () => {
      let wrapper: ShallowWrapper = shallow(<LandingPage></LandingPage>);
      expect(wrapper.instance().state).to.deep.equal({
        sort: {
          type: "NO_SORT"
        },
        searchResult: {
          currentSearchName: "",
          totalAvailable: 0,
          type: "artist",
          displayingResult: [],
          currentSearchParam: {},
          paginatedResults: { 1: [] },
          currentPage: 1
        }
      });

      expect(wrapper.find("Container")).to.have.length(1);

      // Header
      expect(wrapper.find("Header")).to.have.length(1);
      expect(wrapper.find("Header").props()).to.have.property(
        "enableHomeButtonLink"
      );

      // SearchComponent
      expect(wrapper.find("SearchComponent")).to.have.length(1);
      expect(wrapper.find("SearchComponent").props()).to.have.property(
        "setSortType"
      );
      expect(wrapper.find("SearchComponent").props()).to.have.property(
        "searchTriggered"
      );

      // SearchResultBanner
      expect(wrapper.find("SearchResultBanner")).to.have.length(1);
      expect(wrapper.find("SearchResultBanner").props()).to.have.property(
        "banner"
      );

      expect(wrapper.find("SearchResultWrapper")).to.have.length(1);
      expect(wrapper.find("PaginationWrapper")).to.have.length(1);
      expect(wrapper.find("Pagination")).to.have.length(1);
    });

    it("should update state correctly when a new artist search is made", done => {
      let wrapper: ShallowWrapper = shallow(<LandingPage></LandingPage>);
      const instance = wrapper.instance();
      // Make a search request using handleSearchRequest
      const artistSearchRequestParam: IArtistMusixMatchAPIParams = {
        name: "Justin",
        page: 1,
        pageSize: 10
      };
      instance["handleSearchRequest"]("artist", artistSearchRequestParam).then(
        response => {
          const mutatedState = instance.state as LandingPageState;
          expect(mutatedState.sort).to.deep.equal({
            type: "NO_SORT"
          });
          expect(mutatedState.searchResult.currentPage).to.equal(1);
          expect(mutatedState.searchResult.currentSearchName).to.equal(
            "Justin"
          );
          expect(mutatedState.searchResult.type).to.equal("artist");
          expect(mutatedState.searchResult.totalAvailable).to.be.at.least(1000);
          expect(mutatedState.searchResult.currentSearchParam).to.deep.equal({
            name: "Justin",
            page: 1,
            pageSize: 10
          });
          expect(mutatedState.searchResult.paginatedResults).to.have.property(
            "1"
          );
          assert.isArray(mutatedState.searchResult.paginatedResults["1"]);
          expect(
            mutatedState.searchResult.paginatedResults["1"].length
          ).to.be.at.least(10);
          expect(wrapper.find("ArtistCardComponent")).to.have.length(10);
        }
      );
      done();
    });

    it("should update state correctly when a new track search is made", done => {
      let wrapper: ShallowWrapper = shallow(<LandingPage></LandingPage>);
      const instance = wrapper.instance();
      // Make a search request using handleSearchRequest
      const trackSearchRequestParam: ITrackMusixMatchAPIParams = {
        name: "Rolling in the deep",
        lyricsRequired: "true",
        page: 1,
        pageSize: 10
      };
      instance["handleSearchRequest"]("track", trackSearchRequestParam).then(
        response => {
          const mutatedState = instance.state as LandingPageState;
          expect(mutatedState.sort).to.deep.equal({
            type: "NO_SORT"
          });
          expect(mutatedState.searchResult.currentPage).to.equal(1);
          expect(mutatedState.searchResult.currentSearchName).to.equal(
            "Rolling in the deep"
          );
          expect(mutatedState.searchResult.type).to.equal("track");
          expect(mutatedState.searchResult.currentSearchParam).to.deep.equal({
            name: "Rolling in the deep",
            lyricsRequired: "true",
            page: 1,
            pageSize: 10
          });
          expect(mutatedState.searchResult.paginatedResults).to.have.property(
            "1"
          );
          assert.isArray(mutatedState.searchResult.paginatedResults["1"]);
          expect(
            mutatedState.searchResult.paginatedResults["1"].length
          ).to.be.at.least(10);
          expect(wrapper.find("TrackCardComponent")).to.have.length(10);
        }
      );
      done();
    });

    it("should update state correctly when sorting option is set", done => {
      let wrapper: ShallowWrapper = shallow(<LandingPage></LandingPage>);
      const instance = wrapper.instance();

      instance["sortResults"]("NAME_ALPHABETICAL_ASCENDING")
        .then(response => {
          const state = instance.state as LandingPageState;
          expect(state.sort.type).to.equal("NAME_ALPHABETICAL_ASCENDING");
        })
        .catch(err => {});

      instance["sortResults"]("NAME_ALPHABETICAL_DESCENDING")
        .then(response => {
          const state = instance.state as LandingPageState;
          expect(state.sort.type).to.equal("NAME_ALPHABETICAL_DESCENDING");
        })
        .catch(err => {});

      instance["sortResults"]("RATING_NUMERICAL_ASCENDING")
        .then(response => {
          const state = instance.state as LandingPageState;
          expect(state.sort.type).to.equal("RATING_NUMERICAL_ASCENDING");
        })
        .catch(err => {});

      instance["sortResults"]("RATING_NUMERICAL_DESCENDING")
        .then(response => {
          const state = instance.state as LandingPageState;
          expect(state.sort.type).to.equal("RATING_NUMERICAL_DESCENDING");
        })
        .catch(err => {});
      done();
    });

    it("should update state correctly when track search with a specific artist ID is made", done => {
      let wrapper: ShallowWrapper = shallow(<LandingPage></LandingPage>);
      const instance = wrapper.instance();
      // Make a search request using handleSearchRequest
      const trackSearchRequestParam: ITrackMusixMatchAPIParams = {
        artistID: "378462",
        lyricsRequired: "true",
        page: 1,
        pageSize: 10,
        name: "Lady Gaga"
      };
      instance["searchTracksByArtistID"](trackSearchRequestParam).then(
        response => {
          const mutatedState = instance.state as LandingPageState;
          expect(mutatedState.sort).to.deep.equal({
            type: "NO_SORT"
          });
          expect(mutatedState.searchResult.currentPage).to.equal(1);
          expect(mutatedState.searchResult.currentSearchName).to.equal(
            "get all tracks by Lady Gaga"
          );
          expect(mutatedState.searchResult.type).to.equal("track");
          expect(mutatedState.searchResult.currentSearchParam).to.deep.equal({
            artistID: "378462",
            lyricsRequired: "true",
            page: 1,
            pageSize: 10,
            name: "Lady Gaga"
          });
          expect(mutatedState.searchResult.paginatedResults).to.have.property(
            "1"
          );
          assert.isArray(mutatedState.searchResult.paginatedResults["1"]);
          expect(
            mutatedState.searchResult.paginatedResults["1"].length
          ).to.be.at.least(10);
          expect(wrapper.find("TrackCardComponent")).to.have.length(10);
        }
      );
      done();
    });
  });
};
