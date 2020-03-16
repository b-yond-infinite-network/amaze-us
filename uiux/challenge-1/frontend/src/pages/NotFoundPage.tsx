import React from "react";
import styled from "styled-components";

import Header from "../components/Header";

const Wrapper = styled.div`
  width: 100%;
  max-height: 100vh;
  font-family: BitterItalic;
  font-size: 20px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  overflow: scroll;
`;

const Message = styled.div`
  text-align: center;
  width: 100%;
`;

Wrapper.displayName = "Wrapper";
Message.displayName = "Message";

const NotFoundPage: React.FC<{}> = props => {
  return (
    <Wrapper>
      <Header enableHomeButtonLink={true}></Header>
      <Message>The page you're looking for, does not exist.</Message>
    </Wrapper>
  );
};

export default NotFoundPage;
