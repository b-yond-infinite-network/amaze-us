import ComponentTests from "./components";

describe("Initializing tests ", () => {
  it("Testing components", done => {
    ComponentTests.forEach((each: Function) => {
      each();
    });
    done();
  });
});
