const requestNative = require('request-promise-native');
const _ = require('lodash');

const apiOptions = (endpoint) => {
    return {
        uri: `${process.env.MUSIXMATCH_API}/${endpoint}`,
        qs: {
            apikey: process.env.MUSIXMATCH_TOKEN,
            q: null,
            page:null,
            page_size:null,
            track_id:null,
            artist_id:null,
            q_artist:null
        },
        json: true
    }
}

const countryApi = (endpoint) => {
    return {
        uri: `${process.env.REST_COUNTRIES}/${endpoint}`,
        json: true
    }
}


export {
    apiOptions,
    requestNative,
    _ ,
    countryApi
}