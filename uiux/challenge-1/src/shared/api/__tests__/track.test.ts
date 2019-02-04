import Track, { Lyrics } from "../../types/track";
import { searchTracks, getTrack, getLyrics } from "../track";

jest.mock("../index");

it("should return the list of tracks by artist id", async () => {
  const response: Track[] = await searchTracks("1");
  expect(response.length).toEqual(1);
});

it("should get a track by id", async () => {
  const response: Track = await getTrack("1");
  expect(response.track.track_id).toEqual(1);
});

it("should get the lyrics by track id", async () => {
  const response: Lyrics = await getLyrics("1");
  expect(response.lyrics_body).toEqual("hello world");
});
