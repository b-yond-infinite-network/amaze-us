import Track from "../../types/tracks";
import { searchTracks } from "../tracks";

jest.mock("../index");

it("should return the list of tracks by artist id", async () => {
  const response: Track[] = await searchTracks("1");
  expect(response.length).toEqual(1);
});
