<template>
  <section>
      <el-dialog title="配置项目组" :visible.sync="isShow" :before-close="closeDialog" :close-on-click-modal="false" width="40%">
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
    
    <el-dialog title="维护项目组成员" :visible.sync="dlgProjectStaffEditVis" width="350px" :close-on-click-modal="false">
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
  </section>
</template>

<script>
import {
  SELECT_PARAM_VALUE_LIST,
  SELECT_PROJECT_GROUP_TREE_LIST,
  UPDATE_PROJECT_GROUP,
  DELETE_PROJECT_GROUP,
  SELECT_PROJECT_STAGE_LIST,
  SELECT_PROJECT_CONTRACT_LIST,
  SELECT_GROUP_EMP_LIST,
  SELECT_AVAILABLE_GROUP_PROJECT_EMPLOYEE_LIST,
  UPDATE_PROJECT_STAFFS
} from "@/config/api";
import { getNodePath, getNode } from "@/util/treeUtil.js";
export default {
  name: "project-group",
  props: {
    isShow: false,
    projectId: ""
  },
  data() {
    return {
      projectGroupObj: {},
      projectGroupObjRules: {
        groupName: [
          { required: true, message: "请输入项目组名称", trigger: "blur" }
        ]
      },
      parentGroupIds: [],
      projectStaffObj: {},
      projectGroupTreeList: [],
      availableEmployeeGroup: [],
      groupStaffList: [],
      dlgProjectGroupEditVis: false,
      dlgProjectStaffEditVis: false,
      selectedProjectStaffs: [],
      builtinRoleParamList: []
    };
  },
  methods: {
    closeDialog() {
      this.$emit("close");
    },
    selectParamValueList(paramKeyObj) {
      var _this = this;
      SELECT_PARAM_VALUE_LIST(paramKeyObj).then(res => {
        _this.builtinRoleParamList = res;
      });
    },
    selectProjectGroupTreeList(projectId) {
      var _this = this;
      SELECT_PROJECT_GROUP_TREE_LIST({ projectId: projectId }).then(res => {
        _this.projectGroupTreeList = res;
      });
    },
    selectEmployeeGroup(projectId) {
      var _this = this;
      SELECT_AVAILABLE_GROUP_PROJECT_EMPLOYEE_LIST({ id: projectId }).then(
        res => {
          _this.availableEmployeeGroup = res;
        }
      );
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
              _this.selectProjectGroupTreeList(_this.$props.projectId);
              _this.projectGroupObj.id = data;
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
        _this.selectGroupStaffList(_this.projectGroupObj.id);
        _this.dlgProjectStaffEditVis = false;
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
            _this.selectProjectGroupTreeList(_this.$props.projectId);
          });
        });
    },
    openAddProjectGroup() {
      this.projectGroupObj = Object.assign({
        id: "",
        projectId: this.$props.projectId,
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
        projectId: this.$props.projectId,
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
      this.selectOwnerProjectEmpList(this.$props.projectId);
      this.dlgProjectStageEditVis = true;
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
      }
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
    }
  },
  watch: {
    projectId: {
      handler: function() {
        this.selectProjectGroupTreeList(this.$props.projectId);
        this.selectEmployeeGroup(this.$props.projectId);
      }
    }
  },
  computed: {},
  mounted() {
    this.selectParamValueList({ paramKey: "built-in-role" });
    this.$bus.$on('update-contract', this.selectEmployeeGroup(this.$props.projectId));
  },
  destroyed() {
    this.$bus.$off("update-contract", this.selectEmployeeGroup(this.$props.projectId)); // 释放/取消订阅
  }
};
</script>

<style>

</style>
