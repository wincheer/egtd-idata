<template>
    <section>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-card>
              <div slot="header" class="clearfix">
                <span>{{vendorTitle}}</span>
                <el-button @click="openAddVendor" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加{{vendorTitle}}</el-button>
              </div>
              <el-table :data="vendorList" highlight-current-row @current-change="onVendorChange">
                <el-table-column label="名称" prop="vendorName" ></el-table-column>
                <el-table-column label="联系人" prop="contactName" ></el-table-column>
                <el-table-column label="联系电话" prop="contactMobile" width="120"></el-table-column>
                <el-table-column label="操作" width="100">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" @click="openEditVendor(scope.row)">编辑</el-button>
                    <el-button size="mini" type="text" @click="delVendor(scope.row)" >删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-card>
          </el-col>
          <el-col :span="14">
            <el-card>
              <div slot="header" class="clearfix">
                <span>{{vendorTitle}}员工</span>
                <el-button @click="openAddVendorEmp" :disabled="employeeObj.orgId==''" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加{{vendorTitle}}员工</el-button>
              </div>
              <el-table :data="employeeList">
                <el-table-column label="姓名" prop="empName" ></el-table-column>
                <el-table-column label="性别" prop="empGender"  :formatter="fmtGender"></el-table-column>
                <el-table-column label="电话" prop="empMobile" ></el-table-column>
                <el-table-column label="邮箱" prop="empEmail"></el-table-column>
                <el-table-column label="操作" width="100">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" @click="openEditVendorEmp(scope.row)">编辑</el-button>
                    <el-button size="mini" type="text" @click="delVendorEmp(scope.row)" >删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-card>
          </el-col>
        </el-row>
        <!-- 编辑窗口 -->
        <el-dialog :title="vendorObj.id==''?'增加'+vendorTitle:'编辑'+vendorTitle" :visible.sync="dlgVendorEditVis" width="30%">
            <el-form :model="vendorObj" :rules="vendorObjRules" ref="vendorForm" label-width="80px">
                <el-form-item label="名称" prop="vendorName">
                    <el-input type="text" v-model="vendorObj.vendorName" placeholder="公司名称"></el-input>
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
                    <el-button type="primary" @click="updateVendor">保存</el-button>
                    <el-button @click="dlgVendorEditVis = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog :title="employeeObj.id==''?'增加'+vendorTitle+'员工':'编辑'+vendorTitle+'员工'" :visible.sync="dlgVendorEmpEditVis" width="30%">
            <el-form :model="employeeObj" :rules="employeeObjRules" ref="employeeForm" label-width="80px">
                <el-form-item label="姓名" prop="empName">
                    <el-input type="text" v-model="employeeObj.empName"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="empGender">
                    <el-switch v-model="employeeObj.empGender" active-text="女" inactive-text="男" active-value="0" inactive-value="1" active-color="#13ce66" inactive-color="#ff4949" />
                </el-form-item>
                <el-form-item label="电话" prop="empMobile">
                    <el-input type="text" v-model="employeeObj.empMobile"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="empEmail">
                    <el-input type="text" v-model="employeeObj.empEmail"></el-input>
                </el-form-item>
                <el-form-item label="职务" prop="empTitle">
                    <el-input type="text" v-model="employeeObj.empTitle"></el-input>
                </el-form-item>
                <el-form-item label="职位" prop="empGrade">
                    <el-input type="text" v-model="employeeObj.empGrade"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="updateVendorEmp">保存</el-button>
                    <el-button @click="dlgVendorEmpEditVis = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </section>
</template>

<script>
import {
  SELECT_VENDOR_LIST,
  SELECT_VENDOR_EMP_LIST,
  UPDATE_VENDOR,
  DELETE_VENDOR,
  UPDATE_VENDOR_EMP,
  DELETE_VENDOR_EMP
} from "@/config/api";
export default {
  name: "vendor-list",
  props: {
    category: "" //监理supervisor，其它供应商builder
  },
  data() {
    return {
      vendorTitle: "",
      vendorList: [],
      employeeList: [],
      selectedVendor: {},
      selectedEmployee: {},
      vendorObj: {
        id: "",
        vendorName: "",
        vendorFullName: "",
        contactName: "",
        contactMobile: ""
      },
      vendorObjRules: {
        vendorName: [
          { required: true, message: "请输入承建设单位名称", trigger: "blur" }
        ],
        contactName: [
          { required: true, message: "请输入联系人姓名", trigger: "blur" }
        ],
        contactMobile: [
          { required: true, message: "请输入联系电话(手机)", trigger: "blur" },
          {
            min: 8,
            max: 11,
            message: "电话号码长度为8-11个字符",
            trigger: "blur"
          }
        ]
      },
      employeeObj: {
        id: "",
        orgId: "",
        empName: "",
        empGender: 1,
        empMobile: "",
        empEmail: "",
        empTitle: "",
        empGrade: ""
      },
      employeeObjRules: {
        empName: [
          { required: true, message: "请输入员工姓名", trigger: "blur" }
        ],
        empMobile: [
          {
            required: true,
            message: "请输入电话号码（用来登陆）",
            trigger: "blur"
          },
          {
            min: 11,
            max: 11,
            message: "电话号码长度为11个字符",
            trigger: "blur"
          }
        ],
        empEmail: [
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: "blur,change"
          }
        ]
      },
      dlgVendorEditVis: false,
      dlgVendorEmpEditVis: false,
    };
  },
  methods: {
    selectVendorList(category) {
      var _this = this;
      SELECT_VENDOR_LIST({ vendorCategory: category }).then(res => {
        _this.vendorList = res;
      });
    },
    selectVendorEmpList(orgId){
      var _this = this;
      SELECT_VENDOR_EMP_LIST({ id: orgId }).then(res => {
        if (!Array.isArray(res))
          _this.$message({ message: "获取承建设单位员工失败，请联系系统管理员。", type: "error" });
        else {
          _this.employeeList = res;
        }
      });
    },
    onVendorChange(data) {
      // 查询当前承建设单位的员工
      this.selectVendorEmpList(data.id);
      this.employeeObj.orgId = data.id;
    },
    openAddVendor() {
      this.vendorObj.id = "";
      this.vendorObj.vendorName = "";
      this.vendorObj.contactName = "";
      this.vendorObj.contactMobile = "";
      this.vendorObj.vendorCategory = this.$props.category;
      this.dlgVendorEditVis = true;
    },
    openEditVendor(row) {
      Object.assign(this.vendorObj, row);
      this.dlgVendorEditVis = true;
    },
    openEditVendorEmp(row) {
      Object.assign(this.employeeObj, row);
      this.dlgVendorEmpEditVis = true;
    },
    openAddVendorEmp() {
      this.employeeObj.id = "";
      this.employeeObj.code = this.$props.category === "builder" ? "s" : "m";
      this.employeeObj.empName = "";
      this.employeeObj.empGender = 1;
      this.employeeObj.empMobile = "";
      this.employeeObj.empEmail = "";
      this.employeeObj.empTitle = "";
      this.employeeObj.empGrade = "";
      this.dlgVendorEmpEditVis = true;
    },
    updateVendor() {
      var _this = this;
      this.$refs.vendorForm.validate(valid => {
        if (valid) {
          _this.logining = true;
          UPDATE_VENDOR(_this.vendorObj).then(data => {
            _this.logining = false;
            if (data == "") {
              _this.$message({
                message: "更新承建设单位失败，请联系系统管理员。",
                type: "error"
              });
            } else {
              _this.selectVendorList(_this.$props.category);
              _this.dlgVendorEditVis = false;
              _this.employeeList = [];
            }
          });
        }
      });
    },
    updateVendorEmp() {
      var _this = this;
      this.$refs.employeeForm.validate(valid => {
        if (valid) {
          _this.logining = true;
          UPDATE_VENDOR_EMP(_this.employeeObj).then(data => {
            _this.logining = false;
            if (data == "") {
              _this.$message({
                message: "更新员工失败，请联系系统管理员。",
                type: "error"
              });
            } else {
              _this.selectVendorEmpList(_this.employeeObj.orgId);
              _this.dlgVendorEmpEditVis = false;
            }
          });
        }
      });
    },
    delVendor(row) {
      var _this = this;
      _this.$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        }).then(() => {
          DELETE_VENDOR({ id: row.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectVendorList(_this.$props.category);
            _this.employeeList = [];
          });
        });
    },
    delVendorEmp(row) {
      var _this = this;
      _this.$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        }).then(() => {
          DELETE_VENDOR_EMP({ id: row.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectVendorEmpList(row.orgId);
          });
        });
    },
    fmtGender(row, column, cellValue) {
      return cellValue == 1 ? "男" : "女";
    }
  },
  mounted() {
    this.vendorTitle = this.$props.category === "builder" ? "承建设单位" : "监理单位";
    this.selectVendorList(this.$props.category);
  },
  watch: {
    // category: {
    //   handler: function() {
    //     this.vendorTitle = this.$props.category === "builder" ? "承建设单位" : "监理单位";
    //     this.selectVendorList(this.$props.category);
    //   }
    // }
  }
};
</script>

<style>

</style>
