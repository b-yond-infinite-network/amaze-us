import React from "react";
import styled from "styled-components";

interface SearchResultBannerProps {
  banner: string;
}

const Wrapper = styled.div`
  width: 100%;
  text-align: center;
  padding: 8px 10px;
`;

Wrapper.displayName = "Wrapper";

const SearchResultBanner: React.FC<SearchResultBannerProps> = (
  props: SearchResultBannerProps
) => {
  return <Wrapper>{props.banner}</Wrapper>;
};

export default SearchResultBanner;
