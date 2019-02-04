import React, { lazy, Suspense } from "react";
import { Router, RouteComponentProps } from "@reach/router";
import Loader from "../shared/components/Loader";

const HomePage = lazy(() => import("../pages/Home"));
const NotFound: React.FC<RouteComponentProps> = () => <div>Not found</div>;

const AppRouter = () => (
  <Suspense fallback={<Loader />}>
    <Router>
      <HomePage path="/" />
      <NotFound default />
    </Router>
  </Suspense>
);

export default AppRouter;
