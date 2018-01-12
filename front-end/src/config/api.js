import axios from 'axios';
import base from "@/config/remote";

export const LOGIN = (params) => { return axios.post(`${base}/login`, params).then(res => res.data); };

export const SELECT_PARAM_KEY_LIST = () => { return axios.post(`${base}/selectParamKeyList`).then(res => res.data); };
export const UPDATE_PARAM_KEY = (params) => { return axios.post(`${base}/updateParamKey`, params).then(res => res.data); };
export const DEL_PARAM_KEY = (params) => { return axios.post(`${base}/delParamKey`, params).then(res => res.data); };

export const SELECT_PARAM_VALUE_LIST = (params) => { return axios.post(`${base}/selectParamValueList`, params).then(res => res.data); };
export const UPDATE_PARAM_VALUE = (params) => { return axios.post(`${base}/updateParamValue`, params).then(res => res.data); };
export const DEL_PARAM_VALUE = (params) => { return axios.post(`${base}/delParamValue`, params).then(res => res.data); };