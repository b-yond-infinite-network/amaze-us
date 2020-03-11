import ComponentTests from "./components";
import APITests from "./api";

describe("Initializing tests ", () => {
  describe("Testing components", () => {
    ComponentTests.forEach((each: Function) => {
      each();
    });
  });

  describe("Testing API util methods", () => {
    APITests();
  });
});
