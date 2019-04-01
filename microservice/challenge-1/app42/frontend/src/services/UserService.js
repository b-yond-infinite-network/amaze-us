import axios from './Api';

const get = () => {
  return new Promise(resolve => {
    axios.get(`users`).then(getResponse => {
      resolve(getResponse.data);
    });
  });
};

const post = user => {
  return new Promise(resolve => {
    axios.post('users', user).then(postResponse => {
      resolve(postResponse.data);
    });
  });
};

export default {
  get,
  post
}
