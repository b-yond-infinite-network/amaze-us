import { SET_STEP } from '../actions/userStepsActions';

export default function artistReducer(state={step: "search"}, { type , payload }) {
  switch (type) {
    case SET_STEP:
      return  { ...state, step: payload }
    default:
      break;
  }
  return state;
}