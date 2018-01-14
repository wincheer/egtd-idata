<template>
  <section>
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>项目模板</span>
            <el-button @click="openAddTplProject" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加项目模板</el-button>
          </div>
          <el-table :data="tplProjectList" highlight-current-row @current-change="onTplProjectChange">
            <el-table-column label="模板名称" prop="tplName"></el-table-column>
            <el-table-column label="模板类型" prop="tplCategory"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="openEditTplProject(scope.row)">编辑</el-button>
                <el-button size="mini" type="text" @click="delTplProject(scope.row)" >删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;">
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>模板项目阶段</span>
            <el-button @click="openAddTplStage" :disabled="tplStageObj.tplId==''" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加项目阶段</el-button>
          </div>
          <el-tree :data="tplStageTreeList" :render-content="renderContentStage" highlight-current :expand-on-click-node="false" default-expand-all></el-tree>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>模板项目组</span>
            <el-button @click="openAddTplGroup" :disabled="tplStageObj.tplId==''" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加项目组</el-button>
          </div>
          <el-tree :data="tplGroupTreeList" :render-content="renderContentGroup" highlight-current :expand-on-click-node="false" default-expand-all></el-tree>
        </el-card>
      </el-col>
    </el-row>
    <!--编辑窗口-->
    <el-dialog :title="tplProjectObj.id==''?'增加项目模板':'编辑项目模板'" :visible.sync="dlgTplProjectEditVis" width="30%">
      <el-form :model="tplProjectObj" :rules="tplProjectObjRules" ref="tplProjectForm" label-width="80px">
        <el-form-item label="模板名称" prop="tplName">
          <el-input type="text" v-model="tplProjectObj.tplName"></el-input>
        </el-form-item>
        <el-form-item label="模板分类" prop="tplCategory">
          <el-input type="text" v-model="tplProjectObj.tplCategory"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateTplProject" :loading="logining">保存</el-button>
          <el-button @click="dlgTplProjectEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog :title="tplStageObj.id==''?'增加项目模板':'编辑项目模板'" :visible.sync="dlgTplStageEditVis" width="30%">
      <el-form :model="tplStageObj" :rules="tplStageObjRules" ref="tplStageForm" label-width="80px">
        <el-form-item label="上级阶段" prop="parentId">
          <el-cascader :options="tplStageTreeList" :props="{value:'id'}" v-model="stageParentIds" @change="onStageParentChange" change-on-select style="width:100%"/>
        </el-form-item>
        <el-form-item label="阶段名称" prop="stageName">
          <el-input type="text" v-model="tplStageObj.stageName"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateTplStage" :loading="logining">保存</el-button>
          <el-button @click="dlgTplStageEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </section>
</template>

<script>
import {
  SELECT_TPL_PROJECT_LIST,
  UPDATE_TPL_PROJECT,
  DELETE_TPL_PROJECT,
  SELECT_TPL_STAGE_TREE_LIST,
  UPDATE_TPL_STAGE,
  DELETE_TPL_STAGE,
  SELECT_TPL_GROUP_TREE_LIST,
  UPDATE_TPL_GROUP,
  DELETE_TPL_GROUP
} from "@/config/api";
export default {
  data() {
    return {
      logining: false,
      tplProjectList: [],
      tplStageTreeList: [],
      tplGroupTreeList: [],
      dlgTplProjectEditVis: false,
      dlgTplStageEditVis: false,
      dlgTplGroupEditVis: false,
      tplProjectObj: {
        id: 0,
        tplName: "",
        tplCategory: ""
      },
      tplProjectObjRules: {
        tplName: [{ required: true, message: "请输入模板名称", trigger: "blur" }],
        tplCategory: [{ required: true, message: "请输模板类别", trigger: "blur" }]
      },
      tplStageObj: {
        id: 0,
        tplId: "",
        stageName: "",
        parentId: null
      },
      tplStageObjRules: {
        stageName: [{ required: true, message: "请输入阶段/任务名称", trigger: "blur" }]
      },
      stageParentIds:[],
      tplGroupObj: {
        id: 0,
        tplId: "",
        groupName: "",
        parentId: null
      },
      tplGroupObjRules: {
        groupName: [{ required: true, message: "请输入项目组/小组名称", trigger: "blur" }]
      },
      groupParentIds:[],
    };
  },
  methods: {
    selectTplProjectList() {
      var _this = this;
      SELECT_TPL_PROJECT_LIST().then(res => {
        if (!Array.isArray(res))
          _this.$message({ message: "获取项目模板失败，请联系系统管理员。", type: "error" });
        else {
          _this.tplProjectList = res;
        }
      });
    },
    selectTplStageTreeList(tplId){
      var _this = this;
      SELECT_TPL_STAGE_TREE_LIST({tplId:tplId}).then(res => {
        if (!Array.isArray(res))
          _this.$message({ message: "获取模板项目阶段失败，请联系系统管理员。", type: "error" });
        else {
          _this.tplStageTreeList = res;
        }
      });
    },
    selectTplGroupTreeList(tplId){
      var _this = this;
      SELECT_TPL_GROUP_TREE_LIST({tplId:tplId}).then(res => {
        if (!Array.isArray(res))
          _this.$message({ message: "获取模板项目组失败，请联系系统管理员。", type: "error" });
        else {
          _this.tplGroupTreeList = res;
        }
      });
    },
    openAddTplProject() {
      this.tplProjectObj.id = "";
      this.tplProjectObj.tplName = "";
      this.tplProjectObj.tplCategory = "";
      this.dlgTplProjectEditVis = true;
    },
    openAddTplStage() {
      this.tplStageObj.id = "";
      this.tplStageObj.stageName = "";
      this.tplStageObj.parentId = 0;
      this.dlgTplStageEditVis = true;
    },
    openAddTplGroup() {},
    openEditTplProject(row) {
      Object.assign(this.tplProjectObj, row);
      this.dlgTplProjectEditVis = true;
    },
    openEditTplStage(node, data){
      this.tplStageObj.id = data.id;
      this.tplStageObj.stageName = data.label;
      this.tplStageObj.tplId = parseInt(data.desc);
      this.tplStageObj.parentId = data.parentId;
      this.builderParentIdSeq(node,1);
      this.dlgTplStageEditVis = true;
    },

    builderParentIdSeq(node,who) {
      if(who == 1){
        this.stageParentIds = [];
        this.stageParentIds.unshift(node.data.parentId)
      }
      else{
        this.groupParentIds = [];
        this.groupParentIds.unshift(node.data.parentId)
      }
      this.unshiftParentId(node.parent,who);
    },
    unshiftParentId(pnode,who) {
      if (pnode!=null && pnode.level != 1) {
        if(who == 1) this.stageParentIds.unshift(pnode.data.parentId)
        else this.groupParentIds.unshift(pnode.data.parentId);        
        this.unshiftParentId(pnode.parent,who);
      }
    },

    updateTplProject() {
      var _this = this;
      this.$refs.tplProjectForm.validate(valid => {
        if (valid) {
          _this.logining = true;
          UPDATE_TPL_PROJECT(_this.tplProjectObj).then(data => {
            _this.logining = false;
            if (data == "") {
              _this.$message({ message: "更新项目模板失败，请联系系统管理员。", type: "error" });
            } else {
              _this.selectTplProjectList();
              _this.dlgTplProjectEditVis = false;
              _this.tplStageTreeList = [];
              _this.tplGroupTreeList = [];
            }
          });
        }
      });
    },
    updateTplStage(){
      var _this = this;
      this.$refs.tplStageForm.validate(valid => {
        if (valid) {
          // 检查parentId是否等于自身的id
          if (_this.tplStageObj.id === _this.tplStageObj.parentId) {
            _this.$message({ message: "不能将自身设置为上级。", type: "error" });
            return;
          }
          _this.logining = true;
          UPDATE_TPL_STAGE(_this.tplStageObj).then(data => {
            _this.logining = false;
            if (data == "") {
              _this.$message({ message: "更新项目阶段失败，请联系系统管理员。", type: "error" });
            } else {
              _this.selectTplStageTreeList(_this.tplStageObj.tplId);
              _this.dlgTplStageEditVis = false;
            }
          });
        }
      });
    },
    updateTplGroup(){},
    delTplProject(row) {
      var _this = this;
      _this
        .$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        })
        .then(() => {
          DELETE_TPL_PROJECT({ id: row.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectTplProjectList();
          });
        });
    },
    onTplProjectChange(data) {
      this.tplStageObj.tplId = data.id;
      this.tplGroupObj.tplId = data.id;
      this.selectTplStageTreeList(data.id);
      this.selectTplGroupTreeList(data.id);
    },
    onStageParentChange(value){
      if (value.length != 0) {
        this.tplStageObj.parentId = value[value.length - 1];
      } else this.tplStageObj.parentId = 0;
    },
    onGroupParentChange(){},
    renderContentStage(h, { node, data, store }) {
      return (
        <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
          <span>
            <span>{node.label}</span>
          </span>
          <span>
            <el-button
              style="font-size: 12px;"
              type="text"
              on-click={() => this.openEditTplStage(node, data)}
            >
              编辑
            </el-button>
            <el-button
              style="font-size: 12px;"
              type="text"
              on-click={() => this.delTplStage(node, data)}
            >
              删除
            </el-button>
          </span>
        </span>
      );
    },
    renderContentGroup(h, { node, data, store }) {
      return (
        <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
          <span>
            <span>{node.label}</span>
          </span>
          <span>
            <el-button
              style="font-size: 12px;"
              type="text"
              on-click={() => this.openEditTplGroup(node, data)}
            >
              编辑
            </el-button>
            <el-button
              style="font-size: 12px;"
              type="text"
              on-click={() => this.delTplGroup(node, data)}
            >
              删除
            </el-button>
          </span>
        </span>
      );
    }
  },
  mounted() {
    this.selectTplProjectList();
  }
};
</script>

<style>

</style>
