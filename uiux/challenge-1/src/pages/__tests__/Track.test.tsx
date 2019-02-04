import React from "react";
import { render, flushEffects, waitForElement } from "react-testing-library";

import TrackPage from "../Track";

jest.mock("../../shared/api");

test("renders the Lyrics detail page", async () => {
  const { getByTestId } = render(<TrackPage id="1" />);
  await flushEffects();

  const lyrics = await waitForElement(() => getByTestId("Track-lyrics"));

  expect(lyrics).toBeDefined();
  expect(lyrics.innerHTML).toEqual("hello world");
});
