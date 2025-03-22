<template>
  <div id="page-style">
    <div class="surrender-tenancy-container">
      <el-skeleton style="width: 100%;" :loading="dictLoad || initAxiosLoad" animated>
        <template slot="template">

          <div style="padding: 14px;">
            <el-skeleton-item variant="h3" style="width: 100%; margin-bottom: 20px;" />

            <el-skeleton :rows="12" animated />
          </div>
        </template>
        <template>

          <el-divider content-position="left">基本信息</el-divider>
          <div class="common-content-container">
            <el-descriptions :column="3" size="medium">

              <el-descriptions-item label="合同编号">
                <span style="color:#27a7e7 ;">709-1</span>
              </el-descriptions-item>
              <el-descriptions-item label="合同名称">31231231</el-descriptions-item>
              <el-descriptions-item label="租户信息">21312</el-descriptions-item>
            </el-descriptions>
          </div>

          <el-divider content-position="left">租赁场地</el-divider>

          <div class="common-content-container">

            <el-table :data="tableData" style="width: 100%">

              <el-table-column prop="parkName" label="所属园区" min-width="10%" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="buildingName" label="所属楼宇" min-width="10%" show-overflow-tooltip></el-table-column>
              <el-table-column prop="floorName" label="所属楼层" min-width="10%" show-overflow-tooltip></el-table-column>
              <el-table-column prop="roomName" label="房间名称" min-width="10%" show-overflow-tooltip></el-table-column>
              <el-table-column prop="area" label="收租面积 (平方米)" min-width="10%" show-overflow-tooltip></el-table-column>

              <template slot=" empty">
                <el-empty :image-size="Number(80)">
                  <template slot="image">
                    <img src="@/assets/images/envelope.png" alt="">
                  </template>
                </el-empty>
              </template>
            </el-table>

          </div>

          <el-divider content-position="left">退租信息</el-divider>

          <div style="width: 50%; margin: auto; padding-top: 20px;">
            <el-form ref="formRef" :model="form" :rules="formRules" label-width="100px">
              <el-form-item label="退租日期" prop="date">
                <el-date-picker placeholder="请选择退租日期" v-model="form.date" type="date"
                  :picker-options="handlePickerOptions()" style="width: 100%;"
                  @change="handleDatePickerChange"></el-date-picker>
              </el-form-item>

              <el-form-item label="退租原因" prop="reason">
                <el-select style="width: 100%;" v-model="form.reason" placeholder="请选择退租原因">
                  <el-option v-for="(item,index) in surrenderTenancyReasonDicts" :key="index" :label="item.dictLabel"
                    :value="item.dictValue"></el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="应收费用:" prop="receivableFee">

                <el-input v-model.number="form.receivableFee"
                  @change="(value)=>handleInputChange(value,'receivableFee')">
                  <template #append>
                    <div>
                      <span>元</span>
                    </div>
                  </template>
                </el-input>
              </el-form-item>

              <el-form-item label="应退费用:" prop="shouldReturnedFee">
                <el-input v-model.number="form.shouldReturnedFee"
                  @change="(value)=>handleInputChange(value,'shouldReturnedFee')">
                  <template #append>
                    <div>
                      <span>元</span>
                    </div>
                  </template>
                </el-input>
              </el-form-item>

              <el-form-item label="费用合计:" prop="feeTotal">
                <el-input :value="form.feeTotal" disabled>
                  <template #append>
                    <div>
                      <span>元</span>
                    </div>
                  </template>
                </el-input>
                <span style="font-size: 12px; color: #c8c8c8;">正数为应收费用 -- 负数为应退费用</span>
              </el-form-item>

              <el-form-item label="备注 (可选) :" prop="remark">
                <el-input type="textarea" v-model="form.remark"></el-input>
              </el-form-item>
            </el-form>
          </div>



          <div class="flex-justify-center">
            <el-button style="margin-right: 20px;" class="default-btn" type="default"
              @click="$router.push('/assets/contract')">返回</el-button>
            <el-popconfirm title="确定提交 '退租' 信息吗?" @confirm="handleSubmit">
              <el-button class="primary-btn" type="primary" slot="reference">提交</el-button>
            </el-popconfirm>

          </div>


        </template>
      </el-skeleton>

    </div>
  </div>
</template>
<script>
  // 导入封装的方法
  import { getSubmitDate } from '@/utils/ms_stu_pro235/public.js'
  export default {
    data() {
      return {
        // 页面标题
        title: '',

        // 骨架屏 控制初始化加载
        dictLoad: true,
        initAxiosLoad: true,


        // 存储 退租原因 字典值
        surrenderTenancyReasonDicts: [],




        tableData: [
          // {
          //   id: 1,
          //   roomName: "帝豪",
          //   parkName: "明诚广场",
          //   buildingName: "华建大厦",
          //   floorName: "23",
          //   area: 170,
          // }
        ],

        form: {
          date: undefined,
          reason: undefined,
          receivableFee: undefined,
          shouldReturnedFee: undefined,
          feeTotal: undefined,
          remark: undefined,
        },

        formRules: {
          date: [
            {
              required: true, message: '请选择退租日期', trigger: 'blur',
            }
          ],
          reason: [
            {
              required: true, message: '请选择退租原因', trigger: 'change',
            }
          ],
          receivableFee: [
            {
              required: true, message: '请输入应收费用', trigger: 'blur',
            }
          ],
          shouldReturnedFee: [
            {
              required: true, message: '请输入应退费用', trigger: 'blur',
            }
          ],
          feeTotal: [
            {
              required: true, message: '请输入费用合计', trigger: 'blur',
            }
          ],

        },


      }
    },
    computed: {
      // getSurrenderTenancyReason() {
      //   return (stateCode) => {
      //     const { dictLabel } = this.surrenderTenancyReasonDicts.find(item => item.dictValue == stateCode) || {}
      //     return dictLabel || '-'
      //   }
      // },

    },

    watch: {
      'form.receivableFee': {
        handler(newValue, oldValue) {
          if (newValue == undefined || this.form.shouldReturnedFee == undefined) return
          this.form.feeTotal = newValue - this.form.shouldReturnedFee
        }
      },
      'form.shouldReturnedFee': {
        handler(newValue, oldValue) {
          if (newValue == undefined || this.form.receivableFee == undefined) return
          this.form.feeTotal = this.form.receivableFee - newValue
        }
      },

    },
    created() {
      console.log(this.$route.params);
      this.getAllDicts()
      this.getInitAxiosData()
    },
    methods: {
      getAllDicts() {
        Promise.all([this.getDicts('park_rental_reason')]).then(res => {

          this.surrenderTenancyReasonDicts = res[0].data

          console.log(res);

          //更改骨架屏中的其中一个load
          this.dictLoad = false
        }).catch(err => {
          console.log(err);
          this.$message.error('请刷新页面,如不行,请稍后再试')

        })
      },

      getInitAxiosData() {
        // 模拟数据 后面接axios时改成promise.all 
        setTimeout(() => {
          // 拿到表格数据
          const data = [
            {
              id: 1,
              roomName: "帝豪",
              parkName: "明诚广场",
              buildingName: "华建大厦",
              floorName: "23",
              area: 170,
            }
          ]
          this.tableData = data

          // 拿到账单信息导入 表单数据
          this.form.receivableFee = 1123
          this.form.shouldReturnedFee = 0
          this.form.feeTotal = this.form.shouldReturnedFee - this.form.receivableFee

          // 控制初始化骨架屏
          this.initAxiosLoad = false

        }, 1000);
      },

      // 处理退租日期选择变化逻辑
      handleDatePickerChange(value) {
        let now = new Date()
        this.form.date = now.getDate() == value.getDate() ? now : value
        console.log(getSubmitDate(this.form.date));

      },

      // 处理date选择器禁用逻辑
      handlePickerOptions() {
        return {
          disabledDate: (time) => {

            return time.getTime() < Date.now() - 3600 * 1000 * 24;

          },
        }
      },


      // 处理inputChange逻辑
      handleInputChange(value, dataFormAttr) {
        this.form[dataFormAttr] = parseInt(value)
      },


      // 处理提交按钮
      handleSubmit() {
        this.$refs['formRef'].validate(valid => {
          if (!valid) return
          this.submitLoading = true
          // 发送提交接口
          setTimeout(() => {

            this.submitLoading = false
            this.$router.push('/assets/contract')
          }, 500);
        })

      },



    }
  }
</script>
<style scoped lang="scss">
  #page-style {
    /* display: flex;
    flex-direction: column;
    justify-content: space-around; */

    min-height: calc(100vh - 131px);
    padding: 20px;
    background-color: #f1f2f6;
    box-sizing: border-box;

    .surrender-tenancy-container {
      padding: 20px 40px;
      background-color: #ffffff;

      ::v-deep .el-input__prefix {
        display: none;
      }

      ::v-deep .el-date-editor {
        position: relative;

        &::after {
          position: absolute;
          top: 50%;
          transform: translateY(-50%);
          right: 10px;
          /* z-index: 2; */
          content: '';
          width: 20px;
          height: 20px;
          background-image: url('~@/assets/images/date-icon.png');
          background-size: cover;
        }
      }

      .common-content-container {
        width: 85%;
        margin: auto;
        padding: 30px 10px;

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
    }


  }
</style>