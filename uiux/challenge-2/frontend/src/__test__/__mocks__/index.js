import configureStore from "redux-mock-store";

export const mockStore = configureStore([]);

export const loggedInStore = mockStore({
  authReducer: {
    isLoggedIn: true,
  }
});

export const loggedOutStore = mockStore({
  authReducer: {
    isLoggedIn: false,
  }
});
