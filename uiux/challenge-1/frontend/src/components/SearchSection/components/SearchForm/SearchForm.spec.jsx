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
import { SearchForm } from './SearchForm';

describe('SearchForm', () => {
  beforeEach(ctx => {
    ctx.searchSpy = vi.fn();
    ctx.component = render(<SearchForm onSearch={ctx.searchSpy} />);
  });

  it('should render search form', ({ component }) => {
    const form = component.container.querySelector('form');

    expect(form).toBeInTheDocument();

    expect(form.querySelector('input[type="search"]'))
      .toBeInTheDocument();
  });

  it('should search by input value', async ({ component, searchSpy }) => {
    const searchQuery = '123';

    await act(() => {
      fireEvent.change(
        component.container.querySelector('input[type="search"]'),
        { target: { value: searchQuery } },
      );

      fireEvent.submit(component.container.querySelector('form'));
    });

    await waitFor(() => {
      expect(searchSpy).toBeCalledWith(searchQuery);
    });
  });
});
