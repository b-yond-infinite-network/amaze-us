import { fetchArtists } from "../artist";

jest.mock("../index");

it("should return the list of artists by query", async () => {
  const results = await fetchArtists("test");
  expect(results.length).toEqual(2);
});
