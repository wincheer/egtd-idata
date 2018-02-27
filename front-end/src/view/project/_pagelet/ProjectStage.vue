<template>
  <section>
    <el-dialog title="分解项目阶段" :visible.sync="isShow" :before-close="closeDialog" :close-on-click-modal="false" width="620px">
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
    <el-dialog :title="projectStageObj.id==''?'增加项目阶段':'编辑项目阶段'" :visible.sync="dlgProjectStageEditVis" width="300px" :close-on-click-modal="false">
      <el-form :model="projectStageObj" :rules="projectStageObjRules" ref="projectStageForm" label-width="80px" size="mini">
        <el-form-item label="阶段名称" prop="text">
          <el-input type="text" v-model="projectStageObj.text" style="width:135px"/>
        </el-form-item>
        <el-form-item label="启动日期" prop="start_date">
          <el-date-picker type="date" clearable placeholder="启动日期" v-model="projectStageObj.start_date" style="width:135px"/>
        </el-form-item>
        <el-form-item label="结束日期" prop="end_date">
          <el-date-picker type="date" clearable placeholder="结束日期" v-model="projectStageObj.end_date" style="width:135px"/>
        </el-form-item>
        <el-form-item label="负责人" prop="category">
          <el-select v-model="projectStageObj.actorStaffId" placeholder="请选择" clearable style="width:135px">
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
  SELECT_PROJECT_STAGE_LIST,
  UPDATE_PROJECT_STAGE,
  DELETE_PROJECT_STAGE,
  SELECT_OWNER_PROJECT_EMPLOYEE_LIST,
  SELECT_ALL_DEP_EMP_LIST
} from "@/config/api";
import { formatDate } from "@/util/date.js";
export default {
  name: "project-stage",
  props: {
    isShow: false,
    projectId: ""
  },
  data() {
    return {
      projectStageList: [],
      projectStageObj: {},
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
      ownerProjectEmpList: [],
      allDepEmpList: [],
      dlgProjectStageEditVis: false
    };
  },
  methods: {
    closeDialog() {
      this.$emit("close");
    },
    selectOwnerProjectEmpList(projectId) {
      var _this = this;
      SELECT_OWNER_PROJECT_EMPLOYEE_LIST({ id: projectId }).then(res => {
        _this.ownerProjectEmpList = res;
      });
    },
    selectAllDepEmpList() {
      var _this = this;
      SELECT_ALL_DEP_EMP_LIST({}).then(res => {
        _this.allDepEmpList = res;
      });
    },
    selectProjectStageList(projectId) {
      var _this = this;
      SELECT_PROJECT_STAGE_LIST({ projectId: projectId }).then(res => {
        _this.projectStageList = res;
      });
    },
    openEditProjectStage(row) {
      this.projectStageObj = Object.assign(row);
      this.selectOwnerProjectEmpList(this.$props.projectId);
      this.dlgProjectStageEditVis = true;
    },
    openAddProjectStage() {
      this.projectStageObj = Object.assign({
        id: "",
        projectId: this.$props.projectId,
        text: "",
        start_date: "",
        end_date: "",
        actorStaffId: ""
      });
      this.selectOwnerProjectEmpList(this.$props.projectId);
      this.dlgProjectStageEditVis = true;
    },
    updateProjectStage() {
      var _this = this;
      this.$refs.projectStageForm.validate(valid => {
        if (valid) {
          UPDATE_PROJECT_STAGE(_this.projectStageObj).then(data => {
            _this.dlgProjectStageEditVis = false;
            _this.selectProjectStageList(this.$props.projectId);
            _this.projectStageObj.id = data;
          });
        }
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
    fmtDate(row, column, cellValue) {
      if (cellValue == null) return "";
      else return formatDate(new Date(cellValue), "yyyy-MM-dd");
    },
    fmtEmp(row, column, cellValue) {
      for (var i = 0; i < this.allDepEmpList.length; i++) {
        if (this.allDepEmpList[i].id == cellValue)
          return this.allDepEmpList[i].empName;
      }
      return cellValue;
    }
  },
  watch: {
    projectId: {
      handler: function() {
        this.selectProjectStageList(this.$props.projectId);
      }
    }
  },
  computed: {},
  mounted() {
    this.selectAllDepEmpList();
  }
};
</script>

<style>

</style>
