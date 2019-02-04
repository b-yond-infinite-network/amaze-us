import { artistSearch } from "../artist";

jest.mock("../index");

it("should return the list of artists by query", async () => {
  const results = await artistSearch("test");
  expect(results.length).toEqual(2);
});
