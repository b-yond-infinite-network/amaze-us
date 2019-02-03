import { cleanup, render } from "react-testing-library";
import React from "react";
import useDebounce from "../useDebounce";

jest.useFakeTimers();
afterEach(cleanup);

function Component({ value }) {
  const debouncedValue = useDebounce(value);
  return <span>{debouncedValue}</span>;
}

test("filter items depending on the input value", () => {
  const initialValue = "test";
  const updatedValue = "new value";
  // render dummy component
  const { container, rerender } = render(<Component value={initialValue} />);

  expect(container.textContent).toBe(initialValue);

  // update hook with new initial value
  rerender(<Component value={updatedValue} />);

  // value shouldn't change yet
  expect(container.textContent).toBe(initialValue);

  // execute debounce
  jest.runAllTimers();
  expect(container.textContent).toBe(updatedValue);
});
