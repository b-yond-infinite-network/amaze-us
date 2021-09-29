import { createSlice, PayloadAction, AnyAction } from '@reduxjs/toolkit';
import { ACCESS_TOKEN_STORAGE_KEY } from '../../utils/constants';
import { User } from '../../models/user';

export interface UserState {
  isAuthenticated?: boolean;
  data?: User;
  token?: string;
};

const initialState: UserState = {
	isAuthenticated: undefined,
};

const userSlice = createSlice({
	name: 'auth/user',
	initialState,

	reducers: {
		setUser: (state, action: PayloadAction<User>) => {
      return { ...state, data: action.payload, isAuthenticated: true };
    },
    setAuthStatus: (state, action: PayloadAction<boolean>) => {
      return { ...state, isAuthenticated: action.payload };
    },
    setUserToken: (state, action: PayloadAction<string>) => {
      return { ...state, token: action.payload };
    },
		userLoggedOut: (_, __: AnyAction) => {
      localStorage.removeItem(ACCESS_TOKEN_STORAGE_KEY);
      return { ...initialState, isAuthenticated: false };
    },
	},
	extraReducers: {}
});

export const { setUser, userLoggedOut, setUserToken, setAuthStatus } = userSlice.actions;

export default userSlice.reducer;
