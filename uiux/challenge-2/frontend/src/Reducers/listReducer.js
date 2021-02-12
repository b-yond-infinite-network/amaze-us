import {LOAD_ITEM, REMOVE_ITEM} from "../Actions/types";

export const listReducer = (state, action) => {
  switch (action.type) {
    case REMOVE_ITEM:
      return {
        ...state,
        list: state.list.filter((item) => item.id !== action.id),
      };
    case LOAD_ITEM:
      return {
        ...state,
        list: action.data
      };
    default:
      throw new Error();
  }
};
