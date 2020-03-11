import React from "react";
import styled from "styled-components";
import colorCodes from "../styles/color-codes";

import { ILyrics } from "../../../shared";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 75%;
`;

const LyricsContent = styled.div`
  font-family: BitterRegular;
  font-size: 20px;
  color: ${colorCodes.deepMatteGrey};
`;

const BackToSearch = styled.div`
  font-family: BitterItalic;
  font-size: 15px;
  text-align: center;
  cursor: pointer;
`;

Wrapper.displayName = "Wrapper";
LyricsContent.displayName = "LyricsContent";
BackToSearch.displayName = "BackToSearch";

interface LyricsProps extends ILyrics {
  handleClose: Function;
}

const Lyrics: React.FC<LyricsProps> = (props: LyricsProps) => {
  return (
    <Wrapper>
      <LyricsContent>{props.lyricsContent}</LyricsContent>;
      <BackToSearch
        onClick={() => {
          props.handleClose();
        }}
      >
        back to search
      </BackToSearch>
    </Wrapper>
  );
};

export default Lyrics;
