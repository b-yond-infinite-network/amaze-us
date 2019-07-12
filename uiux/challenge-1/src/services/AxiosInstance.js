import axios from 'axios';

// Would generally have this in a .env file.
// const apiKey = '064b9c9b7cfedab8d404802c855976cc';
const baseURl = 'https://cors-anywhere.herokuapp.com/http://api.musixmatch.com/ws/1.1/';
// Gotta find a better way to deal with the whole CORS thing.
// Worst case -- Setup a lightweight express server, make the call there and then have that endpoint return things to you.

const AxiosInstance = axios.create({
  baseURL: baseURl, 
  headers: { 
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'text/plain',
  }
})

export { AxiosInstance }