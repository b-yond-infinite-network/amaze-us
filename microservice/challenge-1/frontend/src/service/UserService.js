import axios from 'axios'

const API_URL = window.REACT_APP_API_URL

class UserService {

    retrieveUsers() {
        return axios.get(`${API_URL}/users`);
    }

    retrieveUser(username) {
        return axios.get(`${API_URL}/users/${username}`);
    }

    deleteUser(username) {
        return axios.delete(`${API_URL}/users/${username}`);
    }

    updateUser(username, user) {
        return axios.put(`${API_URL}/users/${username}`, user);
    }

    createUser(user) {
        return axios.post(`${API_URL}/users`, user);
    }
}

export default new UserService()