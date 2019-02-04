import { artistSearch, getArtist } from "../artist";
import Artist from "../../types/artist";

jest.mock("../index");

it("should return the list of artists by query", async () => {
  const response: Artist[] = await artistSearch("test");
  expect(response.length).toEqual(2);
});

it("should get an artist by id", async () => {
  const response: Artist = await getArtist(1);
  expect(response.artist.artist_id).toEqual(1);
});
