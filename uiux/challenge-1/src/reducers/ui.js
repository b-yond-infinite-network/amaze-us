import { createAction, createReducer } from 'redux-act'



const initialState = {
  loading: false,
  loadingMessage: null
}

export const showLoading = createAction('SHOW_LOADING')
export const hideLoading = createAction('HIDE_LOADING')

export default createReducer(
  {
    // LOADING
    [showLoading]: (state, payload) => ({
      ...state,
      loadingMessage: payload ? payload.message : null,
      loading: true
    }),
    [hideLoading]: state => ({
      ...state,
      loadingMessage: null,
      loading: false
    }),

  },
  initialState
)
