<template>
  <div class="">
    <div class="container">
      <div style="background-color: white;padding: 20px;">
        <el-form :disabled="repairFormLock" ref="repairFormRef" :model="repairForm" :rules="repairFormRules"
          label-width="200px">
          <el-form-item label="联系人：" prop="contacts">
            <el-input v-model="repairForm.contacts"></el-input>
          </el-form-item>
          <el-form-item label="联系电话：" prop="contactNumber">
            <el-input v-model="repairForm.contactNumber"></el-input>
          </el-form-item>
          <el-form-item label="上门时间：" prop="visitTime">
            <el-cascader v-model="repairForm.visitTime" :options="visitTimeOpt" :props="{ expandTrigger: 'hover' }"
              @change=""></el-cascader>
          </el-form-item>
          <el-form-item label="报修区域：" prop="repairArea">
            <el-select v-model="repairForm.repairArea" placeholder="请选择">
              <el-option v-for="item in repairAreaOpt" :key="item.dictValue" :label="item.dictLabel"
                :value="item.dictValue">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="描述：" prop="description">
            <el-input type="textarea" :rows="4" v-model="repairForm.description"
              placeholder="请详细描述您遇到的问题，以便我们更好为您解决（200字以内）"></el-input>
          </el-form-item>
          <el-form-item label="上传维修申请图片：" prop="photo">
            <el-upload :on-change="uploadChange" :action="uploadUrl" :headers="headers" :show-file-list="false">
              <el-button size="small">
                <i class="el-icon-plus avatar-uploader-icon"></i>
                <div>上传</div>
              </el-button>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
import { getToken } from '@/utils/auth'

export default {
  name: "",
  data() {
    return {
      repairFormLock: false,
      repairForm: {
        "contacts": undefined,
        "contactNumber": undefined,
        "visitTime": undefined,
        "repairArea": undefined,
        "description": undefined,
        "photo": undefined
      },
      repairFormRules: {
        "contacts": [{ required: true, message: '请输入联系人', trigger: ['blur'] }],
        "contactNumber": [{ required: true, message: '请输入联系电话', trigger: ['blur'] }],
        "visitTime": [{ required: true, message: '请输入入门时间', trigger: ['blur'] }],
        "repairArea": [{ required: true, message: '请输入报修区域', trigger: ['blur'] }],
        // "description":undefined,
        // "photo":undefined
      },
      headers: {
        Authorization: "Bearer " + getToken()
      },
      uploadUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址m
      visitTimeOpt: [],
      repairAreaOpt: []
    }
  },
  created() {
    // 获取字典值
    this.getDicts('park_repair_area').then(res => {
      this.repairAreaOpt = res.data
    })
  },
  methods: {
    // 上传前
    uploadChange(file, fileList) {
      if (file.status == 'ready') {
        // this.avatarTempUrl = URL.createObjectURL(file.raw)
      }
      else if (file.status == 'success') {
        this.categoryForm.materialImage = `http://mashang.eicp.vip:5555/ms_stu_pro232` + file.response.fileName
      }
    },
  }
}
</script>
<style lang="scss" scoped>
.container {
  background-color: #F1F3F5;
  width: 100%;
  height: 100vh;
  padding: 20px;
  box-sizing: border-box;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 80px;
  height: 80px;
  line-height: 80px;
  text-align: center;
}
</style>
