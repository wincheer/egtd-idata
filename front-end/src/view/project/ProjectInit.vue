<template>
  <section>
    <el-steps style="margin-top: 20px;">
      <el-step title="新建项目" :status="projectStatus"></el-step>
      <el-step title="维护项目合同" :status="projectContractStatus" description="维护合同的同时选择供应商"></el-step>
      <el-step title="配置项目组" :status="projectGroupStatus" description="当前项目的组织结构以及项目成员"></el-step>
      <el-step title="分解项目阶段" :status="projectStageStatus" description="分解项目阶段及任务，指派责任人以及配置检查链"></el-step>
      <el-step title="项目就绪" :status="projectReadyStatus"></el-step>
    </el-steps>
    <el-button style="margin-top: 12px;" @click="openAddProject" type="primary">新建项目</el-button>
    <el-table border stripe :data="projectList" style="margin-top: 20px;" highlight-current-row @current-change="onProjectChange">
      <el-table-column type="index" width="30"></el-table-column>
      <el-table-column prop="projectName" label="项目名称"></el-table-column>
      <el-table-column prop="createDate" label="立项时间"></el-table-column>
      <el-table-column prop="depId" label="所属部门"></el-table-column>
      <el-table-column prop="createDate" label="启动时间"></el-table-column>
      <el-table-column prop="createDate" label="结束时间"></el-table-column>
      <el-table-column label="操作" width="230">
        <template slot-scope="scope">
          <el-dropdown  trigger="click">
            <el-button size="mini" type="primary">编辑<i class="el-icon-arrow-down el-icon--right"></i></el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="openEditProject(scope.row)">项目</el-dropdown-item>
              <el-dropdown-item @click.native="openProjectContractList(scope.row)">合同</el-dropdown-item>
              <el-dropdown-item @click.native="openEditProjectGroup(scope.row)">项目组</el-dropdown-item>
              <el-dropdown-item @click.native="openEditProjectStage(scope.row)">项目阶段</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button size="mini" type="warning">冻结</el-button>
          <el-button size="mini" type="danger" @click="delProject(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 编辑框 -->
    <el-dialog :title="projectObj.id==''?'增加项目':'编辑项目'" :visible.sync="dlgProjectEditVis" width="40%" :close-on-click-modal="false">
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
        <el-form-item label="项目金额" prop="amount">
          <el-input-number type="text" v-model="projectObj.amount" /> 万元
        </el-form-item>
        <el-row>
          <el-col :span="15">
            <el-form-item label="所属部门" prop="depId">
              <!-- <el-input type="text" placeholder="所属部门" v-model="projectObj.depId"/> -->
              <el-cascader :options="depTreeList" :props="{value:'id'}" v-model="depIds" @change="onDepChange" change-on-select clearable style="width:100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="关键项目" prop="isKey">
              <el-switch v-model="projectObj.isKey" active-text="是" inactive-text="否" :active-value="1" :inactive-value="0"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="立项时间" prop="createDate">
          <el-date-picker type="date" clearable placeholder="立项时间" v-model="projectObj.createDate"/>
        </el-form-item>
        <el-row>
          <el-col :span="10">
            <el-form-item label="启动日期" prop="startDate">
              <el-date-picker type="date" clearable placeholder="启动日期" v-model="projectObj.startDate"/>
            </el-form-item>
          </el-col>
          <el-col :span="10">
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
          <el-table-column label="供应商" prop="vendorId"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" @click="openProjectContractList(scope.row)" >编辑</el-button>
              <el-button size="mini" type="warning" @click="delProjectContract(scope.row)" >删除</el-button>
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
            :limit="1" 
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
    <el-dialog title="配置项目组" :visible.sync="dlgProjectGroupEditVis" width="30%" :close-on-click-modal="false">
      <el-form :model="projectGroupObj" :rules="projectGroupObjRules" ref="projectGroupForm" label-width="80px">
        <el-form-item>
          项目组
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--项目阶段-->
    <el-dialog title="分解项目阶段" :visible.sync="dlgProjectStageEditVis" width="30%" :close-on-click-modal="false">
      <el-form :model="projectStageObj" :rules="projectStageObjRules" ref="projectStageForm" label-width="80px">
        <el-form-item>
          项目阶段
        </el-form-item>
      </el-form>
    </el-dialog>
  </section>
</template>

<script>
import {
  SELECT_DEP_TREE_LIST,
  SELECT_VENDOR_LIST,
  SELECT_PARAM_VALUE_LIST,
  SELECT_PROJECT_LIST,
  UPDATE_PROJECT,
  DELETE_PROJECT,
  SELECT_PROJECT_CONTRACT_LIST,
  UPDATE_PROJECT_CONTRACT,
  UPDATE_PROJECT_CONTRACT_NO_FILE,
  DELETE_PROJECT_CONTRACT,
  SELECT_PROJECT_GROUP_LIST,
  UPDATE_PROJECT_GROUP,
  DELETE_PROJECT_GROUP,
  SELECT_PROJECT_STAGE_LIST,
  UPDATE_PROJECT_STAGE,
  DELETE_PROJECT_STAGE,
  SELECT_DOCUMENT_LIST,
  UPDATE_DOCUMENT,
  DELETE_DOCUMENT
} from "@/config/api";
export default {
  props: {},
  data() {
    return {
      // projectStatus:'',
      // projectContractStatus:'',
      // projectGroupStatus:'',
      // projectStageStatus:'',
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
        depId: "",
        icon: "",
        amount: ""
      },
      projectObjRules: {
        projectName: [
          { required: true, message: "请输入项目名称", trigger: "blur" }
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
          { required: true, message: "请选择合同相关的供应商", trigger: "blur,change" }
        ],
        contractName: [
          { required: true, message: "请输入合同名称", trigger: "blur" }
        ]
      },
      contractFileList: [],
      selectedFile: null,
      projectGroupObj: { id: "" },
      projectGroupObjRules: {},
      projectStageObj: { id: "" },
      projectStageObjRules: {},

      selectedProject: {},

      projectList: [],
      projectContractList: [],
      projectGroupList: [],
      projectStageList: [],

      dlgProjectEditVis: false,
      dlgProjectContractEditVis: false,
      dlgProjectContractListVis: false,
      dlgProjectGroupEditVis: false,
      dlgProjectStageEditVis: false,
      depIds: [],
      depTreeList: [],
      categoryParamList: [],
      vendorList: []
    };
  },
  methods: {
    selectParamValueList(paramKeyObj) {
      var _this = this;
      SELECT_PARAM_VALUE_LIST(paramKeyObj).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取参数列表失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.categoryParamList = res;
        }
      });
    },
    selectVendorList() {
      var _this = this;
      SELECT_VENDOR_LIST().then(res => {
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
        }
      });
    },
    selectProjectGroupList(projectId) {
      var _this = this;
      SELECT_PROJECT_GROUP_LIST({ projectId: projectId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({
            message: "获取项目组失败，请联系系统管理员。",
            type: "error"
          });
        else {
          _this.projectGroupList = res;
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
    openAddProject() {
      this.dlgProjectEditVis = true;
    },
    openEditProject(row) {
      this.projectObj = Object.assign(row);
      this.dlgProjectEditVis = true;
    },
    openProjectContractList(row) {
      this.dlgProjectContractListVis = true;
    },
    openAddProjectContract() {
      this.projectContractObj.id = 0;
      this.projectContractObj.projectId = this.selectedProject.id;
      this.projectContractObj.vendorId = "";
      this.projectContractObj.contractName = "";

      this.dlgProjectContractEditVis = true;
    },
    openEditProjectContract(row) {
      this.projectContractObj = Object.assign(row);

      this.dlgProjectContractEditVis = true;
    },
    openAddProjectGroup() {
      this.dlgProjectGroupEditVis = true;
    },
    openAddProjectStage() {
      this.dlgProjectStageEditVis = true;
    },
    openEditProjectGroup(row) {
      this.projectGroupObj = row;
      this.dlgProjectGroupEditVis = true;
    },
    openEditProjectStage(row) {
      this.projectStageObj = row;
      this.dlgProjectStageEditVis = true;
    },
    updateProject() {
      var _this = this;
      this.$refs.projectForm.validate(valid => {
        if (valid) {
          UPDATE_PROJECT(_this.projectObj).then(data => {
            if (data == "") {
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
    uploadFile(){
      if(this.$refs.upload.uploadFiles.length == 0)
        this.updateProjectContractNoFile();
      else
        this.$refs.upload.submit();
    },
    updateProjectContract(item) {
      var _this = this;
      this.$refs.projectContractForm.validate(valid => {
        if (valid) {
          var formData = new FormData();
          for (var key in _this.projectContractObj) {
              formData.append(key, _this.projectContractObj[key]);
          }
          formData.append("file",item.file); 
          UPDATE_PROJECT_CONTRACT(formData).then(data => {
            if (data !== 0)
              _this.$message({
                message: "更新项目合同失败，请联系系统管理员。",
                type: "error"
              });
            else {
               _this.dlgProjectContractEditVis = false;
              _this.selectProjectContractList(_this.selectedProject.id);
            }
          });
        }
      });
    },
    updateProjectContractNoFile() {
      var _this = this;
      this.$refs.projectContractForm.validate(valid => {
        if (valid) {
          UPDATE_PROJECT_CONTRACT_NO_FILE(_this.projectContractObj).then(data => {
            if (data == '')
              _this.$message({
                message: "更新项目合同失败，请联系系统管理员。",
                type: "error"
              });
            else {
              _this.dlgProjectContractEditVis = false;
              _this.selectProjectContractList(_this.selectedProject.id);
            }
          });
        }
      });
    },
    onFileChange(file, fileList) {
      if (fileList.length != 0) {
        this.projectContractObj.contractName = fileList[0].name;
        this.selectedFile = file;
      } else {
        this.projectContractObj.contractName = "";
        this.selectedFile = null;
      }
    },
    onFileRemove(file, fileList) {
      this.projectContractObj.contractName = "";
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
          });
        });
    },
    onProjectChange(row) {
      this.selectedProject = row;
      //查询相关表的list，用以判断当前项目的完整程度......
      this.selectProjectContractList(row.id);
      this.selectProjectGroupList(row.id);
      this.selectProjectStageList(row.id);
    },
    onDepChange(value) {
      if (value.length != 0) {
        this.projectObj.depId = value[value.length - 1];
      } else this.projectObj.depId = "";
    }
  },
  computed: {
    projectStatus() {
      if (this.selectedProject.id) return "finish";
      else return "wait";
    },
    projectContractStatus() {
      if (this.projectContractList.length != 0) return "finish";
      else return "wait";
    },
    projectGroupStatus() {
      if (this.projectGroupList.length != 0) return "finish";
      else return "wait";
    },
    projectStageStatus() {
      if (this.projectStageList.length != 0) return "finish";
      else return "wait";
    },
    projectReadyStatus() {
      if (
        this.selectedProject.id &&
        this.projectContractList.length != 0 &&
        this.projectGroupList.length != 0 &&
        this.projectStageList.length != 0
      )
        return "success";
      else return "wait";
    }
  },
  mounted() {
    this.selectProjectList();
    this.selectDepTreeList();
    this.selectParamValueList({ paramKey: "category" });
    this.selectVendorList();
  }
};
</script>

<style>

</style>
