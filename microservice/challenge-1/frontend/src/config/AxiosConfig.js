import Axios from 'axios'
import { store } from '../store'

const { dispatch } = store
Axios.defaults.baseURL = "http://localhost:8080/api"

Axios.interceptors.request.use((request) => requestHandler(request))

Axios.interceptors.response.use(
    (response) => successHandler(response),
    (error) => errorHandler(error)
)

const requestHandler = (request) => {
    dispatch({ type: 'LOADING_ON' })
    request.headers['Content-Type'] =  'application/json';
    return request
}

const errorHandler = (error) => {
    dispatch({ type: 'LOADING_OFF' })
    let errorMessage = 'Connection error'
    let errorCode = 'internal.error'

    if (error.response) {
        switch (error.response.status.toString()) {
            case '400': {
                errorMessage = "Bad Request"
                errorCode = error.response.code
                break
            }

            case '401': {
                errorMessage = "Unauthorized"
                errorCode = error.response.code
                break
            }

            case '403': {
                errorMessage = "Forbidden"
                errorCode = error.response.code
                break
            }

            case '404': {
                errorMessage = "Not found"
                errorCode = error.response.code
                break
            }

            case '500': {
                errorMessage = "Internal server error"
                errorCode = error.response.code
                break
            }

            default: {
                errorMessage = "Default error"
            }
        }
    }

    return {
        error: {
            code: errorCode,
            errorMessage: errorMessage
        },
        data: undefined
    }
}

const successHandler = (response) => {
    dispatch({ type: 'LOADING_OFF' })

    return {
        data: response.data,
        error: undefined
    }
}
