import axios from 'axios';
import base from "@/config/remote";

export const requestLogin = (params) => { return axios.post(`${base}/login`, params).then(res => res.data); };
export const queryEmployeeList = () => { return axios.get(`${base}/userList`); };