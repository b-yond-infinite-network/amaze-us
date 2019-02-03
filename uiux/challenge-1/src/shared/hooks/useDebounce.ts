import { useState, useEffect } from "react";

/**
 * Creates a debounced function that delays invoking a callback function
 */
export default function useDebounce(
  initialValue: any,
  wait: number = 500
): string {
  const [value, setValue] = useState(initialValue);

  useEffect(() => {
    const fn = setTimeout(() => {
      setValue(initialValue);
    }, wait);

    return () => {
      clearTimeout(fn);
    };
  }, [initialValue, wait]);

  return value;
}
