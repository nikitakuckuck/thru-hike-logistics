
import {BrowserRouter as Router, Route, Link, Switch } from "react-router-dom";
import About from "./components/About";
import Home from "./components/Home";
import TownExitDisplay from "./components/ExitItemDisplay";
import TrailDeleteConfirmation from "./components/TrailDeleteConfirmation";
import TrailDisplay from './components/TrailDisplay';
import TrailForm from './components/TrailForm';
import TrailSectionDeleteConfirmation from "./components/TrailSectionDeleteConfirmation";
import TrailSectionDisplay from './components/TrailSectionDisplay';
import TrailSectionForm from "./components/TrailSectionForm";
import SectionDetails from "./components/SectionDetails";
import SectionAlertForm from "./components/SectionAlertForm";

function App() {
  return (
    <div className="App">
      <Router>
        <nav className="navbar navbar-expand-md navbar-dark bg-dark">
          <button className="navbar-toggler" type="button" data-toggle = "collapse" data-target = "#menu" aria-controls= "#menu" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse " id="menu">
            <ul className="navbar-nav mr-auto">
              <li className="nav-item">
                <Link to="/" className="nav-link">Section Summary Home</Link>
              </li>
              <li className="nav-item">
                <Link to="/trails" className="nav-link">Trails</Link>
              </li>
              <li className="nav-item">
                <Link to="/sections" className="nav-link">Sections</Link>
              </li>
              <li className="nav-item">
                <Link to="/about" className="nav-link">About</Link>
              </li>
            </ul>
          </div>
        </nav>

        <Switch>    
          <Route exact path = "/">
            <Home/>
          </Route>
          <Route exact path="/trails">
            <TrailDisplay/>
          </Route>
          <Route path={["/trails/add", "/trails/edit/:editTrailId"]}>
            <TrailForm/>
          </Route>
          <Route path={"/trails/delete/:deleteTrailId"}>
            <TrailDeleteConfirmation/>
          </Route>
          <Route exact path="/sections">
            <TrailSectionDisplay/>
          </Route>
          <Route path={["/sections/add", "/sections/edit/:editSectionId"]}>
            <TrailSectionForm/>
          </Route>
          <Route path={"/sections/delete/:deleteSectionId"}>
            <TrailSectionDeleteConfirmation/>
          </Route>
          <Route path="/about">
            <About/>
          </Route>
          <Route path="/exit-checklist">
            <TownExitDisplay/>
          </Route>
          <Route exact path="/sections/details/:sectionDetailsId">
            <SectionDetails/>
          </Route>
          <Route exact path={"/sections/add-alert/:sectionId"}>
            <SectionAlertForm/>
          </Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
