import axios from 'axios';
import base from "@/config/remote";

// 登录
export const LOGIN = (params) => { return axios.post(`${base}/login`, params).then(res => res.data); };

// 系统参数配置
export const SELECT_PARAM_KEY_LIST = () => { return axios.post(`${base}/selectParamKeyList`).then(res => res.data); };
export const UPDATE_PARAM_KEY = (params) => { return axios.post(`${base}/updateParamKey`, params).then(res => res.data); };
export const DEL_PARAM_KEY = (params) => { return axios.post(`${base}/delParamKey`, params).then(res => res.data); };
export const SELECT_PARAM_VALUE_LIST = (params) => { return axios.post(`${base}/selectParamValueList`, params).then(res => res.data); };
export const UPDATE_PARAM_VALUE = (params) => { return axios.post(`${base}/updateParamValue`, params).then(res => res.data); };
export const DEL_PARAM_VALUE = (params) => { return axios.post(`${base}/delParamValue`, params).then(res => res.data); };

// 资源管理
export const SELECT_DEP_TREE_LIST = () => { return axios.post(`${base}/selectDepTreeList`).then(res => res.data); };
export const UPDATE_DEP = (params) => { return axios.post(`${base}/updateDep`, params).then(res => res.data); };
export const DELETE_DEP = (params) => { return axios.post(`${base}/deleteDep`, params).then(res => res.data); };