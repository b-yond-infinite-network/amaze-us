import {
  beforeEach,
  describe,
  it,
  expect,
  vi,
} from 'vitest';
import {
  render,
  fireEvent,
  waitFor,
  act,
} from '@testing-library/react';
import { SearchOptions } from './SearchOptions';
import styles from './styles.module.css';

describe('SearchOptions', () => {
  const optionsFixture = [
    {
      key: '1',
      name: '123_A',
      value: 1,
    },
    {
      key: '2',
      name: '123_B',
      value: {},
    },
    {
      key: '3',
      name: '123_C',
      value: '1',
    },
  ];

  beforeEach(ctx => {
    [ctx.selectedOption] = optionsFixture;
    ctx.selectSpy = vi.fn();
    ctx.component = render(
      <SearchOptions
        options={optionsFixture}
        onSelect={ctx.selectSpy}
        value={ctx.selectedOption.value}
      />,
    );
  });

  it('should display all options', ({ component }) => {
    optionsFixture.forEach(({ name }) => {
      expect(component.queryByText(name))
        .toBeInTheDocument();
    });
  });

  it('should highlight current value', ({ component, selectedOption }) => {
    expect(component.queryByText(selectedOption.name)).toHaveClass(styles.selected);
  });

  it('should select option on click', async ({ component, selectSpy }) => {
    const newSelectedOption = optionsFixture[1];

    await act(() => {
      fireEvent.click(component.queryByText(newSelectedOption.name));
    });

    await waitFor(() => {
      expect(selectSpy).toBeCalledWith(newSelectedOption.value);
    });
  });
});
