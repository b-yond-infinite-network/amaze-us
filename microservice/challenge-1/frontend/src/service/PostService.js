import axios from 'axios'

const API_URL = window.REACT_APP_API_URL

class PostService {

    retrievePosts(username) {
        return axios.get(`${API_URL}/users/${username}/posts`);
    }

    retrievePost(id) {
        return axios.get(`${API_URL}/posts/${id}`);
    }

    deletePost(id) {
        return axios.delete(`${API_URL}/posts/${id}`);
    }

    updatePost(id, post) {
        return axios.put(`${API_URL}/posts/${id}`, post);
    }

    createPost(username, post) {
        return axios.post(`${API_URL}/users/${username}/posts`, post);
    }
}

export default new PostService()