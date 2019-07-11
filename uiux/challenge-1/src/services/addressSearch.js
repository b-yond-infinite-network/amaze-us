import axios from 'axios';

// Would generally have this in a .env file.
const apiKey = '064b9c9b7cfedab8d404802c855976cc';
const baseURl = 'http://api.musixmatch.com/ws/1.1/';
//https://cors-anywhere.herokuapp.com/

const axiosInstance = axios.create({
  baseURL: baseURl,
  headers: { 
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'text/plain',
  }
})

const searchArtist = (artistName) => {
  const searchUrl = `track.search?q_artist=${artistName}&page_size=10&f_has_lyrics=true&apikey=${apiKey}`;
  axiosInstance.get(searchUrl)
    .then(response => {
      const trackList = response.data.message.body;
      return trackList;
    })
    .catch(error => {
      console.log(error.message);
    })
}

export default searchArtist;