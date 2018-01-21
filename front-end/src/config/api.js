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
export const SELECT_DEP_EMP_LIST = (params) => { return axios.post(`${base}/selectDepEmpList`, params).then(res => res.data); };
export const SELECT_ALL_DEP_EMP_LIST = (params) => { return axios.post(`${base}/selectAllDepEmpList`, params).then(res => res.data); };
export const UPDATE_DEP_EMP = (params) => { return axios.post(`${base}/updateDepEmp`, params).then(res => res.data); };
export const DELETE_DEP_EMP = (params) => { return axios.post(`${base}/deleteDepEmp`, params).then(res => res.data); };
export const SELECT_VENDOR_LIST = () => { return axios.post(`${base}/selectVendorList`).then(res => res.data); };
export const UPDATE_VENDOR = (params) => { return axios.post(`${base}/updateVendor`, params).then(res => res.data); };
export const DELETE_VENDOR = (params) => { return axios.post(`${base}/deleteVendor`, params).then(res => res.data); };
export const SELECT_VENDOR_EMP_LIST = (params) => { return axios.post(`${base}/selectVendorEmpList`, params).then(res => res.data); };
export const UPDATE_VENDOR_EMP = (params) => { return axios.post(`${base}/updateVendorEmp`, params).then(res => res.data); };
export const DELETE_VENDOR_EMP = (params) => { return axios.post(`${base}/deleteVendorEmp`, params).then(res => res.data); };

//模板管理
export const SELECT_TPL_PROJECT_LIST = () => { return axios.post(`${base}/selectTplProjectList`).then(res => res.data); };
export const UPDATE_TPL_PROJECT = (params) => { return axios.post(`${base}/updateTplProject`, params).then(res => res.data); };
export const DELETE_TPL_PROJECT = (params) => { return axios.post(`${base}/deleteTplProject`, params).then(res => res.data); };
export const SELECT_TPL_STAGE_TREE_LIST = (params) => { return axios.post(`${base}/selectTplStageTreeList`,params).then(res => res.data); };
export const UPDATE_TPL_STAGE = (params) => { return axios.post(`${base}/updateTplStage`, params).then(res => res.data); };
export const DELETE_TPL_STAGE = (params) => { return axios.post(`${base}/deleteTplStage`, params).then(res => res.data); };
export const SELECT_TPL_GROUP_TREE_LIST = (params) => { return axios.post(`${base}/selectTplGroupTreeList`,params).then(res => res.data); };
export const UPDATE_TPL_GROUP = (params) => { return axios.post(`${base}/updateTplGroup`, params).then(res => res.data); };
export const DELETE_TPL_GROUP = (params) => { return axios.post(`${base}/deleteTplGroup`, params).then(res => res.data); };

//项目维护
export const SELECT_PROJECT_LIST = () => { return axios.post(`${base}/selectProjectList`).then(res => res.data); };
export const SELECT_MY_PROJECT_LIST = (params) => { return axios.post(`${base}/selectMyProjectList`,params).then(res => res.data); };
export const UPDATE_PROJECT = (params) => { return axios.post(`${base}/updateProject`, params).then(res => res.data); };
export const DELETE_PROJECT = (params) => { return axios.post(`${base}/deleteProject`, params).then(res => res.data); };
export const SELECT_PROJECT_CONTRACT_LIST = (params) => { return axios.post(`${base}/selectProjectContractList`,params).then(res => res.data); };
export const UPDATE_PROJECT_CONTRACT = (params) => { return axios.post(`${base}/updateProjectContract`, params).then(res => res.data); };
export const UPDATE_PROJECT_CONTRACT_NO_FILE = (params) => { return axios.post(`${base}/updateProjectContractNoFile`, params).then(res => res.data); };
export const DELETE_PROJECT_CONTRACT = (params) => { return axios.post(`${base}/deleteProjectContract`, params).then(res => res.data); };
export const SELECT_PROJECT_GROUP_TREE_LIST = (params) => { return axios.post(`${base}/selectProjectGroupTreeList`,params).then(res => res.data); };
export const UPDATE_PROJECT_GROUP = (params) => { return axios.post(`${base}/updateProjectGroup`, params).then(res => res.data); };
export const DELETE_PROJECT_GROUP = (params) => { return axios.post(`${base}/deleteProjectGroup`, params).then(res => res.data); };
export const SELECT_PROJECT_STAGE_LIST = (params) => { return axios.post(`${base}/selectProjectStageList`,params).then(res => res.data); };
export const UPDATE_PROJECT_STAGE = (params) => { return axios.post(`${base}/updateProjectStage`, params).then(res => res.data); };
export const DELETE_PROJECT_STAGE = (params) => { return axios.post(`${base}/deleteProjectStage`, params).then(res => res.data); };
export const SELECT_GROUP_STAFF_LIST = (params) => { return axios.post(`${base}/selectGroupStaffList`,params).then(res => res.data); };
export const SELECT_AVAILABLE_PROJECT_STAFF_LIST = (params) => { return axios.post(`${base}/selectAvailableProjectStaffList`,params).then(res => res.data); };
export const SELECT_PROJECT_EMPLOYEE_LIST = (params) => { return axios.post(`${base}/selectProjectEmployeeList`,params).then(res => res.data); };
export const UPDATE_PROJECT_STAFFS = (params) => { return axios.post(`${base}/updateProjectStaffs`,params).then(res => res.data); };

// 项目任务
export const SELECT_PROJECT_TASK_LIST = (params) => { return axios.post(`${base}/selectProjectTaskList`,params).then(res => res.data); };
export const UPDATE_PROJECT_TASKE = (params) => { return axios.post(`${base}/updateProjectTask`, params).then(res => res.data); };
//文档附件
export const SELECT_DOCUMENT_LIST = (params) => { return axios.post(`${base}/selectDocumentList`,params).then(res => res.data); };
export const UPDATE_DOCUMENT = (params) => { return axios.post(`${base}/updateDocument`,params).then(res => res.data); };
export const DELETE_DOCUMENT = (params) => { return axios.post(`${base}/deleteDocument`,params).then(res => res.data); };