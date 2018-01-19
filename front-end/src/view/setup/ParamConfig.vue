<template>
  <section>
    <el-row :gutter="20">
      <el-col :span="14">
        <el-card style="margin-top: 20px;">
          <div slot="header" class="clearfix">
            <span>参数</span>
            <el-button @click="dlgParamKeyEditVis=true" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加参数</el-button>
          </div>
          <el-table :data="paramKeyList" ref="paramKeyList" highlight-current-row  @current-change="onSelectParamKey">
            <el-table-column prop="paramKey" label="参数代码"></el-table-column>
            <el-table-column prop="paramKeyName" label="参数名字"></el-table-column>
            <el-table-column prop="dataType" label="数据类型"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="mini" @click="onEditParamKey(scope.row)">编辑</el-button>
                <el-button type="danger" @click="onDelParamKey(scope.row)" size="mini">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- <el-button type="primary" @click="dlgParamKeyEditVis=true" icon="el-icon-circle-plus" style="margin-top: 20px;">增加参数</el-button> -->
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card  style="margin-top: 20px;">
          <div slot="header" class="clearfix">
            <span>参数值</span>
            <el-button @click="dlgParamValueEditVis=true" :disabled="paramValueObj.paramId==''" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加参数值</el-button>
          </div>
        <el-table :data="paramValueList">
          <el-table-column prop="paramValue" label="参数值"></el-table-column>
          <el-table-column prop="paramDesc" label="参数含义"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" @click="onEditParamValue(scope.row)">编辑</el-button>
              <el-button type="danger" @click="onDelParamValue(scope.row)" size="mini">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- <el-button type="primary" @click="dlgParamValueEditVis=true" :disabled="paramValueObj.paramId==''" style="margin-top: 20px;">增加参数值</el-button> -->
      </el-card>
      </el-col>
    </el-row>
    <!--参数编辑对话框-->
    <el-dialog :title="paramKeyObj.id==''?'增加参数':'编辑参数'" :visible.sync="dlgParamKeyEditVis" width="30%">
      <el-form :model="paramKeyObj" :rules="paramKeyObjRules" ref="paramKeyForm" label-width="80px">
        <el-form-item label="参数代码" prop="paramKey">
          <el-input type="text" v-model="paramKeyObj.paramKey"></el-input>
        </el-form-item>
        <el-form-item label="参数名称" prop="paramKeyName">
          <el-input type="text" placeholder="参数的中文名称" v-model="paramKeyObj.paramKeyName"></el-input>
        </el-form-item>
        <el-form-item label="数据类型" prop="dataType">
          <el-select v-model="paramKeyObj.dataType" placeholder="请选择">
            <el-option label="字符串" value="String" />
            <el-option label="整数" value="Integer" />
            <el-option label="布尔型" value="Boolean" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateParamKey" :loading="logining">保存</el-button>
          <el-button @click="dlgParamKeyEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--参数值编辑对话框-->
    <el-dialog :title="paramValueObj.id==''?'增加参数值':'编辑参数值'" :visible.sync="dlgParamValueEditVis" width="30%">
      <el-form :model="paramValueObj" :rules="paramValueObjRules" ref="paramValueForm" label-width="80px">
        <el-form-item label="参数值" prop="paramValue">
          <el-input type="text" v-model="paramValueObj.paramValue"></el-input>
        </el-form-item>
        <el-form-item label="参数含义" prop="paramDesc">
          <el-input type="text" v-model="paramValueObj.paramDesc"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateParamValue" :loading="logining">保存</el-button>
          <el-button @click="dlgParamValueEditVis = false">取消</el-button>
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
      dlgParamKeyEditVis: false,
      dlgParamValueEditVis: false,
      paramKeyObj: {
        id: "",
        paramKey: "",
        paramKeyName: "",
        dataType: "String"
      },
      paramKeyObjRules: {
        paramKey: [{ required: true, message: "请输入参数代码", trigger: "blur" }],
        paramKeyName: [{ required: true, message: "请输入参数名称", trigger: "blur" }],
        dataType: [{ required: true, message: "请选择参数的数据类型", trigger: "change" }]
      },
      paramValueObj: {
        id: "",
        paramId: "",
        paramValue: "",
        paramDesc: ""
      },
      paramValueObjRules: {
        paramValue: [{ required: true, message: "请输入参数值", trigger: "blur" }],
        paramDesc: [{ required: true, message: "请输入参数含义", trigger: "blur" }]
      }
    };
  },
  methods: {
    selectParamKeyList() {
      var _this = this;
      SELECT_PARAM_KEY_LIST().then(res => {
        if (!Array.isArray(res))
          _this.$message({ message: "获取参数列表失败，请联系系统管理员。", type: "error" });
        else {
          _this.paramKeyList = res;
        }
      });
    },
    selectParamValueList(paramKeyID) {
      var _this = this;
      SELECT_PARAM_VALUE_LIST({ id: paramKeyID }).then(res => {
        if (!Array.isArray(res))
          _this.$message({ message: "获取参数列表失败，请联系系统管理员。", type: "error" });
        else {
          _this.paramValueList = res;
          _this.paramValueObj.paramId = paramKeyID;
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
              _this.$message({ message: "更新参数失败，请联系系统管理员。", type: "error" });
            } else {
              _this.selectParamKeyList();
              _this.dlgParamKeyEditVis = false;
            }
          });
        }
      });
    },
    updateParamValue() {
      var _this = this;
      this.$refs.paramValueForm.validate(valid => {
        if (valid) {
          _this.logining = true;
          UPDATE_PARAM_VALUE(_this.paramValueObj).then(data => {
            _this.logining = false;
            if (data == "") {
              _this.$message({ message: "更新参数值失败，请联系系统管理员。", type: "error" });
            } else {
              _this.selectParamValueList(_this.paramValueObj.paramId);
              _this.dlgParamValueEditVis = false;
            }
          });
        }
      });
    },
    onSelectParamKey(newRow, oldRow) {
      if (newRow != null) this.selectParamValueList(newRow.id);
    },
    onEditParamKey(paramKey) {
      Object.assign(this.paramKeyObj, paramKey);
      this.dlgParamKeyEditVis = true;
    },
    onEditParamValue(paramValue) {
      Object.assign(this.paramValueObj, paramValue);
      this.dlgParamValueEditVis = true;
    },
    onDelParamKey(paramKey) {
      var _this = this;
      _this.$confirm("确认删除该记录吗?", "提示", {
        type: "warning"
      }).then(() => {
        DEL_PARAM_KEY({ id: paramKey.id }).then(res => {
          _this.$message({message: "删除成功",type: "success"});
          _this.selectParamKeyList();
          _this.paramValueList = [];
        });
      });
    },
    onDelParamValue(paramValue) {
      var _this = this;
      _this.$confirm("确认删除该记录吗?", "提示", {
        type: "warning"
      }).then(() => {
        DEL_PARAM_VALUE({ id: paramValue.id }).then(res => {
          _this.$message({message: "删除成功",type: "success"});
          this.selectParamValueList(paramValue.paramId);
        });
      });
    }
  },
  mounted() {
    this.selectParamKeyList();
  }
};
</script>

<style>

</style>
