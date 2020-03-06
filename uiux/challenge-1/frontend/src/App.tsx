import * as React from "react";
import styled from "styled-components";
import { HashRouter as Router, Route, Switch } from "react-router-dom";

// Use this to declare global styles for this
const AppContainer = styled.div`
  font-family: BitterItalic;
  font-size: 20px;
  background: #f5f5f5;
`;

const AppLandingPage: React.SFC<any> = (props: any) => {
  return <AppContainer>Hello from Drunk Karaoke!</AppContainer>;
};

class App extends React.Component<{}, {}> {
  render(): JSX.Element {
    return (
      <Router>
        <Switch>
          <Route exact path="/" component={AppLandingPage} />
        </Switch>
      </Router>
    );
  }
}

export default App;
