import React from 'react'
import {rest} from 'msw'
import {setupServer} from 'msw/node'
import {fireEvent, render, waitFor, act} from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect'
import Pioneers from '../Pages/Pioneers';


const server = setupServer(
  rest.get('/v1/population', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({amount: '1234'}))
  }),
  rest.post('/v1/baby/request', (req, res, ctx) => {
    return res(
      ctx.status(201),
      ctx.json({}))
  })
);

beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

test('Load and display the population', async () => {
  const {getByText} = render(<Pioneers/>);

  await waitFor(() => getByText('1234'));

  expect(getByText('1234')).toBeTruthy();
});


test('handles server error', async () => {
  server.use(
    rest.get('/v1/population', (req, res, ctx) => {
      return res(ctx.status(500))
    })
  );

  const {getByText} = render(<Pioneers/>);

  await waitFor(() => getByText('N/A'));

  expect(getByText('N/A')).toBeTruthy()
});

test('Error snackbar on when server is down while requesting baby', async () => {
  server.use(
    rest.post('/v1/baby/request', (req, res, ctx) => {
      return res(ctx.status(500))
    })
  );

  const {getByRole, getByText} = render(<Pioneers/>)


  fireEvent.input(getByRole('textbox', {name: /name/i}), {
    target: {value: 'Example'}
  });

  act(() => {
    fireEvent.submit(getByRole('button', {name: /sendRequest/i}));
  });

  await waitFor(() => {expect(getByText(/500/i)).toBeTruthy()})
});

test('Can make a baby request', async () => {
  const {getByRole} = render(<Pioneers/>);


  fireEvent.input(getByRole('textbox', {name: /name/i}), {
    target: {value: 'Example'}
  });

  expect(getByRole('textbox', {name: /name/i}).value).toBe('Example');

  expect(getByRole('button', {name: /sendRequest/i})).not.toBeDisabled();
  act(() => {
    fireEvent.submit(getByRole('button', {name: /sendRequest/i}));
  });
  await waitFor(() => {expect(getByRole('textbox', {name: /name/i}).value).toBe("")})
});

test('Cannot make a baby request with name too short', async () => {
  const {getByRole} = render(<Pioneers/>);


  fireEvent.input(getByRole('textbox', {name: /name/i}), {
    target: {value: 'A'}
  });

  await waitFor(() => {expect(getByRole('textbox', {name: /name/i}).value).toBe('A')});

  expect(getByRole('button', {name: /sendRequest/i})).toBeDisabled();
});

test('Cannot make a baby request with trololo name', async () => {
  const {getByRole} = render(<Pioneers/>);


  fireEvent.input(getByRole('textbox', {name: /name/i}), {
    target: {value: 'Tr0l0l0'}
  });

  await waitFor(() => {expect(getByRole('textbox', {name: /name/i}).value).toBe('Tr0l0l0')});

  expect(getByRole('button', {name: /sendRequest/i})).toBeDisabled();
});
