export const LOADING_ON = 'LOADING_ON'
export const LOADING_OFF = 'LOADING_OFF'

export function loadingOn() {
    return function (dispatch, getState) {
        dispatch({ type: LOADING_ON })
    }
}

export function loadingOff() {
    return function (dispatch, getState) {
        dispatch({ type: LOADING_OFF })
    }
}
