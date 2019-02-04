import React from "react";
import { render, waitForElement } from "react-testing-library";
import Artist from "../../shared/types/artist";
import ArtistList from "./ArtistList";

const items: Artist[] = [
  { artist: { artist_id: 1, artist_name: "foo" } },
  { artist: { artist_id: 2, artist_name: "bar" } }
];

test("renders the empty ArtistList component", async () => {
  const { container } = render(<ArtistList items={[]} initialized={false} />);

  // should not render anything
  expect(container.innerHTML).toBe("");
});

test("renders an ArtistList with items", async () => {
  // pass items collection
  const { getAllByTestId } = render(<ArtistList items={items} initialized />);
  const renderedItems = await waitForElement(() =>
    getAllByTestId("List-listItem")
  );

  // should display the results
  expect(renderedItems).toHaveLength(2);
});

test("renders Not Found when there are no items", async () => {
  const { getByTestId } = render(<ArtistList items={[]} initialized />);

  // no results
  const notFound = getByTestId("ArtistList-notFound");
  expect(notFound).toBeDefined();
});
