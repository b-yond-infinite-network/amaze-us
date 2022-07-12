import axios from "axios";

// return the user data from the session storage

export const getUser = () => {
  const userStr = sessionStorage.getItem("user");
  if (userStr) return JSON.parse(userStr);
  else return null;
};

// return the token from the session storage
export const getToken = () => {
  return sessionStorage.getItem("token") || null;
};

// remove the token and user from the session storage
export const removeUserSession = () => {
  sessionStorage.removeItem("token");
  sessionStorage.removeItem("user");
};

// set the token and user from the session storage
export const setUserSession = (token, user) => {
  sessionStorage.setItem("token", token);
  sessionStorage.setItem("user", JSON.stringify(user));
};



export const getHTTP = (url, params = {}, headers = null) => {


 
  if (!headers) {
    headers = {
      "Content-type": "application/json",
      // 'content-type': 'application/x-www-form-urlencoded',
      "Access-Control-Allow-Origin": "*",
      Accept: "*",
    };

    var token = getToken();
    if (token) headers["Authorization"] = token;
  }
  return axios.get(process.env.REACT_APP_SERVER_URL??window.$baseURL  + url, {
    headers: headers,
    params: params,
  });

  
};



export const postHTTP = (url, body = {}, headers = null) => {


 
  if (!headers) {
    headers = {
      "Content-type": "application/json",
      // 'content-type': 'application/x-www-form-urlencoded',
      "Access-Control-Allow-Origin": "*",
      Accept: "*",
    };

    var token = getToken();
    if (token) headers["Authorization"] = token;
  }
  return axios.post(process.env.REACT_APP_SERVER_URL??window.$baseURL  + url,body, {
    headers: headers,
  });

  
};
