import {
  createHistory,
  createMemorySource,
  LocationProvider
} from "@reach/router";
import React from "react";
import { cleanup, render, waitForElement } from "react-testing-library";
import Router from "./Router";

afterEach(cleanup);

function renderWithRouter(
  app: any,
  { route = "/", history = createHistory(createMemorySource(route)) } = {}
) {
  return {
    ...render(<LocationProvider history={history}>{app}</LocationProvider>),
    history
  };
}

test("navigate to the Home page", async () => {
  const { getByTestId } = renderWithRouter(<Router />);

  const homePage = await waitForElement(() => getByTestId("page-home"));

  expect(homePage).toBeDefined();
});

test("navigate to the other pages", async () => {
  const {
    getByTestId,
    history: { navigate }
  } = renderWithRouter(<Router />, {
    route: "/artist/1"
  });

  const page = await waitForElement(() => getByTestId("page-artist"));

  expect(page).toBeDefined();

  // navigate to the track lyrics page
  await navigate("/track/1");
  expect(getByTestId("page-track")).toBeDefined();
});

test("landing on a bad page", () => {
  const { container } = renderWithRouter(<Router />, {
    route: "/unknown-route"
  });
  expect(container.innerHTML).toMatch("Not found");
});
