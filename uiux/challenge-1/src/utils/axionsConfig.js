import axios from 'axios';

//Musixmatch.com does not support Access-Control-Allow-Origin 
const corsProxy = "https://cors-anywhere.herokuapp.com/";
const API_KEY = "&apikey=ac03815403318dc10776b6a4af982e89";
const PAGE_SIZE = "&page_size=50";

const baseURL = corsProxy + "http://api.musixmatch.com/ws/1.1";
const headers = {
    'Access-Control-Allow-Origin': '*',
};

const instance = axios.create({
    headers,
    baseURL,
});

instance.interceptors.request.use((request) => {
    request.url += PAGE_SIZE + API_KEY;
    return request;
}, (error) => {
    return Promise.reject(error);
});

export default instance;