<template>
  <section>
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>项目模板</span>
            <el-button @click="openAddTplProject" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">项目模板</el-button>
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
            <el-button @click="openAddDep" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">模板项目阶段</el-button>
          </div>
          <el-tree :data="tplStageTreeList" @node-click="onStageClick" :render-content="renderContent" highlight-current :expand-on-click-node="false" default-expand-all></el-tree>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header" class="clearfix">
            <span>模板项目组</span>
            <el-button @click="openAddDep" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">模板项目组</el-button>
          </div>
          <el-tree :data="tplGroupTreeList" @node-click="onGroupClick" :render-content="renderContent" highlight-current :expand-on-click-node="false" default-expand-all></el-tree>
        </el-card>
      </el-col>
    </el-row>
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
  props: {},
  data() {
    return {
      logining: false,
      tplProjectList:[],
      tplStageTreeList:[],
      tplGroupTreeList:[],
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
        parentId:null
      },
      tplStageObjRules: {
        stageName: [{ required: true, message: "请输入阶段/任务名称", trigger: "blur" }]
      },
      tplGroupObj: {
        id: 0,
        tplId: "",
        groupName: "",
        parentId:null
      },
      tplGroupObjRules: {
        groupName: [{ required: true, message: "请输入项目组/小组名称", trigger: "blur" }]
      }
    };
  },
  methods: {
    renderContentStage(h, { node, data, store }) {
      return (
        <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
          <span>
            <span>{node.label}</span>
          </span>
          <span>
            <el-button style="font-size: 12px;" type="text" on-click={() => this.openEditTplStage(node, data)}>编辑</el-button>
            <el-button style="font-size: 12px;" type="text" on-click={() => this.delTplStage(node, data)}>删除</el-button>
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
            <el-button style="font-size: 12px;" type="text" on-click={() => this.openEditTplGroup(node, data)}>编辑</el-button>
            <el-button style="font-size: 12px;" type="text" on-click={() => this.delTplGroup(node, data)}>删除</el-button>
          </span>
        </span>
      );
    },
  },
  mounted() {}
};
</script>

<style>

</style>
