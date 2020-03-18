import React from "react";
import styled from "styled-components";
import BounceLoader from "react-spinners/BounceLoader";

import Lyrics from "../components/Lyrics";
import SearchResultBanner from "../components/SearchResultBanner";
import Header from "../components/Header";
import colorCodes from "../../src/styles/color-codes";
import { searchForLyrics } from "../api/lyrics";

const Wrapper = styled.div`
  width: 100%;
  max-height: 100vh;
  font-family: BitterItalic;
  font-size: 20px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  padding: 100px 100px;
  overflow: scroll;
`;

Wrapper.displayName = "Wrapper";

interface LyricsPageProps {
  trackName: string;
  trackID: string;
  location?: {
    search: string;
  };
}

const LyricsPage: React.FC<LyricsPageProps> = (props: LyricsPageProps) => {
  const [lyricsContent, setLyricsContent] = React.useState("");
  React.useEffect(() => {
    searchForLyrics({
      trackID: parsedQueryObject.trackID
    })
      .then(result => {
        setLyricsContent(result.lyricsContent);
      })
      .catch(error => {
        setLyricsContent(
          `¯\\_(ツ)_/¯ Backend is not responding. Please check if the backend server is up and running.`
        );
      });
    document.title = `${parsedQueryObject.trackName}`;
  });

  const parsedQueryString = props.location.search.split("?");
  const parsedQueryObject: LyricsPageProps = {
    trackID: parsedQueryString[1].split("=")[1],
    trackName: decodeURI(parsedQueryString[2].split("=")[1])
  };

  return (
    <div>
      <Header enableHomeButtonLink={true}></Header>
      <Wrapper>
        <SearchResultBanner banner={parsedQueryObject.trackName} />
        {lyricsContent === "" ? (
          <div style={{ margin: "20px auto" }}>
            <BounceLoader size={50} color={colorCodes.sandTan} loading={true} />
          </div>
        ) : (
          ""
        )}
        <Lyrics lyricsContent={lyricsContent} />
      </Wrapper>
    </div>
  );
};

export default LyricsPage;
