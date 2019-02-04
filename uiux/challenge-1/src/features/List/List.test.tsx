import React from "react";
import { render, waitForElement } from "react-testing-library";

import List from "./List";
import Artist from "../../shared/types/artist";

const items: Artist[] = [
  { artist: { artist_id: 1, artist_name: "foo" } },
  { artist: { artist_id: 2, artist_name: "bar" } }
];

test("renders the empty List component", async () => {
  const { container } = render(<List items={[]} initialized={false} />);

  // should not render anything
  expect(container.innerHTML).toBe("");
});

test("renders a List of items", async () => {
  // pass items collection
  const { getAllByTestId } = render(<List items={items} initialized />);
  const renderedItems = await waitForElement(() =>
    getAllByTestId("List-listItem")
  );

  // should display the results
  expect(renderedItems).toHaveLength(2);
});

test("renders Not Found when items don't exist", async () => {
  const { getByTestId } = render(<List items={[]} initialized />);

  // no results
  const notFound = getByTestId("List-notFound");
  expect(notFound).toBeDefined();
});
