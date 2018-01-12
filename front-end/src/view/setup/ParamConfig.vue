<template>
  <section>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-table :data="paramKeyList" style="width:90%" highlight-current-row  @current-change="onSelectParamKey">
          <el-table-column prop="paramKey" label="参数代码"></el-table-column>
          <el-table-column prop="paramKeyName" label="参数名字"></el-table-column>
          <el-table-column prop="dataType" label="数据类型"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini">编辑</el-button>
              <el-button type="danger" size="mini">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button @click="dlgParamAddVis=true">增加参数</el-button>
      </el-col>
      <el-col :span="12">
        <el-table :data="paramValueList" style="width:90%">
          <el-table-column prop="paramValue" label="参数值"></el-table-column>
          <el-table-column label="操作"></el-table-column>
        </el-table>
        <el-button>增加参数值</el-button>
      </el-col>
    </el-row>
    <el-dialog :title="paramKeyObj.id==''?'增加参数':'编辑参数'" :visible.sync="dlgParamAddVis" width="30%">
      <el-form :model="paramKeyObj" :rules="paramKeyObjRules" ref="paramKeyForm" label-width="80px">
        <el-form-item label="参数代码" prop="paramKey">
          <el-input type="text" v-model="paramKeyObj.paramKey"></el-input>
        </el-form-item>
        <el-form-item label="参数名称" prop="paramKeyName">
          <el-input type="text" placeholder="参数的中文名称" v-model="paramKeyObj.paramKeyName"></el-input>
        </el-form-item>
        <el-form-item label="数据类型" prop="dataType">
          <el-input v-model="paramKeyObj.dataType"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateParamKey" :loading="logining">保存</el-button>
          <el-button @click="dlgParamAddVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </section>
</template>

<script>
import {
  SELECT_PARAM_KEY_LIST,
  SELECT_PARAM_VALUE_LIST,
  UPDATE_PARAM_KEY,
  UPDATE_PARAM_VALUE,
  DEL_PARAM_KEY,
  DEL_PARAM_VALUE
} from "@/config/api";
export default {
  data() {
    return {
      logining: false,
      paramKeyList: [],
      paramValueList: [],
      dlgParamAddVis: false,
      paramKeyObj: {
        id: "",
        paramKey: "",
        paramKeyName: "",
        dataType: ""
      },
      paramKeyObjRules: {
        paramKey: [{ required: true, message: "请输入参数代码", trigger: "blur" }],
        paramKeyName: [{ required: true, message: "请输入参数名称", trigger: "blur" }],
        dataType: [{ required: true, message: "请输入参数的数据类型", trigger: "blur" }]
      },
      paramValueObj: {}
    };
  },
  methods: {
    selectParamKeyList(){
      var _this = this;
      SELECT_PARAM_KEY_LIST().then(res =>{
        if(!Array.isArray(res))
         _this.$message({message: "获取参数列表失败，请联系系统管理员。",type: "error"});
         else{
           _this.paramKeyList = res;
         }
      });
    },
    selectParamValueList(paramKeyID){
      var _this = this;
      SELECT_PARAM_VALUE_LIST({id:paramKeyID}).then(res =>{
        if(!Array.isArray(res))
         _this.$message({message: "获取参数列表失败，请联系系统管理员。",type: "error"});
         else{
           _this.paramValueList = res;
         }
      });
    },
    updateParamKey() {
      var _this = this;
      this.$refs.paramKeyForm.validate(valid => {
        if (valid) {
          _this.logining = true;
          UPDATE_PARAM_KEY(_this.paramKeyObj).then(data => {
            _this.logining = false;
            if (data == "") {
              _this.$message({message: "更新参数失败，请联系系统管理员。",type: "error"});
            } else {
              _this.selectParamKeyList();
              _this.dlgParamAddVis = false;
            }
          });
        }
      });
    },
    onSelectParamKey(newRow,oldRow){
      this.selectParamValueList(newRow.id);
    }
  },
  mounted() {
    this.selectParamKeyList();
  }
};
</script>

<style>
.el-button {
  margin-top: 20px;
}
</style>
