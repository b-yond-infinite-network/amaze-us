import React from "react";
import styled from "styled-components";
import colorCodes from "../styles/color-codes";
import { IArtist } from "../../../shared";

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

const GetTracks = styled.div`
  font-size: 15px;
  margin: 10px 0;
  color: ${colorCodes.sandTanShadow};
  cursor: pointer;
`;

Wrapper.displayName = "Wrapper";
Title.displayName = "Title";
MetaData.displayName = "MetaData";
GetTracks.displayName = "GetTracks";

const ArtistCardComponent: React.FC<IArtist> = (props: IArtist) => {
  return (
    <Wrapper>
      <Title>{props.artistName}</Title>
      <div style={{ display: "flex", flexDirection: "row" }}>
        <div>
          <MetaData>Rating: {props.artistRating}</MetaData>
          {/* Country */}
          {props.artistCountry !== "" ? (
            <MetaData>Country: {props.artistCountry.toUpperCase()}</MetaData>
          ) : null}
          {/* Twitter URL */}
          {props.artistTwitterURL !== "" ? (
            <MetaData>
              <a
                href={props.artistTwitterURL}
                style={{
                  textDecoration: "none",
                  color: colorCodes.sandTanShadow
                }}
                target="_blank"
              >
                Twitter
              </a>
            </MetaData>
          ) : null}
        </div>
        <GetTracks
          onClick={() => {
            console.log("Getting all tracks ");
          }}
        >
          Get all tracks
        </GetTracks>
      </div>
    </Wrapper>
  );
};

export default ArtistCardComponent;
