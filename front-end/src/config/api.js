import axios from 'axios';
import base from "@/config/remote";

export const LOGIN = (params) => { return axios.post(`${base}/login`, params).then(res => res.data); };