import { fetchArtist } from "../artist";

jest.mock("../index");

it("should return the list of artists by query", async () => {
  const results = await fetchArtist("test");
  expect(results.length).toEqual(2);
});
