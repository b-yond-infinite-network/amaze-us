import React from 'react'
import {rest} from 'msw'
import {setupServer} from 'msw/node'
import renderer from 'react-test-renderer';
import '@testing-library/jest-dom/extend-expect'
import Login from '../Pages/Login';
import {Provider} from 'react-redux';

import {loggedOutStore} from "./__mocks__";


const server = setupServer(
  rest.get('/v1/login', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({token: 'this is the token'}))
  }),
);

let store;
let component;

beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());
afterAll(() => server.close());
beforeEach(() => {
  store = loggedOutStore;
  store.dispatch = jest.fn().mockImplementation(() => Promise.resolve());

  component = renderer.create(
    <Provider store={store}>
      <Login dispatch={store.dispatch}/>
    </Provider>
  );
});

test('should render with given state from Redux store', () => {
  expect(component.toJSON()).toMatchSnapshot();
});

test('should login with username password', () => {
  renderer.act(() => {
    component.root.findByProps({'data-test': 'username'}).props.onChange({target: {value: 'username'}});
    component.root.findByProps({'data-test': 'password'}).props.onChange({target: {value: 'password'}});
  });

  renderer.act(() => {
    component.root.findByType('form').props.onSubmit({preventDefault: jest.fn()});
  });

  expect(store.dispatch).toHaveBeenCalledTimes(1);
});

test('should not login if username missing', () => {
  renderer.act(() => {
    component.root.findByProps({'data-test': 'password'}).props.onChange({target: {value: 'password'}});
  });

  renderer.act(() => {
    component.root.findByType('form').props.onSubmit({preventDefault: jest.fn()});
  });

  expect(store.dispatch).toHaveBeenCalledTimes(0);
});

test('should not login if password missing', () => {
  renderer.act(() => {
    component.root.findByProps({'data-test': 'username'}).props.onChange({target: {value: 'username'}});
  });

  renderer.act(() => {
    component.root.findByType('form').props.onSubmit({preventDefault: jest.fn()});
  });

  expect(store.dispatch).toHaveBeenCalledTimes(0);
});
