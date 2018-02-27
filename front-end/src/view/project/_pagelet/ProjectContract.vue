<template>
  <section>
    <el-dialog title="维护项目合同" :visible.sync="isShow" :close-on-click-modal="false" :before-close="closeDialog" width="600px">
       <el-card>
        <div slot="header" class="clearfix">
          <span>项目合同</span>
          <el-button @click="openAddProjectContract" icon="el-icon-circle-plus" style="float: right; padding: 3px 0" type="text">增加合同</el-button>
        </div>
        <el-table :data="projectContractList" highlight-current-row>
          <el-table-column label="合同" prop="contractName" width="240px"></el-table-column>
          <el-table-column label="供应商" prop="vendorId" :formatter="fmtVendor" width="100px"></el-table-column>
          <el-table-column label="操作" width="160px">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" @click="openEditProjectContract(scope.row)" >编辑</el-button>
              <el-button size="mini" type="danger" @click="delProjectContract(scope.row)" >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-dialog>
    <!--dlgXXEdit-->
    <el-dialog :title="projectContractObj.id?'编辑项目合同':'增加项目合同'" :visible.sync="dlgProjectContractEditVis" width="350px" :close-on-click-modal="false">
      <el-form :model="projectContractObj" :rules="projectContractObjRules" ref="projectContractForm" label-width="100px" size="mini">
        <el-form-item label="合同名称" prop="contractName">
          <el-input type="text" v-model="projectContractObj.contractName" style="width:200px"></el-input>
        </el-form-item>
        <el-form-item label="相关供应商" prop="vendorId">
          <el-select v-model="projectContractObj.vendorId" placeholder="请选择" style="width:200px">
            <el-option v-for="item in vendorList" :key="item.id" :label="item.vendorName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="附件" >
          <el-upload 
            ref="upload" 
            action="any"
            :http-request="updateProjectContract"
            :on-change="onFileChange"
            :on-remove="onFileRemove"
            :auto-upload="false" 
            :file-list="contractFileList">
            <el-button slot="trigger" size="mini">选取合同 ...</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="uploadFile">保存</el-button>
          <el-button @click="dlgProjectContractEditVis = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </section>
</template>

<script>
import {
  SELECT_PROJECT_CONTRACT_LIST,
  UPDATE_PROJECT_CONTRACT,
  UPDATE_PROJECT_CONTRACT_NO_FILE,
  DELETE_PROJECT_CONTRACT,
  SELECT_VENDOR_LIST,
  SELECT_DOCUMENT_LIST,
  DELETE_DOCUMENT
} from "@/config/api";
export default {
  name: "project-contract",
  props: {
    isShow: false,
    projectId: ""
  },
  data() {
    return {
      projectContractObj: {},
      projectContractObjRules: {
        vendorId: [
          {
            required: true,
            message: "请选择合同相关的供应商",
            trigger: "blur,change"
          }
        ],
        contractName: [
          { required: true, message: "请输入合同名称", trigger: "blur" }
        ]
      },
      contractFileList: [],
      selectedFile: null,
      projectContractList: [],
      vendorList: [],
      projectVendorList: [],
      dlgProjectContractEditVis: false
    };
  },
  methods: {
    closeDialog() {
      this.$emit("close");
    },
    selectVendorList(projectId) {
      var _this = this;
      SELECT_VENDOR_LIST({ projectId: projectId }).then(res => {
        _this.vendorList = res;
      });
    },
    selectProjectContractList(projectId) {
      var _this = this;
      SELECT_PROJECT_CONTRACT_LIST({ projectId: projectId }).then(res => {
        _this.projectContractList = res;
        _this.projectVendorList.length = 0;
        for (var i = 0; i < _this.vendorList.length; i++) {
          var _vendor = _this.vendorList[i];
          for (var j = 0; j < _this.projectContractList.length; j++) {
            var _contract = _this.projectContractList[j];
            if (_vendor.id == _contract.vendorId) {
              if (_this.projectVendorList.indexOf(_vendor) == -1)
                _this.projectVendorList.push(_vendor);
            } else break;
          }
        }
      });
    },
    selectDocumentList(projectContractId) {
      var _this = this;
      SELECT_DOCUMENT_LIST({
        belongTo: "contract",
        sourceId: projectContractId
      }).then(res => {
        _this.contractFileList = res;
      });
    },
    openAddProjectContract() {
      this.projectContractObj = Object.assign({
        id: 0,
        projectId: this.$props.projectId,
        vendorId: "",
        contractName: ""
      });
      this.contractFileList = [];
      this.dlgProjectContractEditVis = true;
    },
    openEditProjectContract(row) {
      this.projectContractObj = Object.assign(row);
      //查询当前合同的文档
      this.selectDocumentList(row.id);
      this.dlgProjectContractEditVis = true;
    },
    fmtVendor(row, column, cellValue) {
      for (var i = 0; i < this.vendorList.length; i++) {
        if (this.vendorList[i].id === cellValue) {
          return this.vendorList[i].vendorName;
        }
      }
    },
    updateProjectContract(item) {
      var _this = this;
      this.$refs.projectContractForm.validate(valid => {
        if (valid) {
          var formData = new FormData();
          for (var key in _this.projectContractObj) {
            formData.append(key, _this.projectContractObj[key]);
          }
          formData.append("file", item.file);
          UPDATE_PROJECT_CONTRACT(formData).then(data => {
            _this.dlgProjectContractEditVis = false;
            _this.selectProjectContractList(_this.$props.projectId);
            // TODO 发送更新合同事件，让项目组更新可选成员
            _this.$bus.$emit('update-contract');
          });
        }
      });
    },
    updateProjectContractNoFile() {
      var _this = this;
      this.$refs.projectContractForm.validate(valid => {
        if (valid) {
          UPDATE_PROJECT_CONTRACT_NO_FILE(_this.projectContractObj).then(
            data => {
              _this.dlgProjectContractEditVis = false;
              _this.selectProjectContractList(_this.$props.projectId);
              // TODO 发送更新合同事件，让项目组更新可选成员
            }
          );
        }
      });
    },
    delProjectContract(row) {
      var _this = this;
      _this
        .$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        })
        .then(() => {
          DELETE_PROJECT_CONTRACT({ id: row.id }).then(res => {
            _this.$message({ message: "删除成功", type: "success" });
            _this.selectProjectContractList(row.projectId);
            // TODO 发送更新合同事件，让项目组更新可选成员
          });
        });
    },
    uploadFile() {
      var _uploadFiles = this.$refs.upload.uploadFiles;
      if (_uploadFiles.length == 0) this.updateProjectContractNoFile();
      else {
        for (var i = 0; i < _uploadFiles.length; i++) {
          var _uploadFile = _uploadFiles[i];
          if (_uploadFile.status != "success") {
            this.$refs.upload.submit();
            return;
          }
        }
        this.updateProjectContractNoFile();
      }
    },
    onFileChange(file, fileList) {
      if (fileList.length != 0) {
        //this.projectContractObj.contractName = fileList[0].name;
        this.selectedFile = file;
      } else {
        //this.projectContractObj.contractName = "";
        this.selectedFile = null;
      }
    },
    onFileRemove(file, fileList) {
      var _this = this;
      if (file.status == "success") {
        DELETE_DOCUMENT({ id: file.id }).then(res => {
          _this.$message({ message: "删除成功", type: "success" });
          _this.selectDocumentList(file.sourceId);
        });
      }
      this.selectedFile = null;
    }
  },
  watch: {
    projectId: {
      handler: function() {
        this.selectProjectContractList(this.$props.projectId);
      }
    }
  },
  computed: {},
  mounted() {
    this.selectVendorList();
  }
};
</script>

<style>

</style>
