import React from "react";
import { render, waitForElement } from "react-testing-library";
import Track from "../../shared/types/track";
import TrackList from "./TrackList";

const items: Track[] = [
  {
    track: {
      track_id: 1,
      track_name: "first",
      has_lyrics: 1,
      artist_id: 1,
      artist_name: "foo",
      album_name: "Album 1"
    }
  },
  {
    track: {
      track_id: 2,
      track_name: "second",
      has_lyrics: 0,
      artist_id: 2,
      artist_name: "bar",
      album_name: "Album 2"
    }
  }
];

test("renders the empty List component", async () => {
  const { container } = render(<TrackList items={[]} initialized={false} />);

  // should not render anything
  expect(container.innerHTML).toBe("");
});

test("renders a TrackList with items", async () => {
  // pass items collection
  const { getAllByTestId } = render(<TrackList items={items} initialized />);
  const renderedItems = await waitForElement(() =>
    getAllByTestId("List-listItem")
  );

  // should display the results
  expect(renderedItems).toHaveLength(2);
});

test("renders Not Found when there are no items", async () => {
  const { getByTestId } = render(<TrackList items={[]} initialized />);

  // no results
  const notFound = getByTestId("TrackList-notFound");
  expect(notFound).toBeDefined();
});
