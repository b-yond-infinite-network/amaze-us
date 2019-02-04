import React from "react";
import { render, flushEffects, waitForElement } from "react-testing-library";

import ArtistPage from "../Artist";

jest.mock("../../shared/api");

test("renders the Artist detail page", async () => {
  const { getByTestId } = render(<ArtistPage id="1" />);
  await flushEffects();

  const list = await waitForElement(() => getByTestId("List-list"));

  expect(list).toBeDefined();
  // display only tracks with lyrics
  expect(list.children).toHaveLength(1);
});
