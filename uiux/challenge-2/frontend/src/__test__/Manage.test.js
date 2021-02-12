import React from 'react'
import {rest} from 'msw'
import {setupServer} from 'msw/node'
import {act, fireEvent, render, waitFor} from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect'
import Manage from '../Pages/Manage';


const server = setupServer(
  rest.get('/v1/baby/request', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        requests: [
          {name: 'first baby request', status: 'new', id: '1'},
          {name: 'second baby request', status: 'new', id: '2'},
        ]
      }))
  }),
  rest.put('/v1/baby/request', (req, res, ctx) => {
    return res(ctx.status(200))
  })
);

beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

test('Load and display the baby requests', async () => {
  const {getByText, getAllByText} = render(<Manage/>);

  await waitFor(() => getAllByText(/first/i));

  expect(getAllByText('NEW').length).toBe(2);
  expect(getByText('second baby request')).toBeTruthy()
});

test('No new baby requests', async () => {
  server.use(
    rest.get('/v1/baby/request', (req, res, ctx) => {
      return res(ctx.status(200))
    })
  );
  const {getByText} = render(<Manage/>);

  await waitFor(() => expect(getByText('There are no new requests')).toBeTruthy());

});


test('Approve the baby requests', async () => {
  const {getAllByText, getAllByRole} = render(<Manage/>);

  await waitFor(() => getAllByText(/first/i));

  expect(getAllByRole('button', {name: /requestButton/i}).length).toBe(4);
  act(() => {
    fireEvent.click(getAllByRole('button', {name: /requestButton/i})[0]);
  });
});


test('Fail to load the baby requests', async () => {
  server.use(
    rest.get('/v1/baby/request', (req, res, ctx) => {
      return res(ctx.status(500))
    })
  );
  const {getByText, queryByText} = render(<Manage/>);

  expect(queryByText('NEW')).toBeNull();
  await waitFor(() => expect(getByText(/Unexpected/i)).toBeTruthy());
});
