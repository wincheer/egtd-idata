<template>
  <section>
    <el-tabs type="border-card" v-model="activeName" style="margin-top: 20px;">
      <el-tab-pane name="first">
        <span slot="label"><i class="el-icon-service"></i>业主组织结构</span>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-card>
              <div slot="header" class="clearfix">
                <span>组织结构</span>
                <el-button @click="openAddDep" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加机构</el-button>
              </div>
              <el-tree :data="depTreeList" :props="defaultProps" @node-click="onNodeClick" :render-content="renderContent" highlight-current :expand-on-click-node="false" default-expand-all></el-tree>
            </el-card>
          </el-col>
          <el-col :span="16">
            <el-card>
              <div slot="header" class="clearfix">
                <span>机构员工</span>
                <el-button @click="openAddDepEmp" :disabled="depEmpObj.depId==''" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加机构员工</el-button>
              </div>
              <el-table :data="depEmpList" style="width: 100%">
                <el-table-column label="姓名" prop="empName"></el-table-column>
                <el-table-column label="性别" prop="empGender"></el-table-column>
                <el-table-column label="电话" prop="empMobile"></el-table-column>
                <el-table-column label="邮箱" prop="empEmail"></el-table-column>
                <el-table-column label="职位" prop="empGrade"></el-table-column>
                <el-table-column label="职务" prop="empTitle"></el-table-column>
                <el-table-column label="操作" width="160">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" @click="openEditDepEmp(scope.row)">编辑</el-button>
                    <el-button size="mini" type="text" @click="delDepEmp(scope.row)" >删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane name="second">
        <span slot="label"><i class="el-icon-menu"></i> 供应商资源管理</span>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-card>
              <div slot="header" class="clearfix">
                <span>供应商</span>
                <el-button @click="openAddVendor" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加供应商</el-button>
              </div>
              <el-table :data="vendorList">
                <el-table-column label="名称" prop="empName"></el-table-column>
                <el-table-column label="全称" prop="empName"></el-table-column>
                <el-table-column label="资质" prop="empName"></el-table-column>
                <el-table-column label="联系人" prop="empName"></el-table-column>
                <el-table-column label="联系电话" prop="empName"></el-table-column>
              </el-table>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card>
              <div slot="header" class="clearfix">
                <span>供应商员工</span>
                <el-button @click="dlgVendorEmpEditVis=true" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加供应商员工</el-button>
              </div>
              <el-table>
                <el-table-column label="姓名"></el-table-column>
                <el-table-column label="电话"></el-table-column>
                <el-table-column label="邮箱"></el-table-column>
                <el-table-column label="职位"></el-table-column>
                <el-table-column label="职级"></el-table-column>
              </el-table>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
    <!--编辑窗口-->
    <el-dialog :title="depObj.id==''?'增加机构':'编辑机构'" :visible.sync="dlgDepEditVis" width="30%">
      <el-form :model="depObj" :rules="depObjRules" ref="depForm" label-width="80px">
        <el-form-item label="上级机构" prop="parentId">
          <el-cascader :options="depTreeList" :props="{value:'id'}" v-model="parentIds" @change="onParentChange" change-on-select style="width:100%">
        </el-cascader>
        </el-form-item>
        <el-form-item label="机构名称" prop="depName">
          <el-input type="text" v-model="depObj.depName"></el-input>
        </el-form-item>
        <el-form-item label="机构描述" prop="depDesc">
          <el-input type="text" v-model="depObj.depDesc"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateDep" :loading="logining">保存</el-button>
          <el-button @click="dlgDepEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog :title="depEmpObj.id==''?'增加机构员工':'编辑机构员工'" :visible.sync="dlgDepEmpEditVis" width="30%">
      <el-form :model="depEmpObj" :rules="depEmpObjRules" ref="depEmpForm" label-width="80px">
        <el-form-item label="姓名" prop="empName">
          <el-input type="text" v-model="depEmpObj.empName"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="empGender">
          <el-switch v-model="depEmpObj.empGender" active-text="女" inactive-text="男" active-value="0" inactive-value="1" active-color="#13ce66" inactive-color="#ff4949" />
        </el-form-item>
        <el-form-item label="电话" prop="empMobile">
          <el-input type="text" v-model="depEmpObj.empMobile"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="empEmail">
          <el-input type="text" v-model="depEmpObj.empEmail"></el-input>
        </el-form-item>
        <el-form-item label="职务" prop="empTitle">
          <el-input type="text" v-model="depEmpObj.empTitle"></el-input>
        </el-form-item>
        <el-form-item label="职位" prop="empGrade">
          <el-input type="text" v-model="depEmpObj.empGrade"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateDepEmp" :loading="logining">保存</el-button>
          <el-button @click="dlgDepEmpEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog :title="vendorObj.id==''?'增加机构':'编辑机构'" :visible.sync="dlgVendorEditVis" width="30%">
      <el-form :model="vendorObj" :rules="vendorObjRules" ref="vendorForm" label-width="80px">
        <el-form-item label="名称" prop="vendorName">
          <el-input type="text" v-model="vendorObj.vendorName" placeholder="供应商公司名称"></el-input>
        </el-form-item>
        <el-form-item label="全称" prop="vendorFullName">
          <el-input type="text" v-model="vendorObj.vendorFullName"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="contactName">
          <el-input type="text" v-model="vendorObj.contactName" placeholder="联系人姓名"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactMobile">
          <el-input type="text" v-model="vendorObj.contactMobile" placeholder="电话号码(手机)"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateDep" :loading="logining">保存</el-button>
          <el-button @click="dlgVendorEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </section>
</template>

<script>
import { 
  SELECT_DEP_TREE_LIST, 
  UPDATE_DEP, 
  DELETE_DEP, 
  SELECT_DEP_EMP_LIST,
  UPDATE_DEP_EMP,
  DELETE_DEP_EMP 
} from "@/config/api";
export default {
  data() {
    return {
      logining: false,
      activeName: "first",
      depTreeList: [],
      depEmpList: [],
      vendorList: [],
      vendorEmpList: [],
      dlgDepEditVis: false,
      dlgDepEmpEditVis: false,
      dlgVendorEditVis: false,
      dlgVendorEmpEditVis: false,
      defaultProps: {
        children: "children",
        label: "label"
      },
      depObj: {
        id: 0,
        depName: "",
        depDesc: "",
        parentId: null
      },
      depObjRules: {
        depName: [{ required: true, message: "请输入参数代码", trigger: "blur" }]
      },
      parentIds: [],
      depEmpObj: {
        id: "",
        depId: "",
        empName: "",
        empGender: 1,
        empMobile: "",
        empEmail: "",
        empTitle: "",
        empGrade: ""
      },
      depEmpObjRules: {
        empName: [{ required: true, message: "请输入员工姓名", trigger: "blur" }],
        empMobile: [
          { required: true, message: "请输入电话号码（用来登陆）", trigger: "blur" },
          { min: 11, max: 11, message: '电话号码长度为11个字符', trigger: 'blur' }
        ],
        empEmail:[{ type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }]
      },
      vendorObj: {
        id: "",
        vendorName: "",
        vendorFullName: "",
        contactName: "",
        contactMobile:""
      },
      vendorObjRules: {
        vendorName: [{ required: true, message: "请输入供应商名称", trigger: "blur" }],
        contactName: [{ required: true, message: "请输入联系人姓名", trigger: "blur" },{ min: 8, max: 11, message: '电话号码长度为8-11个字符', trigger: 'blur' }],
        contactMobile: [{ required: true, message: "请输入联系电话(手机)", trigger: "blur" }]
      },
    };
  },
  methods: {
    selectDepTreeList() {
      var _this = this;
      SELECT_DEP_TREE_LIST().then(res => {
        if (!Array.isArray(res))
          _this.$message({ message: "获取组织结构失败，请联系系统管理员。", type: "error" });
        else {
          _this.depTreeList = res;
        }
      });
    },
    selectDepEmpList(depId){
      var _this = this;
      SELECT_DEP_EMP_LIST({ id: depId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({ message: "获取结构员工失败，请联系系统管理员。", type: "error" });
        else {
          _this.depEmpList = res;
        }
      });
    },
    updateDep() {
      var _this = this;
      this.$refs.depForm.validate(valid => {
        if (valid) {
          // 检查parentId是否等于自身的id
          if (_this.depObj.id == _this.depObj.parentId) {
            _this.$message({ message: "不能将自身设置为上级机构。", type: "error" });
            return;
          }
          _this.logining = true;
          UPDATE_DEP(_this.depObj).then(data => {
            _this.logining = false;
            if (data == "") {
              _this.$message({ message: "更新部门失败，请联系系统管理员。", type: "error" });
            } else {
              _this.selectDepTreeList();
              _this.dlgDepEditVis = false;
            }
          });
        }
      });
    },
    updateDepEmp() {
      var _this = this;
      this.$refs.depEmpForm.validate(valid => {
        if (valid) {
          _this.logining = true;
          UPDATE_DEP_EMP(_this.depEmpObj).then(data => {
            _this.logining = false;
            if (data == "") {
              _this.$message({ message: "更新员工失败，请联系系统管理员。", type: "error" });
            } else {
              _this.selectDepEmpList(_this.depEmpObj.depId);
              _this.dlgDepEmpEditVis = false;
            }
          });
        }
      });
    },
    openAddDep() {
      this.depObj.id = null;
      this.depObj.depName = "";
      this.depObj.depDesc = "";
      this.depObj.parentId = 0;
      this.parentIds = [];
      this.dlgDepEditVis = true;
    },
    openAddDepEmp() {
      this.depEmpObj.id = "";
      this.depEmpObj.empName = "";
      this.depEmpObj.empGender = 1;
      this.depEmpObj.empMobile = "";
      this.depEmpObj.empEmail = "";
      this.depEmpObj.empTitle = "";
      this.depEmpObj.empGrade = "";
      this.dlgDepEmpEditVis = true;
    },
    openAddVendor() {
      this.vendorObj.id = "";
      this.vendorObj.vendorName = "";
      this.vendorObj.vendorFullName = "";
      this.vendorObj.contactName = "";
      this.vendorObj.contactMobile = "";
      this.dlgVendorEditVis = true;
    },
    openEditDep(node, data) {
      this.depObj.id = data.id;
      this.depObj.depName = data.label;
      this.depObj.depDesc = data.desc;
      this.depObj.parentId = data.parentId;
      this.builderParentIdSeq(node);
      this.dlgDepEditVis = true;
    },
    openEditDepEmp(row) {
      Object.assign(this.depEmpObj, row);
      this.dlgDepEmpEditVis = true;
    },
    builderParentIdSeq(node) {
      this.parentIds = [];
      if (node.level != 1) {
        this.parentIds.unshift(node.data.parentId); //push末尾添加,unshift开头添加
        this.unshiftParentId(node.parent);
      }
    },
    unshiftParentId(pnode) {
      if (pnode.level != 1) {
        this.parentIds.unshift(pnode.data.parentId);
        this.unshiftParentId(pnode.parent);
      }
    },
    delDep(node, data) {
      var _this = this;
      if(node.childNodes.length != 0){
        _this.$message({ message: "当前机构包含下级机构，不允许删除。", type: "warning" });
        return;
      }
      _this.$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        }).then(() => {
          DELETE_DEP({ id: data.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectDepTreeList();
            _this.depEmpList = [];
          });
        });
    },
    delDepEmp(row) {
      var _this = this;
      _this.$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        }).then(() => {
          DELETE_DEP_EMP({ id: row.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectDepEmpList(row.depId);
          });
        });
    },
    onNodeClick(data) {
      // 查询当前机构的员工
      this.selectDepEmpList(data.id);
      this.depEmpObj.depId = data.id;
    },
    onParentChange(value) {
      if (value.length != 0) {
        this.depObj.parentId = value[value.length - 1];
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
              on-click={() => this.openEditDep(node, data)}
            >
              编辑
            </el-button>
            <el-button
              style="font-size: 12px;"
              type="text"
              on-click={() => this.delDep(node, data)}
            >
              删除
            </el-button>
          </span>
        </span>
      );
    }
  },
  watch: {},
  computed: {},
  mounted() {
    this.selectDepTreeList();
  }
};
</script>

<style>

</style>
