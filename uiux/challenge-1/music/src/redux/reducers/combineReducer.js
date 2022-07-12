import {combineReducers} from 'redux'

import GetTracksReducer from './MyReducers/GetTracksReducer'
import GetTrackReducer from './MyReducers/GetTrackReducer'
import GetTop10TrackReducer from './MyReducers/GetTop10TrackReducer'
import GetLyricsReducer from './MyReducers/GetLyricsReducer'

const combineReducer=combineReducers({
    GetTracksReducer,
    GetTop10TrackReducer,
    GetTrackReducer,
    GetLyricsReducer
})

export default combineReducer