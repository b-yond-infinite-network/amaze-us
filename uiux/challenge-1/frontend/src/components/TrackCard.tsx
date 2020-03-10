import React from "react";
import styled from "styled-components";
import colorCodes from "../styles/color-codes";
import { ITrack } from "../../../shared";

const Wrapper = styled.div`
  width: 350px;
  background: ${colorCodes.deepMatteGrey};
  border-radius: 25px 25px 25px 25px;
  margin: 10px;
`;

const Title = styled.div`
  font-size: 20px;
  text-align: center;
  color: ${colorCodes.areYaYellow};
`;

const MetaData = styled.div`
  font-size: 15px;
  text-align: left;
  margin: 10px 50px;

  color: ${colorCodes.silverFox};
`;

const GetLyrics = styled.div`
  font-size: 15px;
  margin: 10px 0;
  color: ${colorCodes.sandTanShadow};
  cursor: pointer;
`;

Wrapper.displayName = "Wrapper";
Title.displayName = "Title";
MetaData.displayName = "MetaData";
GetLyrics.displayName = "GetLyrics";

const TrackCardComponent: React.FC<ITrack> = (props: ITrack) => {
  return (
    <Wrapper>
      <Title>{props.name}</Title>
      <div style={{ display: "flex", flexDirection: "row" }}>
        <div>
          <MetaData>Artist: {props.artistName}</MetaData>
          <MetaData>Rating: {props.rating}</MetaData>
          {props.explicit ? (
            <MetaData style={{ color: "red" }}>Explicit content</MetaData>
          ) : null}
        </div>
        {props.hasLyrics ? (
          <GetLyrics
            onClick={() => {
              console.log("Getting lyrics");
            }}
          >
            Get lyrics
          </GetLyrics>
        ) : null}
      </div>
    </Wrapper>
  );
};

export default TrackCardComponent;
