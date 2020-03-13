import React from "react";
import styled from "styled-components";
import { Pagination } from "antd";

import Header from "../components/Header";
import SearchComponent from "../components/Search";
import SearchResultBanner from "../components/SearchResultBanner";
import ArtistCardComponent from "../components/ArtistCard";

import SortFuntions from "../util/sort";

import { searchForArtists } from "../api/artist";
import { searchForTracks } from "../api/track";
import {
  IArtist,
  ITrack,
  IArtistMusixMatchAPIParams,
  ITrackMusixMatchAPIParams
} from "../../../shared";
import TrackCardComponent from "../components/TrackCard";

const Container = styled.div`
  width: 100%;
  max-height: 100vh;
  font-family: BitterItalic;
  font-size: 20px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
`;

const SearchResultWrapper = styled.div`
  overflow: scroll;
  width: 90%;
  margin: 20px auto;
  display: flex;
  flex-direction: row;
  align-content: flex-start;
  flex-wrap: wrap;
  justify-content: space-around;
`;

const PaginationWrapper = styled.div`
  height: 50px;
  width: 100%;
  margin-bottom: 10px;
`;

Container.displayName = "Container";
SearchResultWrapper.displayName = "SearchResultWrapper";
PaginationWrapper.displayName = "PaginationWrapper";

export interface LandingPageState {
  sort: {
    type: string;
  };
  searchResult: {
    currentSearchName: string;
    totalAvailable: number;
    type: string;
    displayingResult: [] | IArtist[] | ITrack[];
    currentSearchParam:
      | IArtistMusixMatchAPIParams
      | ITrackMusixMatchAPIParams
      | {};
    paginatedResults: { [pageNumber: number]: IArtist[] | ITrack[] | [] };
    currentPage: number;
  };
}

class LandingPage extends React.Component<{}, LandingPageState> {
  constructor(props) {
    super(props);
    this.handleSearchRequest = this.handleSearchRequest.bind(this);
    this.sortResults = this.sortResults.bind(this);
    this.handlePageClick = this.handlePageClick.bind(this);
    this.getSearchResultComponents = this.getSearchResultComponents.bind(this);
    this.searchTracksByArtistID = this.searchTracksByArtistID.bind(this);
  }

  state: LandingPageState = {
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
  };

  async handleSearchRequest(type, params) {
    // Update state searchResult.type and result
    let result: IArtist[] | ITrack[];
    if (type === "artist") {
      result = (await searchForArtists(params)) as IArtist[];
    } else {
      result = (await searchForTracks(params)) as ITrack[];
    }

    // Sorting the result
    if (this.state.sort.type !== "NO_SORT") {
      result.sort(SortFuntions[this.state.sort.type]);
    }

    this.setState({
      ...this.state,
      searchResult: {
        currentSearchName: params.name,
        type: type,
        displayingResult: result,
        totalAvailable: result.length > 0 ? result[0].totalAvailable / 10 : 0,
        currentSearchParam: params,
        paginatedResults: {
          ...this.state.searchResult.paginatedResults,
          [params.page]: result
        },
        currentPage: parseInt(params.page)
      }
    });
  }

  async searchTracksByArtistID(params: ITrackMusixMatchAPIParams) {
    await this.handleSearchRequest("track", params);
    await this.setState({
      searchResult: {
        ...this.state.searchResult,
        currentSearchName: `get all tracks by ${params.name}`
      }
    });
  }

  getSearchResultComponents() {
    let results;
    if (this.state.searchResult.type === "artist") {
      results = this.state.searchResult.displayingResult as IArtist[];
      return results.map((eachResult: IArtist) => (
        <ArtistCardComponent
          name={eachResult.name}
          artistID={eachResult.artistID}
          country={eachResult.country}
          rating={eachResult.rating}
          twitterURL={eachResult.twitterURL}
          totalAvailable={eachResult.totalAvailable}
          key={eachResult.artistID}
          getAllTracks={this.searchTracksByArtistID}
        />
      ));
    } else {
      results = this.state.searchResult.displayingResult as ITrack[];
      return results.map((eachResult: ITrack) => (
        <TrackCardComponent
          artistName={eachResult.artistName}
          name={eachResult.name}
          explicit={eachResult.explicit}
          trackID={eachResult.trackID}
          hasLyrics={eachResult.hasLyrics}
          rating={eachResult.rating}
          numFavorite={eachResult.numFavorite}
          totalAvailable={eachResult.totalAvailable}
          key={eachResult.trackID}
        />
      ));
    }
  }

  async handlePageClick(page, pageSize) {
    if (this.state.searchResult.paginatedResults[page] !== undefined) {
      // If we already have the result in the paginatedResults prop, assign that
      // to the displaying result.
      await this.setState({
        ...this.state,
        searchResult: {
          ...this.state.searchResult,
          displayingResult: this.state.searchResult.paginatedResults[page],
          currentPage: page
        }
      });
    } else {
      // Else, query backend for that.
      const params = {
        ...this.state.searchResult.currentSearchParam,
        page: page
      };
      this.handleSearchRequest(this.state.searchResult.type, params);
    }
  }

  async sortResults(sortBy) {
    // Sort results in state by sortBy option
    let sortedPaginatedResult: { [page: string]: IArtist[] | ITrack[] };
    Object.keys(this.state.searchResult.paginatedResults).map(
      (each: string) => {
        sortedPaginatedResult = {
          ...sortedPaginatedResult,
          [each]: this.state.searchResult.paginatedResults[each].sort(
            SortFuntions[sortBy]
          )
        };
      }
    );
    this.setState({
      ...this.state,
      sort: {
        type: sortBy
      },
      searchResult: {
        ...this.state.searchResult,
        paginatedResults: sortedPaginatedResult,
        displayingResult:
          sortedPaginatedResult[this.state.searchResult.currentPage]
      }
    });
  }

  render() {
    return (
      <Container>
        <Header enableHomeButtonLink={true}></Header>
        <SearchComponent
          searchTriggered={this.handleSearchRequest}
          setSortType={this.sortResults}
        />
        <SearchResultBanner
          banner={`Search results for ${this.state.searchResult.currentSearchName}`}
        />
        {/* Show results */}
        <SearchResultWrapper>
          {this.getSearchResultComponents()}
        </SearchResultWrapper>
        {/* Show pagination */}
        <PaginationWrapper
          style={{
            fontFamily: "BitterRegular",
            textAlign: "center"
          }}
        >
          <Pagination
            size="medium"
            defaultCurrent={1}
            current={this.state.searchResult.currentPage}
            total={this.state.searchResult.totalAvailable}
            onChange={this.handlePageClick}
          />
        </PaginationWrapper>
      </Container>
    );
  }
}

export default LandingPage;
