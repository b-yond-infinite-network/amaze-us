import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export interface LoadingStates {
  global: boolean;
};

const initialState: LoadingStates = {
  global: false,
};

const loadingSlice = createSlice({
  name: 'loading',
  initialState,
  reducers: {
    startLoading: (state: LoadingStates, action: PayloadAction<keyof LoadingStates>) => {
      return { ...state, [action.payload]: true };
    },
    stopLoading: (state, action: PayloadAction<keyof LoadingStates>) => {
      return { ...state, [action.payload]: false };
    }
  }
});

export const { startLoading, stopLoading } = loadingSlice.actions;

export default loadingSlice.reducer;
