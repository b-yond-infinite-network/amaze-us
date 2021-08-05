import React from "react"
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import './App.scss';

const LandingPage = React.lazy(() => import('./Containers/LandingPage/LandingPage'));


function App(props) {

  console.log(props)
  return (
    <div className="App">
      <React.Suspense
        fallback={<span>Loading...</span>}
      >
        <Router>
          <Switch>
            <Route exact path="/" render={() => <LandingPage />} />
          </Switch>
        </Router>

      </React.Suspense>
    </div>
  );
}

export default App;
