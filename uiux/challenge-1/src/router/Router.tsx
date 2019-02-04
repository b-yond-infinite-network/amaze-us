import React, { lazy, Suspense } from "react";
import { Router, RouteComponentProps, Link } from "@reach/router";
import Loader from "../shared/components/Loader";

const HomePage = lazy(() => import("../pages/Home"));
const ArtistPage = lazy(() => import("../pages/Artist"));
const TrackPage = lazy(() => import("../pages/Track"));
const NotFound: React.FC<RouteComponentProps> = () => <div>Not found</div>;

const AppRouter = () => (
  <Suspense fallback={<Loader />}>
    <Router>
      <HomePage path="/" />
      <ArtistPage path="/artist/:id" />
      <TrackPage path="/track/:id" />
      <NotFound default />
    </Router>
  </Suspense>
);

export default AppRouter;
