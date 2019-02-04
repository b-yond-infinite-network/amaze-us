import React from "react";
import {
  render,
  flushEffects,
  waitForElement,
  fireEvent
} from "react-testing-library";

import HomePage from "../Home";

jest.mock("../../shared/api");

test("renders the Home page", async () => {
  const { getByTestId } = render(<HomePage />);
  await flushEffects();

  const queryInput = getByTestId("Input-field");

  fireEvent.change(queryInput, { target: { value: "test" } });

  const list = await waitForElement(() => getByTestId("List-list"));

  expect(list).toBeDefined();
  expect(list.children).toHaveLength(2);
});
