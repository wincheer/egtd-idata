<template>
    <section>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-card>
              <div slot="header" class="clearfix">
                <span>{{vendorTitle}}</span>
                <el-button @click="openAddVendor" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加{{vendorTitle}}</el-button>
              </div>
              <el-table :data="builderList" highlight-current-row @current-change="onVendorChange">
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
                <el-button @click="openAddVendorEmp" :disabled="vendorEmpObj.orgId==''" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加{{vendorTitle}}员工</el-button>
              </div>
              <el-table :data="vendorEmpList">
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
                    <el-button type="primary" @click="updateVendor" :loading="logining">保存</el-button>
                    <el-button @click="dlgVendorEditVis = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog :title="EmployeeObj.id==''?'增加'+vendorTitle+'员工':'编辑'+vendorTitle+'员工'" :visible.sync="dlgVendorEmpEditVis" width="30%">
            <el-form :model="EmployeeObj" :rules="EmployeeObjRules" ref="vendorEmpForm" label-width="80px">
                <el-form-item label="姓名" prop="empName">
                    <el-input type="text" v-model="EmployeeObj.empName"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="empGender">
                    <el-switch v-model="EmployeeObj.empGender" active-text="女" inactive-text="男" active-value="0" inactive-value="1" active-color="#13ce66" inactive-color="#ff4949" />
                </el-form-item>
                <el-form-item label="电话" prop="empMobile">
                    <el-input type="text" v-model="EmployeeObj.empMobile"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="empEmail">
                    <el-input type="text" v-model="EmployeeObj.empEmail"></el-input>
                </el-form-item>
                <el-form-item label="职务" prop="empTitle">
                    <el-input type="text" v-model="EmployeeObj.empTitle"></el-input>
                </el-form-item>
                <el-form-item label="职位" prop="empGrade">
                    <el-input type="text" v-model="EmployeeObj.empGrade"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="updateVendorEmp" :loading="logining">保存</el-button>
                    <el-button @click="dlgVendorEmpEditVis = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </section>
</template>

<script>
export default {
  name: "vendor-list",
  props: {
    category: "" //监理supervisor，其它供应商builder
  },
  data() {
    return {
      vendorTitle:'',
      vendorObj:{},
      EmployeeObj:{},
      vendorList: [],
      employeeList: [],
      selectedVendor: {},
      selectedEmployee: {},
      dlgVendorEditVis: false,
      dlgVendorEmpEditVis: false,
    };
  },
  methods: {
      selectVendorList(category){
      var _this = this;
      SELECT_VENDOR_LIST({vendorCategory:category}).then(res => {
        _this.vendorList = res;
      });
    }
  },
  watch: {
    category: {
      handler: function() {
          this.vendorTitle = (this.$props.category==="builder")?"承建设单位":"监理单位"
          this.selectVendorList(this.$props.category);
      }
    }
  }
};
</script>

<style>

</style>
