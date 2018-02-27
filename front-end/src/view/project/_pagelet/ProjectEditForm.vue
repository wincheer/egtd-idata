<template>
  <section>
    <el-dialog :title="project.id?'编辑项目':'新建项目'" :visible.sync="isShow" width="550px" :close-on-click-modal="false" :before-close="closeDialog">
      <el-form :model="project" :rules="projectObjRules" ref="projectForm" label-width="80px" size="mini">
        <el-row>
          <el-col :span="13">
            <el-form-item label="项目名称" prop="projectName">
              <el-input type="text" v-model="project.projectName" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="类别" prop="category">
              <el-select v-model="project.category" placeholder="请选择">
                <el-option v-for="item in categoryParamList" :key="item.id" :label="item.paramDesc" :value="item.paramValue" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="项目描述" prop="projectDesc">
          <el-input type="textarea" v-model="project.projectDesc"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="14">
            <el-form-item label="项目金额" prop="amount">
              <el-input-number type="text" v-model="project.amount" style="width:140px"/> 万元
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="关键项目" prop="isKey">
              <el-switch v-model="project.isKey" active-text="是" inactive-text="否" :active-value="1" :inactive-value="0"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目经理" prop="actorStaffId">
              <el-select v-model="project.actorStaffId" placeholder="甲方项目经理" style="width:140px">
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
          <el-date-picker type="date" clearable placeholder="立项时间" v-model="project.createDate" style="width:140px"/>
        </el-form-item>
        <el-row >
          <el-col :span="12">
            <el-form-item label="启动日期" prop="startDate">
              <el-date-picker type="date" clearable placeholder="启动日期" v-model="project.startDate" style="width:140px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束日期" prop="endDate">
              <el-date-picker type="date" clearable placeholder="结束日期" v-model="project.endDate" style="width:140px"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
          <el-button size="small" @click.native="closeDialog">取消</el-button>
          <el-button type="primary" size="small" @click="updateProject">保存</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import {
  SELECT_PARAM_VALUE_LIST,
  SELECT_ALL_DEP_EMP_LIST,
  SELECT_DEP_TREE_LIST,
  UPDATE_PROJECT
} from "@/config/api";
import { getNodePath, getNode } from "@/util/treeUtil.js";
export default {
  name: "project-edit-form",
  props: {
    isShow: false,
    project: {}
  },
  data() {
    return {
      categoryParamList: [],
      allDepEmpList: [],
      depTreeList: [],
      depIds: [],
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
      }
    };
  },
  methods: {
    closeDialog() {
      this.$emit("close");
    },
    selectParamValueList(paramKeyObj) {
      var _this = this;
      SELECT_PARAM_VALUE_LIST(paramKeyObj).then(res => {
        _this.categoryParamList = res;
      });
    },
    selectAllDepEmpList() {
      var _this = this;
      SELECT_ALL_DEP_EMP_LIST({}).then(res => {
        _this.allDepEmpList = res;
      });
    },
    selectDepTreeList() {
      var _this = this;
      SELECT_DEP_TREE_LIST().then(res => {
        _this.depTreeList = res;
      });
    },
    onDepChange(value) {
      if (value.length != 0) {
        this.$props.project.depId = value[value.length - 1];
      }
    },
    updateProject() {
      var _this = this;
      this.$refs.projectForm.validate(valid => {
        if (valid) {
          _this.$props.project.ownerId = _this.$store.state.loginUser.id;
          UPDATE_PROJECT(_this.$props.project).then(data => {
            _this.closeDialog();
            _this.$emit("updateSuccess");
          });
        }
      });
    }
  },
  watch: {
    project: {
      handler: function() {
        this.depIds = getNodePath(this.depTreeList, this.$props.project.depId);
      }
    }
  },
  computed: {},
  mounted() {
    this.selectParamValueList({ paramKey: "category" });
    this.selectAllDepEmpList();
    this.selectDepTreeList();
  }
};
</script>

<style>

</style>
