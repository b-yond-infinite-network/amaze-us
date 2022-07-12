import axios from "axios";
export function get(url) { 
    const apikey =process.env.REACT_APP_API_KEY;
    const config = {
      headers: {
      }
    };
  
    const fullURL = `${process.env.REACT_APP_PROXY}${process.env.REACT_APP_MUSIC_URL}${url}&apikey=${apikey}`;
  
    return axios.get(fullURL, config);
  }