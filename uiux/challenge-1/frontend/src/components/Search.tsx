import React from "react";
import styled from "styled-components";
import { Select, Input, Menu, Dropdown } from "antd";

import colorCodes from "../styles/color-codes";
import { device } from "../styles/breakpoints";

const { Search } = Input;
const { Option } = Select;
const { SubMenu } = Menu;

const SearchAreaWrapper = styled.div`
  background: ${colorCodes.areYaYellow};

  display: flex;
  flex-direction: row;

  padding: 20px 100px 20px 100px;
  margin: 0 auto;
  text-align: center;
  justify-content: space-between;

  @media ${device.mobileL} {
    display: flex;
    flex-direction: column;
    padding: 10px 50px 10px 50px;
  }
`;

SearchAreaWrapper.displayName = "SearchAreaWrapper";

const ArtistSortOptions = (
  <Menu style={{ fontFamily: "BitterRegular" }} id="artistSortOptions">
    <SubMenu title="Popularity" style={{ width: 150 }}>
      <Menu.Item style={{ fontFamily: "BitterRegular" }}>Low to high</Menu.Item>
      <Menu.Item style={{ fontFamily: "BitterRegular" }}>High to low</Menu.Item>
    </SubMenu>
    <SubMenu title="Name">
      <Menu.Item style={{ fontFamily: "BitterRegular" }}>A - Z</Menu.Item>
      <Menu.Item style={{ fontFamily: "BitterRegular" }}>Z - A</Menu.Item>
    </SubMenu>
  </Menu>
);

const TrackSortOptions = (
  <Menu style={{ fontFamily: "BitterRegular" }} id="trackSortOptions">
    <Menu.Item>Title</Menu.Item>
    <Menu.Item>Number of lyrics</Menu.Item>
    <SubMenu title="Duration">
      <Menu.Item style={{ fontFamily: "BitterRegular" }}>
        Longer to shorter
      </Menu.Item>
      <Menu.Item style={{ fontFamily: "BitterRegular" }}>
        Shorter to longer
      </Menu.Item>
    </SubMenu>
    <SubMenu title="Popularity">
      <Menu.Item style={{ fontFamily: "BitterRegular" }}>Low to high</Menu.Item>
      <Menu.Item style={{ fontFamily: "BitterRegular" }}>High to low</Menu.Item>
    </SubMenu>
    <SubMenu title="Name">
      <Menu.Item style={{ fontFamily: "BitterRegular" }}>A - Z</Menu.Item>
      <Menu.Item style={{ fontFamily: "BitterRegular" }}>Z - A</Menu.Item>
    </SubMenu>
  </Menu>
);

export function useSearchType(value) {
  const [searchType, setSearchType_] = React.useState(value);
  const setSearchType = value => setSearchType_(value);
  return [searchType, setSearchType];
}

const SearchComponent: React.FC<{}> = props => {
  const [searchType, setSearchType] = useSearchType("artist");
  return (
    <div>
      <SearchAreaWrapper>
        <Select
          bordered={false}
          defaultValue={searchType}
          style={{
            borderRadius: 15,
            fontFamily: "BitterRegular",
            background: "#cadeed"
          }}
          dropdownStyle={{ fontFamily: "BitterRegular" }}
          onChange={value => {
            setSearchType(value);
          }}
          value={searchType}
        >
          <Option value="artist">Artist</Option>
          <Option value="track">Track</Option>
        </Select>
        <Search
          placeholder="Search for your favorite artists or tracks"
          onSearch={value => console.log(value)} // TODO Add functionality here!
          style={{ borderRadius: 15 }}
        />
        <Dropdown
          overlay={
            searchType === "track" ? TrackSortOptions : ArtistSortOptions
          }
        >
          <button
            style={{
              textDecoration: "none",
              fontSize: 15,
              width: 100,
              background: "#cadeed",
              borderRadius: "15px"
            }}
          >
            Sort
          </button>
        </Dropdown>
      </SearchAreaWrapper>
    </div>
  );
};

export default SearchComponent;
