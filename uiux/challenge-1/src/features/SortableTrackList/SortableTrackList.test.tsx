import React from "react";
import {
  fireEvent,
  getBySelectText,
  render,
  waitForElement
} from "react-testing-library";
import Track from "../../shared/types/track";
import SortableTrackList from "../SortableTrackList";

jest.mock("../../shared/api");

const tracks: Track[] = [
  {
    track: {
      track_id: 2,
      track_name: "foo",
      has_lyrics: 1,
      artist_id: 2,
      artist_name: "foo",
      album_name: "Album 2"
    }
  },
  {
    track: {
      track_id: 1,
      track_name: "hello",
      has_lyrics: 1,
      artist_id: 1,
      artist_name: "hello",
      album_name: "Album 1"
    }
  },
  {
    track: {
      track_id: 3,
      track_name: "bar",
      has_lyrics: 1,
      artist_id: 3,
      artist_name: "bar",
      album_name: "Album 3"
    }
  }
];

test("sorts the track list by name (desc)", async () => {
  const { getByTestId, getAllByTestId } = render(
    <SortableTrackList artistName="John Doe" tracks={tracks} />
  );

  // change selected option
  const field = getByTestId("Select-field");

  fireEvent.change(field, { target: { value: "track-desc" } });

  const container = getByTestId("Select-container");

  // verify option has been selected
  const selectedOption = waitForElement(() =>
    getBySelectText(container, "Track name (desc)")
  );
  expect(selectedOption).toBeTruthy();

  // list should be sorted (desc)
  const list = await waitForElement(() => getAllByTestId("Card-title"));
  expect(list[0].innerHTML).toBe("bar");
});

test("sorts the track list by name (asc)", async () => {
  const { getByTestId, getAllByTestId } = render(
    <SortableTrackList artistName="John Doe" tracks={tracks} />
  );

  // change selected option
  const field = getByTestId("Select-field");

  fireEvent.change(field, { target: { value: "track-asc" } });

  // verify option has been selected
  const selectedOption = waitForElement(() =>
    getBySelectText(container, "Track name (asc)")
  );
  expect(selectedOption).toBeTruthy();

  // list should be sorted (asc)
  const list = await waitForElement(() => getAllByTestId("Card-title"));
  expect(list[0].innerHTML).toBe("hello");

  const container = getByTestId("Select-container");

  // reset to default option
  fireEvent.change(field, { target: { value: "" } });
  // list should be displayed by default
  const defaultList = await waitForElement(() => getAllByTestId("Card-title"));
  expect(defaultList[0].innerHTML).toBe("foo");
});
