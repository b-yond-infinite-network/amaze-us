import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { ApiValidationError } from '../services/api/models';

const initialState: ApiValidationError[] = [];

const loadingSlice = createSlice({
	name: 'remoteValidationErrors',
	initialState,
	reducers: {
		setRemoteValidationErrors: (_: ApiValidationError[], action: PayloadAction<ApiValidationError[]>) => {
      return action.payload;
		},
	}
});

export const { setRemoteValidationErrors } = loadingSlice.actions;

export default loadingSlice.reducer;
