import axios from 'axios';

export default axios.create({
    baseURL: ' https://cors-anywhere.herokuapp.com/https://api.musixmatch.com/ws/1.1/'
});