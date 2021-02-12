import authentication from '../Reducers/authReducer'
import {LOGIN_FAIL, LOGIN_SUCCESS, LOGOUT,} from '../Actions/types';

describe('Auth reducer', () => {
  it('should handle initial state', () => {
    expect(authentication(undefined, {}))
      .toEqual({isLoggedIn: false, token: null, user: null})
  });

  it('should handle login success state', () => {
    expect(authentication(undefined, {type: LOGIN_SUCCESS, payload: {token: 'token', user: 'author'}}))
      .toEqual({isLoggedIn: true, token: 'token', user: 'author'})
  });

  it('should handle login failed state', () => {
    expect(authentication(undefined, {type: LOGIN_FAIL}))
      .toEqual({isLoggedIn: false, token: null, user: null})
  });

  it('should handle logout state', () => {
    expect(authentication({isLoggedIn: true, token: 'token', user: 'author'}, {type: LOGOUT}))
      .toEqual({isLoggedIn: false, token: null, user: null})
  });
});
