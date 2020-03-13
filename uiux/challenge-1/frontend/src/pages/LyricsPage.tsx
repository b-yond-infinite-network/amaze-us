import React from "react";
import styled from "styled-components";

import Lyrics from "../components/Lyrics";
import SearchResultBanner from "../components/SearchResultBanner";
import Header from "../components/Header";
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
    }).then(result => {
      setLyricsContent(result.lyricsContent);
    });
    document.title = `${parsedQueryObject.trackName}`;
  });

  const parsedQueryString = props.location.search.split("?");
  const parsedQueryObject: LyricsPageProps = {
    trackID: parsedQueryString[1].split("=")[1],
    trackName: parsedQueryString[2].split("=")[1].replace(/%20/g, " ")
  };

  return (
    <div>
      <Header enableHomeButtonLink={true}></Header>
      <Wrapper>
        <SearchResultBanner banner={parsedQueryObject.trackName} />
        <Lyrics lyricsContent={lyricsContent} />
      </Wrapper>
    </div>
  );
};

export default LyricsPage;
