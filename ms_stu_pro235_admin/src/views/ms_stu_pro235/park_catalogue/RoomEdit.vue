<template>
  <div id="page-style">
    <el-skeleton style="width: 100%" :loading="dictLoad || initAxiosLoad" animated :throttle="100">
      <template slot="template">
        <div style="padding: 30px; background-color: #fff;">
          <div style="width: 70%; margin: auto;">
            <el-skeleton :rows="16" animated />
          </div>
        </div>
      </template>
      <template>

        <div style=" padding: 30px; background-color: #ffffff;">
          <div style="width: 70%; margin: auto;">
            <el-form class="form-container" ref="formRef" :model="form" :rules="formRules" label-width="140px"
              label-position="right">
              <el-form-item label="所属园区:" prop="parkName">
                <el-select v-model="form.parkName" style="width: 100%;">
                  <!-- <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                      </el-option> -->
                </el-select>
              </el-form-item>
              <el-form-item label="所属楼宇:" prop="buildingName">
                <el-select v-model="form.buildingName" style="width: 100%;">
                  <!-- <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option> -->
                </el-select>
              </el-form-item>
              <el-form-item label="所属楼层:" prop="floorName">
                <el-select v-model="form.floorName" style="width: 100%;">
                  <!-- <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option> -->
                </el-select>
              </el-form-item>
              <el-form-item label="房间名称:" prop="roomName">
                <el-input v-model="form.roomName" size="mini" placeholder="请输入房间名称" style="width: 34%;"></el-input>
              </el-form-item>
              <el-form-item label="租金:" prop="rent">
                <el-input-number v-model="form.rent" size="small" controls-position="right"
                  @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'rent')}"
                  :min="1"></el-input-number>
                <el-select v-model="form.rentUnit" style="width: 15%;">
                  <el-option v-for="item in rentUnitDicts" :key="item.dictValue" :label="item.dictLabel"
                    :value="item.dictValue">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="物业费:" prop="propertyFee">
                <el-input-number v-model="form.propertyFee" size="small" controls-position="right"
                  @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'propertyFee')}"
                  :min="1"></el-input-number>
                <el-select v-model="form.propertyFeeUnit" style="width: 15%;">
                  <el-option v-for="item in propertyFeeUnitDicts" :key="item.dictValue" :label="item.dictLabel"
                    :value="item.dictValue">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="房间面积 (平方米) :" prop="area">
                <el-input-number v-model="form.area" size="small" controls-position="right"
                  @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'area')}" :min="1"
                  style="width: 34%;" placeholder="请输入房间面积"></el-input-number>
              </el-form-item>
              <el-form-item label="房型:" prop="roomType">
                <el-select v-model="form.roomType" style="width: 100%;">
                  <el-option v-for="item in roomTypeDicts" :key="item.dictValue" :label="item.dictLabel"
                    :value="item.dictValue">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="层高(米):" prop="buildingHight">
                <el-input-number v-model="form.buildingHight" size="small" controls-position="right"
                  @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'buildingHight')}"
                  :min="1" style="width: 34%;"></el-input-number>
              </el-form-item>
              <el-form-item label="装修类型:" prop="decorationType">
                <el-select v-model="form.decorationType" style="width: 100%;">
                  <el-option v-for="item in decorationTypeDicts" :key="item.dictValue" :label="item.dictLabel"
                    :value="item.dictValue">
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="上传房间小图">
                <el-upload action=" #" list-type="picture-card" :auto-upload="false" :file-list="fileListArr"
                  :on-change="handleImageUpdate">
                  <i slot="default" class="el-icon-plus plus"></i>
                  <div slot="file" slot-scope="{file}">
                    <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
                    <span class="el-upload-list__item-actions">
                      <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                        <i class="el-icon-zoom-in"></i>
                      </span>
                      <span class="el-upload-list__item-delete" @click="handleRemove(file)">
                        <i class="el-icon-delete"></i>
                      </span>
                    </span>
                  </div>
                </el-upload>
                <span style="color: #8c8c8c;">尺寸建议: 宽160px, 高160px</span>
              </el-form-item>
              <el-form-item label="房间简介 (可选):" prop="intro">
                <el-input v-model="form.intro" type="textarea" :rows="5" show-word-limit resize="none" :maxlength="50"
                  placeholder="请输入房间简介">
                </el-input>
                <span style="font-size: 12px; color:#8c8c8c ;">不要输入超过50个字符</span>
              </el-form-item>
              <el-form-item>
                <div style="padding-left: 200px;">
                  <el-button class="primary-btn" type="primary" :loading="submitLoading"
                    @click="onSubmit">提交</el-button>
                  <el-button class="default-btn" type="default" icon="el-icon-arrow-left" @click="clone">关闭</el-button>
                </div>
              </el-form-item>
            </el-form>

          </div>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </div>


      </template>
    </el-skeleton>

  </div>
</template>
<script>
  // 导入API
  import { getParkSelect, getBuildingSelect, getFloorSelect, inquireRoomDetails } from '@/api/ms_stu_pro235/park_manage/room_edit.js'
  export default {
    data() {
      return {

        // 字典值模块
        rentUnitDicts: [],
        propertyFeeUnitDicts: [],
        roomTypeDicts: [],
        decorationTypeDicts: [],
        // 控制字典dictLoad
        dictLoad: true,

        // 初始化axios模块
        parkSelectArr: [],
        buildingSelectArr: [],
        floorSelectArr: [],
        // 控制axiox初始化是否完成
        initAxiosLoad: true,

        // 控制按钮加载
        submitLoading: false,

        // form
        form: {
          parkName: undefined,
          buildingName: undefined,
          floorName: undefined,
          roomName: undefined,
          rent: undefined,
          rentUnit: '3',
          propertyFee: undefined,
          propertyFeeUnit: '3',
          area: undefined,
          roomType: '0',
          buildingHight: undefined,
          decorationType: '1',
          image: undefined,
          intro: undefined,
        },
        formRules: {
          parkName: [
            {
              required: true, message: '请选择园区', trigger: 'change'
            }
          ],
          buildingName: [
            {
              required: true, message: '请选择楼宇', trigger: 'change'
            }
          ],
          floorName: [
            {
              required: true, message: '请选择楼层', trigger: 'change'
            }
          ],
          roomName: [
            {
              required: true, message: '请输入房间名称', trigger: 'blur'
            }
          ],
          rent: [
            {
              required: true, message: '请输入租金', trigger: 'blur'
            }
          ],
          propertyFee: [
            {
              required: true, message: '请输入物业费', trigger: 'blur'
            }
          ],
          area: [
            {
              required: true, message: '请输入房间面积', trigger: 'blur'
            }
          ],
          roomType: [
            {
              required: true, message: '请选择房型', trigger: 'change'
            }
          ],
          decorationType: [
            {
              required: true, message: '请选择装修类型', trigger: 'change'
            }
          ],
          // identityCardNumber: [
          //   { required: true, message: '身份证号码不能为空', trigger: 'blur' },
          //   {
          //     pattern: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
          //     message: '请输入正确的身份证号码',
          //     trigger: 'blur'
          //   },
          // ],
          // phone: [
          //   { required: true, message: '联系手机号码不能为空', trigger: 'blur' },
          //   {
          //     pattern: /^1[3-9]\d{9}$/,
          //     message: '请输入正确的手机号码',
          //     trigger: 'blur'
          //   },
          // ],

        },
        fileListArr: [],
        dialogImageUrl: '',
        dialogVisible: false,




      }
    },
    computed: {
      getTeantState() {
        return (stateCode) => {
          const { dictLabel } = this.taentStateDict.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },
    },
    created() {
      this.getAllDicts()
      this.getInitAxiosData()
    },
    mounted() {
    },
    methods: {

      // 只有当全部成功时才会去更改骨架屏的load的其中一个,还有初始请求的axios的数据
      getAllDicts() {
        Promise.all([this.getDicts('park_rent_type'), this.getDicts('park_property_unit'), this.getDicts('park_house_type'), this.getDicts('park_decoration_type')]).then(res => {
          //更改骨架屏中的其中一个load
          this.rentUnitDicts = res[0].data
          this.propertyFeeUnitDicts = res[1].data
          this.roomTypeDicts = res[2].data
          this.decorationTypeDicts = res[3].data
          this.dictLoad = false
          console.log(res);

        }).catch(err => {
          console.log(err);
          this.$message.error('请刷新页面,如不行,请稍后再试')

        })
      },

      getInitAxiosData() {
        // 模拟数据 后面接axios时改成promise.all
        // 判断路径中的参数是否为数字类型, 用来区分是 新建还是编辑
        let roomId = this.$route.params.id
        console.log(roomId);
        console.log(!(isNaN(roomId)));
        if (!(isNaN(roomId))) {
          inquireRoomDetails(roomId).then(res => {
            console.log('房间详情:', res);
            this.initAxiosLoad = false
          }).catch(err => {
            console.log(err);
          })
        }
        Promise.all([getParkSelect(), getBuildingSelect(), getFloorSelect()]).then(res => {
          this.parkSelectArr = res[0].data
          this.buildingSelectArr = res[0].data
          this.floorSelectArr = res[0].data
          console.log(roomId);
          if (isNaN(roomId)) this.initAxiosLoad = false
        }).catch(err => {
          console.log(err);
        })

      },

      handleNumInputChange(currentValue, oldValue, key) {
        console.log('currentValue', currentValue);
        console.log('oldValue', oldValue);
        console.log('key', key);

        switch (key) {
          case 'rent':

            break;

          case 'rent':

            break;
        }
      },

      handleRemove(file) {
        const index = this.fileListArr.findIndex(item => item.uid === file.uid)
        console.log(index);
        this.fileListArr.splice(index, 1)

      },

      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },

      handleImageUpdate(file, fileList) {
        this.fileListArr = fileList

      },

      onSubmit() {
        this.$refs['formRef'].validate(valid => {
          if (!valid) return
          this.submitLoading = true
          setTimeout(() => {
            this.$message.success('提交成功')
            this.$router.push('/park_catalogue/room')
          }, 1000);
        })

      },

      clone() {
        this.$router.push('/park_catalogue/room')
      }

    },

  }
</script>

</script>
<style lang="scss" scoped>
  #page-style {


    min-height: calc(100vh - 131px);
    padding: 20px;
    background-color: #f1f2f6;
    box-sizing: border-box;


    .plus {
      position: relative;

      &::after {
        position: absolute;
        bottom: -15px;
        left: 50%;
        transform: translateX(-50%);
        content: '上传';
        display: inline-block;
        width: 40px;
        font-size: 12px;
        color: #8c8c8c;

      }
    }

    /* 更改elementUI的按钮自动聚焦 */
    .primary-btn {
      color: #fff;
      background-color: #168ffa;

      &:hover {
        opacity: 0.8;
      }

      &:active {
        opacity: 0.7;
      }
    }

    .default-btn {
      color: #000;
      background-color: #fff;

      &:hover {
        color: #189dff;
        background-color: #e8f4ff;
      }

      &:active {
        border: 1px solid #189dff;
      }
    }


    .success-style {
      padding: 5px;
      text-align: center;
      color: #70c646;
      background-color: #f0f9eb;
      border: 1px solid #c2e7b0;
    }

    .info-style {
      padding: 5px;
      text-align: center;
      color: #989ba1;
      background-color: #f4f4f5;
      border: 1px solid #d3d4d6;
    }

    .danger-btn {
      color: #fff;
      background-color: #ff4a4f;

      &:hover {
        opacity: 0.8;
      }

      &:active {
        opacity: 0.7;
      }
    }

  }
</style>