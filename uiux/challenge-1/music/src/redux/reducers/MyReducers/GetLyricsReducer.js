import * as actionTypes from '../../actions/Types/ActionTypes'
import InitialState from '../InitialState'

export default function GetTrackReducer(state=InitialState,action){
    switch(action.type){
        case actionTypes.GET_LYRİC:
            return{
                ...state.lyrics,
                lyrics:action.payload.lyrics
            }

        default:
            return state
    }
}