import * as React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

import colorCodes from "../styles/color-codes";

interface HeaderProps {
  enableHomeButtonLink: boolean;
}

const Wrapper = styled.div`
  text-align: center;
  margin: 0 auto;
  width: 100%;
  height: 80px;
  background: ${colorCodes.midnightBlue};
  font-family: BitterRegular;

  & div {
    padding: 10px;
  }
`;

const Title = styled.div`
  color: ${colorCodes.silverFox};
  text-align: center;
  font-size: 30px;

  & a {
    color: ${colorCodes.silverFox};
    text-decoration: none;
    :hover {
      color: ${colorCodes.sandTan};
    }
  }
`;

Wrapper.displayName = "Wrapper";
Title.displayName = "Title";

const Header: React.SFC<HeaderProps> = (props: HeaderProps) => {
  return (
    <Wrapper>
      <Title>
        {props.enableHomeButtonLink ? (
          <Link to="/">Karaoke Needs Words!</Link>
        ) : (
          "Karaoke Needs Words!"
        )}
      </Title>
    </Wrapper>
  );
};

export default Header;
