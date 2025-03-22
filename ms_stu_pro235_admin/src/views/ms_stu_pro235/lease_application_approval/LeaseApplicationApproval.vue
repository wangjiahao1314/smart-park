<template>
  <div id="page-style">

    <el-skeleton style="width: 100%" :loading="dictLoad ||initAxiosLoad" animated>
      <template slot="template">
        <div style=" padding: 20px; background-color: #fff;">
          <div style="padding: 10px 0px;">
            <el-skeleton-item variant="h2" />
          </div>

          <el-skeleton-item style="width: 100%; height: 470px;" variant="rect" />
        </div>
      </template>
      <template>
        <div class="building-container" style="padding: 20px; background-color: #fff;">
          <el-form ref="formRef" class="form-container" :inline="true" :model="inquireForm">
            <el-form-item class="control-form-item" label="申请人:" prop="applicant">
              <el-input v-model="inquireForm.applicant" size="mini" placeholder='请输入申请人'
                @keyup.enter.native="handleInquire"></el-input>
            </el-form-item>

            <el-form-item class="control-form-item" label="联系电话:" prop="phone">
              <el-input v-model="inquireForm.phone" size="mini" placeholder='请输入联系电话'
                @keyup.enter.native="handleInquire"></el-input>
            </el-form-item>

            <el-form-item label="租赁状态：:" prop="leaseState">
              <el-radio-group v-model="inquireForm.leaseState" size="small">
                <el-radio-button label="待审批"></el-radio-button>
                <el-radio-button label="审批通过"></el-radio-button>
                <el-radio-button label="已拒绝"></el-radio-button>
                <el-radio-button label="已取消"></el-radio-button>
              </el-radio-group>
              </el-select>
            </el-form-item>



            <el-form-item style="padding-left: 20px;">
              <el-button class="primary-btn" type="primary" size="mini" @click="handleInquire">查询</el-button>
              <el-button class="default-btn" type="default" size="mini" @click="handleReset">重置</el-button>
            </el-form-item>
          </el-form>


          <div class="table-container">
            <el-table v-loading="tableLoad" ref="tableDataRef" style="width: 100%" :data="tableData">

              <el-table-column prop="tenantry" label="租户" min-width="8%">
              </el-table-column>
              <el-table-column prop="ApplyPark" label="申请园区" min-width="10%">
              </el-table-column>
              <el-table-column prop="Applybuilding" label="申请楼宇" min-width="10%">
              </el-table-column>
              <el-table-column prop="Applyfloor" label="申请楼层" min-width="10%">
              </el-table-column>
              <el-table-column prop="leaseStart" label="租赁状态" min-width="10%">
                <template #default="{row}">
                  <div :class="row.leaseState == '0'?'success-style':'info-style'">
                    {{getLeaseState(row.leaseState)}}
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="phone" label="联系电话" min-width="10%"></el-table-column>
              <el-table-column prop="applyStartTime" label="申请租赁开始时间" min-width="12%">
              </el-table-column>
              <el-table-column prop="applyEndTime" label="申请租赁结束时间" min-width="12%">
              </el-table-column>
              <el-table-column prop="applyState" label="审批状态" min-width="12%">
                <template #default="{row}">
                  <div :class="customStyleFields[row.applyState]">
                    {{getApplyState(row.applyState)}}
                  </div>
                </template>
              </el-table-column>



              <el-table-column fixed="right" label="操作" min-width="10%">
                <template #default="{row}">
                  <div style="display: flex; align-items: center;">

                    <el-button type="text" size="mini">房屋详情</el-button>



                    <div v-if="row.applyState =='0'">

                      <span style="color: #dfdfdf; padding: 0px 3px;">|</span>

                      <el-button type="text" size="mini" @click="handleRoomApply">审批</el-button>

                    </div>


                  </div>
                </template>
              </el-table-column>

              <template slot=" empty">
                <el-empty :image-size="Number(80)">
                  <template slot="image">
                    <img src="@/assets/images/envelope.png" alt="">
                  </template>
                </el-empty>
              </template>
            </el-table>

            <div style="display: flex; justify-content: end;  font-size: 13px; color: #606266;">
              <span style="line-height: 32px;">显示从 1 到 {{pagination.pageSize}} , 总 {{total}} 条记录</span>
              <el-pagination background @current-change="handlePaginationCurrentChange"
                :current-page="pagination.pageNum" :page-sizes="[10, 15]" :page-size="pagination.pageSize"
                layout="total,prev, pager, next,slot" :total="total">

                <el-select size='mini' v-model="pagination.pageSize" @change="handlePaginationSizeChange">
                  <el-option v-for="(item,index) in pageSizeArr" :key="index" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>

              </el-pagination>
            </div>
          </div>


          <!-- 弹窗模块 -->
          <el-dialog class="dialog-container" title="审批" :visible.sync="dialogApplyVisible" @close='handleDialogCancel'>
            <!-- <el-skeleton style="width: 240px" :loading="loading" animated>
              <template slot="template">

              </template>
              <template>

              </template>
            </el-skeleton> -->

            <div style="width: 80%; margin: auto;" class="flex-justify-center">
              <el-form ref="dialogApplyFormRef" style="width: 100%;" :model="dialogApplyForm"
                :rules="dialogApplyFormRules">
                <el-form-item label="审批结果:" prop="approvalResult">
                  <el-select style="width: 70%;" v-model="dialogApplyForm.approvalResult" placeholder="请选择审批结果">
                    <el-option v-for="item in applyStatusDicts" :key="item.dictValue" :label="item.dictLabel"
                      :value="item.dictValue">
                    </el-option>
                  </el-select>
                </el-form-item>

                <el-form-item label="审批意见:" prop="approvalOpinion">
                  <el-input style="width: 70%;" type="textarea" :rows="4" placeholder="请输入审批意见"
                    v-model="dialogApplyForm.approvalOpinion">
                  </el-input>
                </el-form-item>
              </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogApplyVisible = false">取 消</el-button>
              <el-button type="primary" @click="handleDialogSure">确 定</el-button>
            </span>
          </el-dialog>

        </div>
      </template>
    </el-skeleton>


  </div>
</template>
<script>
  // 导入封装的方法
  import { getSubmitDate } from '@/utils/ms_stu_pro235/public.js'
  export default {

    data() {
      return {

        // 根据设置的字段去查找对应的样式 根据字典设置
        customStyleFields: {
          '0': 'warning-style',
          '1': 'success-style',
          '2': 'danger-style',
          '3': 'info-style',
        },

        // 存储 审批状态字典值 租赁状态字典值
        leaseStatusDicts: [],
        applyStatusDicts: [],

        // 控制初始化骨架屏 默认打开true
        dictLoad: true,
        initAxiosLoad: true,

        inquireForm: {
          applicant: undefined,
          phone: undefined,
          leaseState: undefined,
        },



        // 控制table的加载状态 true打开,false关闭
        tableLoad: false,

        tableData: [
          {
            // id: 1,
            // tenantry: "汪",
            // ApplyPark: "阳光园区",
            // Applybuilding: "华建大厦",
            // Applyfloor: "华建大厦",
            // leaseState: "0",
            // phone: "12312312",
            // applyStartTime: "2022-05=30",
            // applyEndTime: "0",
            // applyState: "0",

          },

        ],
        multipleSelection: [],

        pagination: {
          pageNum: 1,
          pageSize: 10
        },

        pageSizeArr: [
          { label: '10 条/页', value: 10 },
          { label: '15 条/页', value: 15 },
        ],

        total: 15,

        // 对话框模块
        dialogApplyVisible: false,

        // 对话框form
        dialogApplyForm: {
          approvalResult: undefined,
          approvalOpinion: undefined,
        },
        dialogApplyFormRules: {
          approvalResult: [{ required: true, message: '审批结果不能为空', trigger: 'change' }],
          approvalOpinion: [{ required: true, message: '审批意见不能为空', trigger: 'blur' }]
        },

        maintenanceManList: [],

      }
    },
    computed: {
      // 得到租赁状态字典值
      getLeaseState() {
        return (stateCode) => {
          const { dictLabel } = this.leaseStatusDicts.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },

      // 得到审批状态字典值
      getApplyState() {
        return (stateCode) => {
          const { dictLabel } = this.applyStatusDicts.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },

    },
    created() {
      this.getAllDicts()
      this.getAxiosData()
    },
    mounted() {
    },
    methods: {

      getAllDicts() {
        Promise.all([this.getDicts('park_rental_status'), this.getDicts('park_approval_status')]).then(res => {
          this.leaseStatusDicts = res[0].data
          this.applyStatusDicts = res[1].data
          console.log('租赁状态字典值:', this.leaseStatusDicts);
          console.log('审批状态字典值:', this.applyStatusDicts);
          this.dictLoad = false
        }).catch(err => {
          console.log(err);

        })
      },

      getAxiosData() {
        setTimeout(() => {
          const data = [
            {
              id: 1,
              tenantry: "汪",
              ApplyPark: "阳光园区",
              Applybuilding: "华建大厦",
              Applyfloor: "华建大厦",
              leaseState: "0",
              phone: "12312312",
              applyStartTime: "2022-05=30",
              applyEndTime: "0",
              applyState: "0",
            },
            {
              id: 2,
              tenantry: "汪",
              ApplyPark: "阳光园区",
              Applybuilding: "华建大厦",
              Applyfloor: "华建大厦",
              leaseState: "1",
              phone: "12312312",
              applyStartTime: "2022-05=30",
              applyEndTime: "2023-05-30",
              applyState: "1",
            },
            {
              id: 3,
              tenantry: "汪",
              ApplyPark: "阳光园区",
              Applybuilding: "华建大厦",
              Applyfloor: "华建大厦",
              leaseState: "0",
              phone: "12312312",
              applyStartTime: "2022-05=30",
              applyEndTime: "0",
              applyState: "2",
            },
            {
              id: 4,
              tenantry: "汪",
              ApplyPark: "阳光园区",
              Applybuilding: "华建大厦",
              Applyfloor: "华建大厦",
              leaseState: "0",
              phone: "12312312",
              applyStartTime: "2022-05=30",
              applyEndTime: "0",
              applyState: "3",
            },
          ]

          this.tableData = data
          this.initAxiosLoad = false
        }, 500);
      },


      handleInquire() {
        let isPass = []
        for (let attr in this.inquireForm) {
          isPass.push(Boolean(this.inquireForm[attr]))
        }
        if (!isPass.includes(true)) return this.$message.warning('请至少输入一个查询数据')

        // 转换工单状态
        console.log('租赁状态字典值：', this.leaseStatusDicts);

        let { dictValue } = this.leaseStatusDicts.find(item => item.dictLabel == this.inquireForm.leaseState) || '-'
        console.log('dictValue', dictValue);
        // 解构出来做合平处理
        const { repairBillNumber } = this.inquireForm

        // 发送查询axios请求
        setTimeout(() => {
          console.log('发送查询请求');
        }, 1000)

      },

      // inquireForm重置
      handleReset() {
        this.$refs['formRef'].resetFields()
      },

      // 处理table内的 指派按钮
      handleRoomApply(row) {
        this.dialogApplyVisible = true
        // 发起接口
        setTimeout(() => {
          this.maintenanceManList = [
            {
              id: 1,
              name: 'zhangsan',
              phone: '1231231'
            },
            {
              id: 2,
              name: 'zh214312gsan',
              phone: '12231231231'
            },
          ]
        }, 500);
      },

      // 处理dialog的取消按钮
      handleDialogCancel() {
        this.dialogApplyVisible = false
        this.$refs['dialogApplyFormRef'].resetFields()
      },

      // 处理dialog确定按钮
      handleDialogSure() {
        this.$refs['dialogApplyFormRef'].validate(valid => {
          if (!valid) return
          this.dialogApplyVisible = false
          this.tableLoad = true
          // 发送选定维修人员  要求顺序执行 考虑使用async
          setTimeout(() => {

            // 再发送刷新表格接口

            this.tableLoad = false
          }, 500);
        })

      },


      handlePaginationCurrentChange(value) {
        this.pagination.pageNum = value
        console.log(this.pagination);

      },

      // 处理自定义的select
      handlePaginationSizeChange(value) {
        this.pagination.pageSize = value
        console.log(this.pagination);

      },



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



    .building-container {

      .form-container {
        ::v-deep .control-form-item .el-form-item__content {
          width: 64%;
        }
      }

      .table-container {

        ::v-deep .selection-row>td {
          background-color: #f5f7fa;
        }

      }

      .dialog-container {
        width: 60%;
        margin: 15px auto 0px;

        ::v-deep .el-dialog__header {
          border-bottom: 1px solid #f1f1f1;
        }

        ::v-deep .el-dialog__footer {
          padding: 20px;
          border-top: 1px solid #f1f1f1;
        }

        ::v-deep .el-form-item__error {
          padding-left: 83px;
        }
      }

      ::v-deep .el-table--medium {
        margin-bottom: 10px;
      }

      /* 缩小空状态的字体 */
      ::v-deep .el-empty__description {
        margin: 0;
        line-height: 14px;
      }

      /* 改变勾选的背景颜色 */
      ::v-deep .selection-row>td {
        background-color: #f5f7fa !important;
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

      /* 租赁状态样式 */
      .success-style {
        width: 80px;
        padding: 5px;
        text-align: center;
        color: #70c646;
        background-color: #f0f9eb;
        border: 1px solid #c2e7b0;
      }

      .warning-style {
        width: 80px;
        padding: 5px;
        text-align: center;
        color: #ffd15f;
        background-color: #fffaed;
        border: 1px solid #fff5dc;
      }

      .danger-style {
        width: 80px;
        padding: 5px;
        text-align: center;
        color: #ff8282;
        background-color: #fff2f2;
        border: 1px solid #ffe7e7;
      }

      .info-style {
        width: 80px;
        padding: 5px;
        text-align: center;
        color: #989ba1;
        background-color: #f4f4f5;
        border: 1px solid #d3d4d6;
      }


    }


  }
</style>