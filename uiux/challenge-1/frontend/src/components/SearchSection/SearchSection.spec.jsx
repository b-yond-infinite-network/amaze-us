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
import { SearchSection } from './SearchSection';

describe('SearchSection', () => {
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
    ctx.searchSpy = vi.fn(() => optionsFixture);
    ctx.setSpy = vi.fn();
    ctx.title = 'Test Title';
    ctx.component = render(
      <SearchSection
        setValue={ctx.setSpy}
        onSearch={ctx.searchSpy}
        title={ctx.title}
      />,
    );
    ctx.searchQuery = '123';
  });

  beforeEach(async ({
    component,
    searchQuery,
  }) => {
    await act(() => {
      fireEvent.change(
        component.container.querySelector('input[type="search"]'),
        { target: { value: searchQuery } },
      );
    });
    await act(() => {
      fireEvent.submit(component.container.querySelector('form'));
    });
  });

  it('should render title', ({ title, component }) => {
    expect(component.queryByText(title)).toBeInTheDocument();
  });

  it('should search on submit', async ({
    searchSpy,
    searchQuery,
  }) => {
    await waitFor(() => {
      expect(searchSpy)
        .toHaveBeenCalledWith(searchQuery);
    });
  });

  it('should show search results', async ({ component }) => {
    await waitFor(() => {
      optionsFixture.forEach(({ name }) => {
        expect(component.queryByText(name))
          .toBeInTheDocument();
      });
    });
  });

  it('should select item on click', async ({
    component,
    setSpy,
  }) => {
    await act(() => {
      fireEvent.click(component.queryByText(optionsFixture[0].name));
    });

    await waitFor(() => {
      expect(setSpy)
        .toHaveBeenCalledWith(optionsFixture[0].value);
    });
  });

  it('should clear item on click "clear" button', async ({ component, setSpy }) => {
    await act(() => {
      fireEvent.click(component.queryByText(/clear/i));
    });

    await waitFor(() => {
      expect(setSpy)
        .toHaveBeenCalledWith(null);
    });
  });
});
