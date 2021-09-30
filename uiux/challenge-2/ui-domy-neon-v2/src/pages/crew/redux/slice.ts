import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { BabyMakingRequest } from '../../../models/baby-making-request';
import { User } from '../../../models/user';

interface State {
  members: User[];
  birthRequests: BabyMakingRequest[];
};

const initialState: State = {
	members: [],
  birthRequests: []
};

const crewSlice = createSlice({
	name: 'crew',
	initialState,

	reducers: {
		setMembers: (state, action: PayloadAction<User[]>) => {
      return { ...state, members: action.payload };
    },
    setBirthRequests: (state, action: PayloadAction<BabyMakingRequest[]>) => {
      return { ...state, birthRequests: action.payload };
    },
	},
	extraReducers: {}
});

export const { setMembers, setBirthRequests } = crewSlice.actions;

export default crewSlice.reducer;
