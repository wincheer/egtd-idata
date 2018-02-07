<template>
  <section>
    <el-steps style="margin-top: 20px;">
      <el-step title="新建项目" :status="projectStatus"></el-step>
      <el-step title="维护项目合同" :status="projectContractStatus" description="维护合同的同时选择供应商"></el-step>
      <el-step title="配置项目组" :status="projectGroupStatus" description="当前项目的组织结构以及项目成员"></el-step>
      <el-step title="分解项目阶段" :status="projectStageStatus" description="分解项目阶段及任务，指派责任人以及配置检查链"></el-step>
      <el-step title="项目就绪" :status="projectReadyStatus"></el-step>
    </el-steps>

    <el-button style="margin-top: 12px;" @click="openAddProject" type="primary" ref="btnCreateProject">新建项目</el-button>
    <el-dropdown  trigger="click" @command="createProjectFromTpl">
      <el-button type="success">从模板新建项目<i class="el-icon-arrow-down el-icon--right"></i></el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item v-for="item in tplList" :key="item.id" :command="item.id">{{item.tplName}}</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

    <el-table border stripe :data="projectList" style="margin-top: 20px;" highlight-current-row @current-change="onProjectChange" ref="projectTable">
      <el-table-column type="index" width="30"></el-table-column>
      <el-table-column prop="projectName" label="项目名称">
        <template slot-scope="scope">
        <el-button type="text" :disabled="scope.row.ownerId !== $store.state.loginUser.id" @click="openEditProject(scope.row)">{{ scope.row.projectName }}</el-button>
      </template>
      </el-table-column>
      <el-table-column prop="createDate" label="立项时间" :formatter="fmtDate"></el-table-column>
      <el-table-column prop="depId" label="所属部门"  :formatter="fmtDep"></el-table-column>
      <el-table-column prop="actorStaffId" label="项目经理" :formatter="fmtEmp"></el-table-column>
      <el-table-column prop="startDate" label="启动时间" :formatter="fmtDate"></el-table-column>
      <el-table-column prop="endDate" label="结束时间" :formatter="fmtDate"></el-table-column>
      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <el-dropdown  trigger="click">
            <el-button size="mini" type="primary" :disabled="scope.row.ownerId !== $store.state.loginUser.id">编辑<i class="el-icon-arrow-down el-icon--right"></i></el-button>
            <el-dropdown-menu slot="dropdown">
              <!-- <el-dropdown-item @click.native="openEditProject(scope.row)">项目</el-dropdown-item> -->
              <el-dropdown-item @click.native="openProjectContractList(scope.row)">合同</el-dropdown-item>
              <el-dropdown-item @click.native="openProjectGroupList(scope.row)">项目组</el-dropdown-item>
              <el-dropdown-item @click.native="openProjectStageList(scope.row)">项目阶段</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button size="mini" type="danger" @click="delProject(scope.row)" :disabled="scope.row.ownerId !== $store.state.loginUser.id">删除</el-button>
          <!-- <el-button size="mini" type="warning" @click="applyAudit(scope.row)" :disabled="(scope.row.ownerId !== $store.state.loginUser.id) || scope.row.auditState==='wait' || scope.row.auditState==='ready'">申请审批</el-button> -->
          <el-button size="mini" type="warning" @click="applyAudit(scope.row)" v-if="projectReadyStatus === 'success' && (scope.row.ownerId === $store.state.loginUser.id) && scope.row.auditState==='init'">申请审批</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 编辑框 -->
    <el-dialog :title="projectObj.id==''?'新建项目':'编辑项目'" :visible.sync="dlgProjectEditVis" width="40%" :close-on-click-modal="false">
      <el-form :model="projectObj" :rules="projectObjRules" ref="projectForm" label-width="80px">
        <el-row>
          <el-col :span="15">
            <el-form-item label="项目名称" prop="projectName">
              <el-input type="text" v-model="projectObj.projectName" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="类别" prop="category">
              <el-select v-model="projectObj.category" placeholder="请选择">
                <el-option v-for="item in categoryParamList" :key="item.id" :label="item.paramDesc" :value="item.paramValue" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="项目描述" prop="projectDesc">
          <el-input type="textarea" v-model="projectObj.projectDesc"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目金额" prop="amount">
              <el-input-number type="text" v-model="projectObj.amount" /> 万元
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="关键项目" prop="isKey">
              <el-switch v-model="projectObj.isKey" active-text="是" inactive-text="否" :active-value="1" :inactive-value="0"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目经理" prop="actorStaffId">
              <el-select v-model="projectObj.actorStaffId" placeholder="请选择甲方项目经理">
                <el-option v-for="item in allDepEmpList" :key="item.id" :label="item.empName" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属部门" prop="depId">
              <el-cascader :options="depTreeList" :props="{value:'id'}" v-model="depIds" @change="onDepChange" change-on-select clearable style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="立项时间" prop="createDate">
          <el-date-picker type="date" clearable placeholder="立项时间" v-model="projectObj.createDate"/>
        </el-form-item>
        <el-row >
          <el-col :span="12">
            <el-form-item label="启动日期" prop="startDate">
              <el-date-picker type="date" clearable placeholder="启动日期" v-model="projectObj.startDate"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束日期" prop="endDate">
              <el-date-picker type="date" clearable placeholder="结束日期" v-model="projectObj.endDate"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="updateProject">保存</el-button>
          <el-button @click="dlgProjectEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--合同-->
    <!--dlgXXlist-->
    <el-dialog title="维护项目合同" :visible.sync="dlgProjectContractListVis" width="40%" :close-on-click-modal="false">
       <el-card>
        <div slot="header" class="clearfix">
          <span>项目合同</span>
          <el-button @click="openAddProjectContract" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加合同</el-button>
        </div>
        <el-table :data="projectContractList" highlight-current-row >
          <el-table-column label="合同" prop="contractName"></el-table-column>
          <el-table-column label="供应商" prop="vendorId" :formatter="fmtVendor"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" @click="openEditProjectContract(scope.row)" >编辑</el-button>
              <el-button size="mini" type="danger" @click="delProjectContract(scope.row)" >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-dialog>
    <!--dlgXXEdit-->
    <el-dialog :title="projectContractObj.id==''?'增加项目合同':'编辑项目合同'" :visible.sync="dlgProjectContractEditVis" width="25%" :close-on-click-modal="false">
      <el-form :model="projectContractObj" :rules="projectContractObjRules" ref="projectContractForm" label-width="100px">
        <el-form-item label="合同名称" prop="contractName">
          <el-input type="text" v-model="projectContractObj.contractName"></el-input>
        </el-form-item>
        <el-form-item label="相关供应商" prop="vendorId">
          <el-select v-model="projectContractObj.vendorId" placeholder="请选择">
            <el-option v-for="item in vendorList" :key="item.id" :label="item.vendorName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="附件" >
          <el-upload 
            ref="upload" 
            action="any"
            :http-request="updateProjectContract"
            :on-change="onFileChange"
            :on-remove="onFileRemove"
            :auto-upload="false" 
            :file-list="contractFileList">
            <el-button slot="trigger" size="mini">选取合同 ...</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="uploadFile">保存</el-button>
          <el-button @click="dlgProjectContractEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--项目组-->
    <el-dialog title="配置项目组" :visible.sync="dlgProjectGroupListVis" width="40%" :close-on-click-modal="false">
      <el-row :gutter="10">
        <el-col :span="12">
          <el-card>
            <div slot="header" class="clearfix">
              <span>项目组</span>
              <el-button @click="openAddProjectGroup" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加项目组</el-button>
            </div>
            <el-tree :data="projectGroupTreeList" @node-click="onProjectGroupChange" :render-content="renderContent" highlight-current :expand-on-click-node="false" default-expand-all></el-tree>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card>
            <div slot="header" class="clearfix">
              <span>项目组成员</span>
              <el-button @click="openAddProjectStaff" :disabled="projectGroupObj.id == ''" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">维护项目组成员</el-button>
            </div>
            <el-table :data="groupStaffList">
              <el-table-column label="姓名" prop="empName"></el-table-column>
              <el-table-column label="电话" prop="empMobile"></el-table-column>
            </el-table>
          </el-card>
        </el-col>
      </el-row>
    </el-dialog>
    <el-dialog :title="projectGroupObj.id==''?'增加项目组':'编辑项目组'" :visible.sync="dlgProjectGroupEditVis" width="25%" :close-on-click-modal="false">
      <el-form :model="projectGroupObj" :rules="projectGroupObjRules" ref="projectGroupForm" label-width="100px">
        <el-form-item label="上级项目组" prop="parentId">
          <el-cascader :options="projectGroupTreeList" :props="{value:'id'}" v-model="parentGroupIds" @change="onParentGroupChange" change-on-select clearable style="width:100%" />
        </el-form-item>
        <el-form-item label="项目组名称" prop="groupName">
          <el-input type="text" v-model="projectGroupObj.groupName"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="groupRole">
          <el-select v-model="projectGroupObj.groupRole" placeholder="请选择">
            <el-option v-for="item in builtinRoleParamList" :key="item.id" :label="item.paramDesc" :value="item.paramValue" />
          </el-select>
        </el-form-item>
        <el-form-item label="项目组描述" prop="vendorId">
           <el-input type="text" v-model="projectGroupObj.groupDesc"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateProjectGroup">保存</el-button>
          <el-button @click="dlgProjectGroupEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="维护项目组成员" :visible.sync="dlgProjectStaffEditVis" width="20%" :close-on-click-modal="false">
      <el-form :model="projectStaffObj" ref="projectStaffObjForm" label-width="80px">
        <el-form-item label="机构员工">
            <el-select v-model="selectedProjectStaffs" value-key="id" filterable multiple clearable placeholder="请选择">
              <el-option-group v-for="group in availableEmployeeGroup" :key="group.branch" :label="group.branch">
                <el-option v-for="item in group.employeeList" :key="item.id" :label="item.empName" :value="item"></el-option>
              </el-option-group>
            </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateProjectStaffs">保存</el-button>
          <el-button @click="dlgProjectStaffEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--项目阶段-->
    <el-dialog title="分解项目阶段" :visible.sync="dlgProjectStageListVis" width="35%" :close-on-click-modal="false">
      <el-card>
        <div slot="header" class="clearfix">
          <span>项目阶段</span>
          <el-button @click="openAddProjectStage" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加项目阶段</el-button>
        </div>
        <el-table :data="projectStageList" highlight-current-row >
          <el-table-column label="阶段" prop="text"></el-table-column>
          <el-table-column label="负责人" prop="actorStaffId" :formatter="fmtEmp"></el-table-column>
          <el-table-column label="开始日期" prop="start_date" :formatter="fmtDate"></el-table-column>
          <el-table-column label="结束日期" prop="end_date" :formatter="fmtDate"></el-table-column>
          <el-table-column label="操作" width="160">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" @click="openEditProjectStage(scope.row)" >编辑</el-button>
              <el-button size="mini" type="danger" @click="delProjectStage(scope.row)" >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-dialog>
    <el-dialog :title="projectStageObj.id==''?'增加项目阶段':'编辑项目阶段'" :visible.sync="dlgProjectStageEditVis" width="23%" :close-on-click-modal="false">
      <el-form :model="projectStageObj" :rules="projectStageObjRules" ref="projectStageForm" label-width="100px">
        <el-form-item label="阶段名称" prop="text">
          <el-input type="text" v-model="projectStageObj.text" />
        </el-form-item>
        <el-form-item label="启动日期" prop="start_date">
          <el-date-picker type="date" clearable placeholder="启动日期" v-model="projectStageObj.start_date"/>
        </el-form-item>
        <el-form-item label="结束日期" prop="end_date">
          <el-date-picker type="date" clearable placeholder="结束日期" v-model="projectStageObj.end_date"/>
        </el-form-item>
        <el-form-item label="负责人" prop="category">
          <el-select v-model="projectStageObj.actorStaffId" placeholder="请选择" clearable>
            <el-option v-for="item in ownerProjectEmpList" :key="item.id" :label="item.empName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateProjectStage">保存</el-button>
          <el-button @click="dlgProjectStageEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </section>
</template>

<script>
import {
  SELECT_ALL_DEP_EMP_LIST,
  SELECT_VENDOR_LIST,
  SELECT_VENDOR_EMP_LIST,
  SELECT_PARAM_VALUE_LIST,
  SELECT_PROJECT_LIST,
  UPDATE_PROJECT,
  DELETE_PROJECT,
  SELECT_PROJECT_CONTRACT_LIST,
  UPDATE_PROJECT_CONTRACT,
  UPDATE_PROJECT_CONTRACT_NO_FILE,
  DELETE_PROJECT_CONTRACT,
  SELECT_PROJECT_GROUP_TREE_LIST,
  UPDATE_PROJECT_GROUP,
  DELETE_PROJECT_GROUP,
  SELECT_PROJECT_STAGE_LIST,
  UPDATE_PROJECT_STAGE,
  DELETE_PROJECT_STAGE,
  SELECT_DOCUMENT_LIST,
  UPDATE_DOCUMENT,
  DELETE_DOCUMENT,
  SELECT_GROUP_EMP_LIST,
  SELECT_AVAILABLE_GROUP_PROJECT_EMPLOYEE_LIST,
  UPDATE_PROJECT_STAFFS,
  SELECT_PROJECT_EMPLOYEE_LIST,
  SELECT_OWNER_PROJECT_EMPLOYEE_LIST,
  SELECT_DEP_TREE_LIST,
  UPDATE_MESSAGE,
  SELECT_TPL_PROJECT_LIST,
  CREATE_PROJECT_FROM_TPL
} from "@/config/api";
import { formatDate } from "@/util/date.js";
import { getNodePath, getNode } from "@/util/treeUtil.js";
export default {
  props: {},
  data() {
    return {
      // projectStatus:'',
      // projectContractStatus:'',
      // projectGroupStatus:'',
      // projectStageStatus:'',
      allDepEmpList: [],
      depTreeList: [],
      depIds: [],
      projectObj: {
        id: "",
        projectName: "",
        projectDesc: "",
        category: "",
        isKey: 0,
        isForzen: 0,
        createDate: "",
        startDate: "",
        endDate: "",
        actorStaffId: "",
        icon: "",
        amount: "",
        ownerId:""
      },
      projectObjRules: {
        projectName: [
          { required: true, message: "请输入项目名称", trigger: "blur" }
        ],
        actorStaffId: [
          { required: true, message: "请选择项目经理", trigger: "blur" }
        ],
        startDate: [
          { required: true, message: "请输入项目开始时间", trigger: "blur" }
        ],
        endDate: [
          { required: true, message: "请输入项目结束时间", trigger: "blur" }
        ]
      },
      projectContractObj: {
        id: 0,
        projectId: "",
        vendorId: "",
        contractName: ""
      },
      projectContractObjRules: {
        vendorId: [
          {
            required: true,
            message: "请选择合同相关的供应商",
            trigger: "blur,change"
          }
        ],
        contractName: [
          { required: true, message: "请输入合同名称", trigger: "blur" }
        ]
      },
      contractFileList: [],
      selectedFile: null,
      projectGroupObj: {
        id: "",
        projectId: "",
        groupName: "",
        groupRole: "",
        groupDesc: "",
        parentId: ""
      },
      projectGroupObjRules: {
        groupName: [
          { required: true, message: "请输入项目组名称", trigger: "blur" }
        ]
      },
      parentGroupIds: [],
      projectStaffObj: {
        id: "",
        isVendor: 0,
        groupId: "",
        empId: "",
        staffName: "",
        staffEmail: "",
        staffMobile: "",
        password: ""
      },
      availableEmployeeGroup: [],
      selectedProjectStaffs: [],

      projectStageObj: {
        id: "",
        projectId: "",
        text: "",
        start_date: "",
        end_date: "",
        actorStaffId: ""
      },
      projectStageObjRules: {
        text: [
          { required: true, message: "请输入项目阶段名称", trigger: "blur" }
        ],
        start_date: [
          { required: true, message: "请输入启动时间", trigger: "blur" }
        ],
        end_date: [
          { required: true, message: "请输入结束时间", trigger: "blur" }
        ]
      },

      selectedProject: {},

      projectList: [],
      projectContractList: [],
      projectGroupTreeList: [],
      projectStaffList: [],
      groupStaffList: [],
      projectStageList: [],

      dlgProjectEditVis: false,
      dlgProjectContractEditVis: false,
      dlgProjectContractListVis: false,
      dlgProjectGroupListVis: false,
      dlgProjectGroupEditVis: false,
      dlgProjectStaffEditVis: false,
      dlgProjectStageListVis: false,
      dlgProjectStageEditVis: false,
      categoryParamList: [],
      builtinRoleParamList: [],
      vendorList: [],
      projectVendorList: [],
      ownerProjectEmpList: [],
      tplList: []
    };
  },
  methods: {
    selectDepTreeList() {
      var _this = this;
      SELECT_DEP_TREE_LIST().then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取组织结构失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.depTreeList = res;
        }
      });
    },
    onDepChange(value) {
      if (value.length != 0) {
        this.projectObj.depId = value[value.length - 1];
      } else this.depObj.parentId = 0;
    },
    selectAllDepEmpList() {
      var _this = this;
      SELECT_ALL_DEP_EMP_LIST({}).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取业主员工列表失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.allDepEmpList = res;
        }
      });
    },
    selectParamValueList(paramKeyObj) {
      var _this = this;
      SELECT_PARAM_VALUE_LIST(paramKeyObj).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取参数列表失败，请联系系统管理员。",
            type: "error"
          });
        else {
          if (paramKeyObj.paramKey == "category") _this.categoryParamList = res;
          else if (paramKeyObj.paramKey == "built-in-role")
            _this.builtinRoleParamList = res;
        }
      });
    },
    selectVendorList(projectId) {
      var _this = this;
      SELECT_VENDOR_LIST({ projectId: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取供应商失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.vendorList = res;
        }
      });
    },
    selectTplProjectList() {
      var _this = this;
      SELECT_TPL_PROJECT_LIST().then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目模板失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.tplList = res;
        }
      });
    },
    selectProjectList() {
      var _this = this;
      SELECT_PROJECT_LIST().then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectList = res;
        }
      });
    },
    selectProjectContractList(projectId) {
      var _this = this;
      SELECT_PROJECT_CONTRACT_LIST({ projectId: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目合同失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectContractList = res;
          _this.projectVendorList.length = 0;
          for (var i = 0; i < _this.vendorList.length; i++) {
            var _vendor = _this.vendorList[i];
            for (var j = 0; j < _this.projectContractList.length; j++) {
              var _contract = _this.projectContractList[j];
              if (_vendor.id == _contract.vendorId) {
                if (_this.projectVendorList.indexOf(_vendor) == -1)
                  _this.projectVendorList.push(_vendor);
              } else break;
            }
          }
        }
      });
    },
    selectProjectGroupTreeList(projectId) {
      var _this = this;
      SELECT_PROJECT_GROUP_TREE_LIST({ projectId: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目组失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectGroupTreeList = res;
        }
      });
    },
    selectProjectStageList(projectId) {
      var _this = this;
      SELECT_PROJECT_STAGE_LIST({ projectId: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目阶段失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectStageList = res;
        }
      });
    },
    selectDocumentList(projectContractId) {
      var _this = this;
      SELECT_DOCUMENT_LIST({
        belongTo: "contract",
        sourceId: projectContractId
      }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目文档文件失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.contractFileList = res;
        }
      });
    },
    selectGroupStaffList(projectGroupId) {
      var _this = this;
      SELECT_GROUP_EMP_LIST({ groupId: projectGroupId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目组成员失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.groupStaffList = res;
          _this.selectedProjectStaffs = res;
        }
      });
    },
    selectProjectStaffList(projectId) {
      var _this = this;
      SELECT_PROJECT_EMPLOYEE_LIST({ id: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目组成员失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectStaffList = res;
        }
      });
    },
    selectOwnerProjectEmpList(projectId) {
      var _this = this;
      SELECT_OWNER_PROJECT_EMPLOYEE_LIST({ id: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取甲方项目组成员失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.ownerProjectEmpList = res;
        }
      });
    },
    openAddProject() {
      let btn = this.$refs.btnCreateProject;
      this.dlgProjectEditVis = true;
    },
    openEditProject(row) {
      this.projectObj = Object.assign(row);
      this.depIds = getNodePath(this.depTreeList, row.depId); //级联选项的数据
      this.dlgProjectEditVis = true;
    },

    openProjectContractList(row) {
      this.dlgProjectContractListVis = true;
    },
    openAddProjectContract() {
      this.projectContractObj = Object.assign({
        id: 0,
        projectId: this.selectedProject.id,
        vendorId: "",
        contractName: ""
      });
      this.contractFileList = [];

      this.dlgProjectContractEditVis = true;
    },
    openEditProjectContract(row) {
      this.projectContractObj = Object.assign(row);
      //查询当前合同的文档
      this.selectDocumentList(row.id);
      this.dlgProjectContractEditVis = true;
    },
    openAddProjectGroup() {
      this.projectGroupObj = Object.assign({
        id: "",
        projectId: this.selectedProject.id,
        groupName: "",
        groupRole: "",
        groupDesc: "",
        parentId: 0
      });
      this.parentGroupIds = [];
      this.dlgProjectGroupEditVis = true;
    },
    openEditProjectGroup(node, data) {
      this.projectGroupObj = {
        id: data.id,
        projectId: this.selectedProject.id,
        groupName: data.label,
        groupRole: data.data.groupRole,
        groupDesc: data.data.groupDesc,
        parentId: data.parentId
      };
      this.parentGroupIds = getNodePath(
        this.projectGroupTreeList,
        data.parentId
      );
      this.dlgProjectGroupEditVis = true;
    },
    openAddProjectStaff() {
      this.dlgProjectStaffEditVis = true;
    },
    openEditProjectStage(row) {
      this.projectStageObj = Object.assign(row);
      this.selectOwnerProjectEmpList(this.selectedProject.id);
      this.dlgProjectStageEditVis = true;
    },
    openAddProjectStage() {
      this.projectStageObj = Object.assign({
        id: "",
        projectId: this.selectedProject.id,
        text: "",
        start_date: "",
        end_date: "",
        actorStaffId: ""
      });
      this.selectOwnerProjectEmpList(this.selectedProject.id);
      this.dlgProjectStageEditVis = true;
    },
    openProjectGroupList(row) {
      //this.projectGroupObj = row;
      this.dlgProjectGroupListVis = true;
    },
    openProjectStageList(row) {
      //this.projectStageObj = row;
      this.dlgProjectStageListVis = true;
    },
    updateProject() {
      var _this = this;
      this.$refs.projectForm.validate(valid => {
        if (valid) {
          _this.projectObj.ownerId = _this.$store.state.loginUser.id
          UPDATE_PROJECT(_this.projectObj).then(data => {
            if (data === "") {
              _this.$message({
                message: "更新项目失败，请联系系统管理员。",
                type: "error"
              });
            } else {
              _this.dlgProjectEditVis = false;
              _this.selectProjectList();
              _this.projectObj.id = data;
              _this.selectedProject = _this.projectObj;
            }
          });
        }
      });
    },
    /**上传文件，同时包含表单内容 */
    uploadFile() {
      var _uploadFiles = this.$refs.upload.uploadFiles;
      if (_uploadFiles.length == 0) this.updateProjectContractNoFile();
      else {
        for (var i = 0; i < _uploadFiles.length; i++) {
          var _uploadFile = _uploadFiles[i];
          if (_uploadFile.status != "success") {
            this.$refs.upload.submit();
            return;
          }
        }
        this.updateProjectContractNoFile();
      }
    },
    updateProjectContract(item) {
      var _this = this;
      this.$refs.projectContractForm.validate(valid => {
        if (valid) {
          var formData = new FormData();
          for (var key in _this.projectContractObj) {
            formData.append(key, _this.projectContractObj[key]);
          }
          formData.append("file", item.file);
          UPDATE_PROJECT_CONTRACT(formData).then(data => {
            if (data !== 0)
              _this.$message({
                message: "更新项目合同失败，请联系系统管理员。",
                type: "error"
              });
            else {
              _this.dlgProjectContractEditVis = false;
              _this.selectProjectContractList(_this.selectedProject.id);
              _this.selectProjectStaffList(_this.selectedProject.id);
            }
          });
        }
      });
    },
    updateProjectContractNoFile() {
      var _this = this;
      this.$refs.projectContractForm.validate(valid => {
        if (valid) {
          UPDATE_PROJECT_CONTRACT_NO_FILE(_this.projectContractObj).then(
            data => {
              if (data == "")
                _this.$message({
                  message: "更新项目合同失败，请联系系统管理员。",
                  type: "error"
                });
              else {
                _this.dlgProjectContractEditVis = false;
                _this.selectProjectContractList(_this.selectedProject.id);
                _this.selectProjectStaffList(_this.selectedProject.id);
              }
            }
          );
        }
      });
    },
    updateProjectGroup() {
      var _this = this;
      this.$refs.projectGroupForm.validate(valid => {
        if (valid) {
          UPDATE_PROJECT_GROUP(_this.projectGroupObj).then(data => {
            if (data == "") {
              _this.$message({
                message: "更新项目组失败，请联系系统管理员。",
                type: "error"
              });
            } else {
              _this.dlgProjectGroupEditVis = false;
              _this.selectProjectGroupTreeList(_this.selectedProject.id);
              _this.projectGroupObj.id = data;
            }
          });
        }
      });
    },
    updateProjectStage() {
      var _this = this;
      this.$refs.projectStageForm.validate(valid => {
        if (valid) {
          UPDATE_PROJECT_STAGE(_this.projectStageObj).then(data => {
            if (data === "") {
              _this.$message({
                message: "更新项目阶段失败，请联系系统管理员。",
                type: "error"
              });
            } else {
              _this.dlgProjectStageEditVis = false;
              _this.selectProjectStageList(_this.selectedProject.id);
              _this.projectStageObj.id = data;
            }
          });
        }
      });
    },
    updateProjectStaffs() {
      var _this = this;
      for (var i = 0; i < this.selectedProjectStaffs.length; i++) {
        this.selectedProjectStaffs[i].groupId = this.projectGroupObj.id;
      }
      var params = {
        groupId: this.projectGroupObj.id,
        staffList: this.selectedProjectStaffs
      };
      UPDATE_PROJECT_STAFFS(params).then(data => {
        if (data === "") {
          _this.$message({
            message: "更新项目组成员失败，请联系系统管理员。",
            type: "error"
          });
        } else {
          _this.selectGroupStaffList(_this.projectGroupObj.id);
          _this.dlgProjectStaffEditVis = false;
        }
      });
    },
    onFileChange(file, fileList) {
      if (fileList.length != 0) {
        //this.projectContractObj.contractName = fileList[0].name;
        this.selectedFile = file;
      } else {
        //this.projectContractObj.contractName = "";
        this.selectedFile = null;
      }
    },
    onFileRemove(file, fileList) {
      var _this = this;
      if (file.status == "success") {
        DELETE_DOCUMENT({ id: file.id }).then(res => {
          _this.$message({ message: "删除成功", type: "success" });
          _this.selectDocumentList(file.sourceId);
        });
      }
      this.selectedFile = null;
    },
    delProject(row) {
      var _this = this;
      _this
        .$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        })
        .then(() => {
          DELETE_PROJECT({ id: row.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectProjectList();
          });
        });
    },
    delProjectContract(row) {
      var _this = this;
      _this
        .$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        })
        .then(() => {
          DELETE_PROJECT_CONTRACT({ id: row.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectProjectContractList(row.projectId);
            _this.selectProjectStaffList(row.id);
          });
        });
    },

    delProjectGroup(node, data) {
      var _this = this;
      _this
        .$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        })
        .then(() => {
          DELETE_PROJECT_GROUP({ id: data.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectProjectGroupTreeList(_this.selectedProject.id);
          });
        });
    },
    delProjectStage(row) {
      var _this = this;
      _this
        .$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        })
        .then(() => {
          DELETE_PROJECT_STAGE({ id: row.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectProjectStageList(row.projectId);
          });
        });
    },
    onProjectChange(row) {
      if (row) {
        this.selectedProject = row;
        //查询相关表的list，用以判断当前项目的完整程度......
        this.selectProjectContractList(row.id);
        this.selectProjectGroupTreeList(row.id);
        this.selectProjectStageList(row.id);
        //填充项目相关的供应商
        this.selectEmployeeGroup(row.id);
        this.selectProjectStaffList(row.id);
      } else this.selectedProject = {};

      this.$refs.projectTable.setCurrentRow(row);
    },
    selectEmployeeGroup(projectId) {
      var _this = this;
      SELECT_AVAILABLE_GROUP_PROJECT_EMPLOYEE_LIST({ id: projectId }).then(
        res => {
          _this.availableEmployeeGroup = res;
        }
      );
    },
    onProjectGroupChange(data) {
      // 查询当前项目组的员工
      this.selectGroupStaffList(data.id);
      //填充
      this.projectGroupObj.id = data.id;
    },
    onParentGroupChange(value) {
      if (value.length != 0) {
        this.projectGroupObj.parentId = value[value.length - 1];
      } else this.depObj.parentId = 0;
    },
    renderContent(h, { node, data, store }) {
      return (
        <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
          <span>
            <span>{node.label}</span>
          </span>
          <span>
            <el-button
              style="font-size: 12px;"
              type="text"
              on-click={() => this.openEditProjectGroup(node, data)}
            >
              编辑
            </el-button>
            <el-button
              style="font-size: 12px;"
              type="text"
              on-click={() => this.delProjectGroup(node, data)}
            >
              删除
            </el-button>
          </span>
        </span>
      );
    },
    fmtDate(row, column, cellValue) {
      if (cellValue == null) return "";
      else return formatDate(new Date(cellValue), "yyyy-MM-dd");
    },
    fmtVendor(row, column, cellValue) {
      for (var i = 0; i < this.vendorList.length; i++) {
        if (this.vendorList[i].id === cellValue) {
          return this.vendorList[i].vendorName;
        }
      }
    },
    fmtEmp(row, column, cellValue) {
      for (var i = 0; i < this.allDepEmpList.length; i++) {
        if (this.allDepEmpList[i].id == cellValue)
          return this.allDepEmpList[i].empName;
      }
      return cellValue;
    },
    fmtDep(row, column, cellValue) {
      var node = getNode(this.depTreeList, cellValue);
      if (node) return node.label;
    },
    authCheck(conmponentName) {
      const authList = [{ name: "btnCreateProject", whiteList: ["R00"] }];

      var conmponent = this.$refs[conmponentName];
      var whiteList = [];
      for (var auth of authList) {
        if (auth.name === conmponentName) {
          whiteList = auth.whiteList;
          break;
        }
      }
      var denied = true;
      var myRoles = this.$store.state.myRoles;
      //任意一个角色在白名单就不拒绝
      for (var i = 0; i < myRoles.length; i++) {
        var myRole = myRoles[i];
        if (whiteList.indexOf(myRole.group_role) != -1) {
          denied = false;
          break;
        }
      }
      //return denied;
      conmponent.disabled = denied;
    },
    applyAudit(row) {
      var _this = this;
      var params = {
        from: this.$store.state.loginUser.id,
        to: 0, //后台确定
        toScope: "actor",
        title: "项目计划审批",
        body:
          this.$store.state.loginUser.empName +
          "已完成项目 【" +
          row.projectName +
          "】的项目计划，请您进行审批",
        type: "audit",
        relationId: row.id
      };
      UPDATE_MESSAGE(params).then(data => {
        if (data === "") {
          _this.$message({
            message: "申请审批失败，请联系系统管理员。",
            type: "error"
          });
        } else {
          _this.$notify({
            title: "发送成功",
            message: "您的申请已发出，请等待审批结果。",
            type: "success"
          });
          _this.selectProjectList();
        }
      });
    },
    createProjectFromTpl(command) {
      var _this = this;
      this.$prompt("请输入项目名称", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(({ value }) => {
        var params = { tplId: command, projectName: value,empId:_this.$store.state.loginUser.id };
        CREATE_PROJECT_FROM_TPL(params).then(res => {
          _this.$notify({title: '创建项目成功', message: '项目根据模板创建成功，请自行维护项目属性和组成员',type: "success"});
          _this.selectProjectList();
        });
      });
    }
  },
  computed: {
    projectStatus() {
      if (this.selectedProject.id) return "finish";
      else return "wait";
    },
    projectContractStatus() {
      if (this.selectedProject.id && this.projectContractList.length != 0)
        return "finish";
      else return "wait";
    },
    projectGroupStatus() {
      if (this.selectedProject.id && this.projectGroupTreeList.length != 0)
        return "finish";
      else return "wait";
    },
    projectStageStatus() {
      if (this.selectedProject.id && this.projectStageList.length != 0)
        return "finish";
      else return "wait";
    },
    projectReadyStatus() {
      if (
        this.selectedProject.id &&
        this.projectContractList.length != 0 &&
        this.projectGroupTreeList.length != 0 &&
        this.projectStageList.length != 0
      )
        return "success";
      else return "wait";
    }
  },
  mounted() {
    this.selectProjectList();
    this.selectParamValueList({ paramKey: "category" });
    this.selectParamValueList({ paramKey: "built-in-role" });
    this.selectVendorList();
    this.selectAllDepEmpList(); //业主的所有员工
    this.selectDepTreeList(); //业主的所有部门
    this.selectTplProjectList(); // 项目模板

    //this.authCheck("btnCreateProject"); //检查组件权限

    console.log("发布 my-event事件");
    this.$bus.$emit('my-event', { msg:"Hello,From Project Init" }) // 消息发布
  }
};
</script>

<style>

</style>
