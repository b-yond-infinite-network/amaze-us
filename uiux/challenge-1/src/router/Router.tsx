import React, { lazy, Suspense } from "react";
import { Router } from "@reach/router";

const HomePage = lazy(() => import("../pages/Home"));

const AppRouter = () => (
  <div>
    <Suspense fallback={<div>loading...</div>}>
      <Router>
        <HomePage path="/" />
      </Router>
    </Suspense>
  </div>
);

export default AppRouter;
