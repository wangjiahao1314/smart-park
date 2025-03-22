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
            <el-form-item class="control-form-item" label="报修单号:" prop="repairBillNumber">
              <el-input v-model="inquireForm.repairBillNumber" size="mini" placeholder='请输入报修单号'
                @keyup.enter.native="handleInquire"></el-input>
            </el-form-item>

            <el-form-item label="工单状态：:" prop="workOrderState">
              <el-radio-group v-model="inquireForm.workOrderState" size="small">
                <el-radio-button label="待分配"></el-radio-button>
                <el-radio-button label="待处理"></el-radio-button>
                <el-radio-button label="已完成"></el-radio-button>
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

              <el-table-column fixed="left" prop="serviceOrderNumber" label="维修单号" width="">
              </el-table-column>
              <el-table-column fixed="left" prop="repairArea" label="报修区域" width="">
              </el-table-column>
              <el-table-column prop="contactPerson" label="联系人" width=""></el-table-column>
              <el-table-column prop="maintenanceWorker" label="维修工人" width="">
              </el-table-column>
              <el-table-column prop="state" label="状态" width="">
                <template #default="{row}">
                  <div>
                    {{getStateLabel(row.state)}}
                  </div>
                </template>
              </el-table-column>

              <el-table-column prop="repairTime" label="报修时间" sortable width="150x"></el-table-column>


              <el-table-column fixed="right" label="操作" width="">
                <template #default="{row}">
                  <div style="display: flex; align-items: center;">

                    <el-button type="text" size="mini">详细</el-button>



                    <div v-if="row.state =='1'">

                      <span style="color: #dfdfdf; padding: 0px 3px;">|</span>

                      <el-button type="text" size="mini" @click="handleDesignate">指派</el-button>

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
          <el-dialog class="dialog-container" title="指派维修员" :visible.sync="dialogDesignateVisible"
            @close='handleDialogCancel'>
            <!-- <el-skeleton style="width: 240px" :loading="loading" animated>
              <template slot="template">

              </template>
              <template>

              </template>
            </el-skeleton> -->

            <div style="width: 70%; margin: auto;" class="flex-justify-center">
              <el-form ref="dialogDesignateFormRef" :inline="true" :model="dialogDesignateForm"
                :rules="dialogDesignateFormRules">
                <el-form-item label="维修员:" prop="maintenanceMan">
                  <el-select v-model="dialogDesignateForm.maintenanceMan" placeholder="请选择维修人员">
                    <el-option v-for="item in maintenanceManList" :key="item.id"
                      :label="getMaintenanceManLabel(item.name,item.phone)" :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogDesignateVisible = false">取 消</el-button>
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

        // 存储工单状态字典值
        workOrderStatusDicts: [],

        // 控制初始化骨架屏 默认打开true
        dictLoad: true,
        initAxiosLoad: true,

        inquireForm: {
          repairBillNumber: undefined,
          workOrderState: "待分配",
        },



        // 控制table的加载状态 true打开,false关闭
        tableLoad: false,

        tableData: [
          {
            id: 1,
            serviceOrderNumber: "1231231",
            repairArea: "供暖系统",
            contactPerson: "汪",
            maintenanceWorker: "张三 （1231231231）",
            state: "0",
            repairTime: "2024-02-20",

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
        dialogDesignateVisible: false,

        // 对话框form
        dialogDesignateForm: {
          maintenanceMan: undefined
        },
        dialogDesignateFormRules: {
          maintenanceMan: [{ required: true, message: '维修人员不能为空', trigger: 'change' }]
        },

        maintenanceManList: [],

      }
    },
    computed: {

      getStateLabel() {
        return (stateCode) => {
          const { dictLabel } = this.workOrderStatusDicts.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },

      // 处理dialog的字符串拼接
      getMaintenanceManLabel() {
        return (name, phone) => {
          return `${name} (${phone.replace(phone.substring(3, 7), 'xxxx')})`
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
        Promise.all([this.getDicts('park_work_order_status')]).then(res => {
          this.workOrderStatusDicts = res[0].data
          console.log('账单状态字典值:', this.workOrderStatusDicts);
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
              serviceOrderNumber: "1231231",
              repairArea: "供暖系统",
              contactPerson: "汪",
              maintenanceWorker: "张三 （1231231231）",
              state: "0",
              repairTime: "2024-02-20",

            },
            {
              id: 2,
              serviceOrderNumber: "1231231",
              repairArea: "供暖222系统",
              contactPerson: "汪",
              maintenanceWorker: "张三 （1231231231）",
              state: "0",
              repairTime: "2024-02-20",

            },
            {
              id: 3,
              serviceOrderNumber: "1231231",
              repairArea: "供暖333系统",
              contactPerson: "汪",
              maintenanceWorker: "张三 （1231231231）",
              state: "1",
              repairTime: "2024-02-20",
            },
          ]

          this.tableData = data
          this.initAxiosLoad = false
        }, 500);
      },


      handleInquire() {
        // 转换工单状态
        console.log('工单状态字典值：', this.workOrderStatusDicts);

        let { dictValue } = this.workOrderStatusDicts.find(item => item.dictLabel == this.inquireForm.workOrderState) || '-'
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
      handleDesignate(row) {
        this.dialogDesignateVisible = true
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
        this.dialogDesignateVisible = false
        this.$refs['dialogDesignateFormRef'].resetFields()
      },

      // 处理dialog确定按钮
      handleDialogSure() {
        this.$refs['dialogDesignateFormRef'].validate(valid => {
          if (!valid) return
          this.dialogDesignateVisible = false
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
        ::v-deep .el-dialog__header {
          border-bottom: 1px solid #f1f1f1;
        }

        ::v-deep .el-dialog__footer {
          padding: 20px;
          border-top: 1px solid #f1f1f1;
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


    }


  }
</style>