import axios from "axios";
import {authHeader} from "./AuthService";
import {user} from "../store";

const getProcessedRequest = () => {
  return axios.get('/v1/baby/request/audit', {headers: authHeader()})
};

const getRequest = () => {
  return axios.get('/v1/baby/request', {headers: authHeader()})
};

const postRequest = (name) => {
  return axios.post('/v1/baby/request', {name: name, author: user()}, {headers: authHeader()})
};

const putRequestDecision = (id, decision) => {
  return axios.put('/v1/baby/request/' + encodeURI(id), {status: decision, reviewer: user()}, {headers: authHeader()})
};

const getPopulation = () => {
  return axios.get('/v1/population', {headers: authHeader()})
};

const ColonyService = {
  getPopulation,
  getRequest,
  getProcessedRequest,
  postRequest,
  putRequestDecision
};

export default ColonyService;


