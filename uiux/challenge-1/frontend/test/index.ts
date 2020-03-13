import ComponentTests from "./components";
import APITests from "./api";
import UtilTests from "./util";
import PageTests from "./pages";

describe("Initializing tests ", () => {
  describe("Testing components", () => {
    ComponentTests.forEach((each: Function) => {
      each();
    });
  });

  describe("Testing API util methods", () => {
    APITests();
  });

  describe("Testing Util tests", () => {
    UtilTests();
  });

  describe("Testing Pages", () => {
    PageTests.forEach((each: Function) => {
      each();
    });
  });
});
