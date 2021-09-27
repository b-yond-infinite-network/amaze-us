import "./App.css";
import { LoginPage } from "./components/LoginPage";
import { MainPage } from "./components/MainPage";
import { Switch, Route } from "react-router-dom";

function App() {
  return (
    <div>
      <Header />
      <Switch>
        <Route exact path="/" component={MainPage} />
        <Route path="/login" component={LoginPage} />
      </Switch>
    </div>
  );
}

function Header() {
  return (
    <nav class="navbar navbar-dark bg-primary">
      <div className="row col-12 text-white text-center">
        <span className="h5"> Bus Drivers Shifts</span>
      </div>
    </nav>
  );
}

export default App;
