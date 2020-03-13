import React from "react";
import styled from "styled-components";
import colorCodes from "../styles/color-codes";

import { ILyrics } from "../../../shared";

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin: 25px 0;
`;

const LyricsContent = styled.div`
  font-family: BitterRegular;
  font-size: 20px;
  color: ${colorCodes.deepMatteGrey};
`;

Wrapper.displayName = "Wrapper";
LyricsContent.displayName = "LyricsContent";

interface LyricsProps extends ILyrics {
  handleClose: Function;
}

const Lyrics: React.FC<LyricsProps> = (props: LyricsProps) => {
  return (
    <Wrapper>
      <LyricsContent>{props.lyricsContent}</LyricsContent>;
    </Wrapper>
  );
};

export default Lyrics;
