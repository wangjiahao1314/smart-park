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
          <el-form ref="formRef" style="display: flex;" class="form-container" :inline="true" :model="inquireForm">
            <el-form-item style="flex: 1;" class="control-form-item" label="客户名称:" prop="customerName">
              <el-input v-model="inquireForm.customerName" size="mini" placeholder='请输入客户名称'
                @keyup.enter.native="handleInquire"></el-input>
            </el-form-item>

            <el-form-item style="flex: 1;" class="control-form-item" label="合同编号:" prop="contractNumber">
              <el-input v-model="inquireForm.contractNumber" size="mini" placeholder='请输入合同编号'
                @keyup.enter.native="handleInquire"></el-input>
            </el-form-item>
            <el-form-item style="flex: 1;" class="control-form-item" label="账单月份:" prop="billMonth">
              <el-date-picker style="width: 100%;" placeholder="请选择日期" v-model="inquireForm.billMonth" size="mini"
                type="date"></el-date-picker>
            </el-form-item>

            <el-form-item style="flex: 0.9;" label="账单状态:" prop="billState">
              <el-radio-group v-model="inquireForm.billState" size="small">
                <el-radio-button label="未收"></el-radio-button>
                <el-radio-button label="已收"></el-radio-button>
              </el-radio-group>
              </el-select>
            </el-form-item>

            <el-form-item style="flex: 0.9;" label="是否逾期:" prop="isOverdue">
              <el-radio-group v-model="inquireForm.isOverdue" size="small">
                <el-radio-button label="否"></el-radio-button>
                <el-radio-button label="是"></el-radio-button>
              </el-radio-group>
              </el-select>
            </el-form-item>

            <el-form-item style="flex: 0.6;">
              <el-button class="primary-btn" type="primary" size="mini" @click="handleInquire">查询</el-button>
              <el-button class="default-btn" type="default" size="mini" @click="handleReset">重置</el-button>
            </el-form-item>
          </el-form>


          <div v-if="multipleSelection.length>0" style="padding-bottom: 20px;">
            <el-popconfirm style="margin-right: 20px;" @confirm="handleVerifyCollection('batch')" title="确定批量确认收款吗？"
              placement="top">
              <el-button slot="reference" class="primary-btn" type="primary" size="mini">确认收款</el-button>
            </el-popconfirm>

            <el-popconfirm @confirm="delLogic('batch')" title="是否删除所选中的内容" placement="top">
              <el-button slot="reference" class="danger-btn" type="danger" size="mini"
                icon="el-icon-delete-solid">删除</el-button>
            </el-popconfirm>

          </div>


          <div class="table-container">
            <el-table v-loading="tableLoad" ref="tableDataRef" style="width: 100%" :data="tableData"
              highlight-selection-row :row-key="handleRowKey" @cell-click="handleClickRow"
              @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="60px" reserve-selection>
              </el-table-column>
              <el-table-column fixed="left" prop="paymentPeriod" label="账期" width="200px">
              </el-table-column>
              <el-table-column fixed="left" prop="tenantName" label="租户名称" width="150px">
              </el-table-column>
              <el-table-column prop="contractName" label="合同名称" width="120px"></el-table-column>
              <el-table-column prop="contractNumber" label="合同编号" width="150px">
                <template #default="{row}">
                  <div>
                    <el-button style="color: #68696d;" type="text" @click="handleClickContractNumber(row)">
                      {{row.contractNumber}}
                    </el-button>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="billState" label="账单状态" width="80px">
                <template #default="{row}">
                  <div :class="row.billState == '0'?'success-style':'info-style'">
                    {{getBillStateValue(row.billState)}}
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="isOverdue" label="是否逾期" width="80px"></el-table-column>
              <el-table-column prop="collectionDeadline" label="收款截止日" sortable width="150x"></el-table-column>
              <el-table-column prop="rentReceivable" label="应收租金 (元)" width="100px"></el-table-column>
              <el-table-column prop="deposit" label="押金 (元)" width="100px"></el-table-column>
              <el-table-column prop="returnFee" label="退费 (元)" width="100px"></el-table-column>
              <el-table-column prop="managementFee" label="物业管理费 (元)" width="120px"></el-table-column>
              <el-table-column prop="totalReceivable" label="合计应收 (元)" width="120px"></el-table-column>
              <el-table-column prop="remark" label="备注" width="100px" show-overflow-tooltip></el-table-column>

              <el-table-column fixed="right" label="操作" width="120px">
                <template #default="{row}">
                  <div style="display: flex; align-items: center;">

                    <div v-if="row.billState =='1'">
                      <el-popconfirm @confirm="handleVerifyCollection('single',row)" title="确认要确认收款吗？" placement="top">
                        <el-button slot="reference" type="text" size="mini">确认收款</el-button>

                      </el-popconfirm>
                      <span style="color: #dfdfdf; padding: 0px 3px;">|</span>
                    </div>

                    <el-popconfirm @confirm="delLogic('single',row)" title="是否删除当前行的内容" placement="top">
                      <el-button slot="reference" type="text" size="mini">删除</el-button>
                    </el-popconfirm>


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

        // 存储账单状态字典值
        billStateDicts: [],

        // 控制初始化骨架屏 默认打开true
        dictLoad: true,
        initAxiosLoad: true,

        inquireForm: {
          customerName: undefined,
          contractNumber: undefined,
          billMonth: undefined,
          contractState: undefined,
          billState: undefined,
          isOverdue: undefined,
        },



        // 控制table的加载状态 true打开,false关闭
        tableLoad: false,

        tableData: [
          {
            // id: 1,
            // paymentPeriod: "2024-04-03 ~ 2024-05-01",
            // tenantName: "义乌老板",
            // contractName: "测试1231",
            // contractNumber: "1241232131",
            // billState: "0",
            // isOverdue: "0",
            // collectionDeadline: "2024-03-20",
            // rentReceivable: '5800',
            // deposit: '6000',
            // returnFee: '0',
            // managementFee: '580',
            // totalReceivable: '12380',
            // remark: '',
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


      }
    },
    computed: {

      getBillStateValue() {
        return (stateCode) => {
          const { dictLabel } = this.billStateDicts.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },

      hiddenPhone() {
        return (rowPhone) => {
          return rowPhone.replace(rowPhone.substring(3, 7), "xxxx");
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
        Promise.all([this.getDicts('park_bill_status')]).then(res => {
          this.billStateDicts = res[0].data
          console.log('账单状态字典值:', this.billStateDicts);
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
              paymentPeriod: "2024-04-03 ~ 2024-05-01",
              tenantName: "义乌老板",
              contractName: "测试1231",
              contractNumber: "1241232131",
              billState: "0",
              isOverdue: "0",
              collectionDeadline: "2024-02-20",
              rentReceivable: '5800',
              deposit: '6000',
              returnFee: '0',
              managementFee: '580',
              totalReceivable: '12380',
              remark: '',
            },
            {
              id: 2,
              paymentPeriod: "2024-04-03 ~ 2024-05-01",
              tenantName: "义乌老12板",
              contractName: "测试121231",
              contractNumber: "124121232131",
              billState: "1",
              isOverdue: "1",
              collectionDeadline: "2024-01-20",
              rentReceivable: '5800',
              deposit: '6000',
              returnFee: '0',
              managementFee: '580',
              totalReceivable: '12380',
              remark: '',
            },
          ]

          this.tableData = data
          this.initAxiosLoad = false
        }, 500);
      },

      leastInputOne(rule, value, callback) {


      },



      handleInquire() {
        let isPass = []
        for (let attr in this.inquireForm) {
          isPass.push(Boolean(this.inquireForm[attr]))
        }
        if (!isPass.includes(true)) return this.$message.warning('请至少输入一个查询数据')
        // 转换账单状态 是否逾期数据
        let billState = this.inquireForm.billState == '已收' ? '0' : this.inquireForm.billState == '未收' ? '1' : undefined
        let isOverdue = this.inquireForm.isOverdue == '否' ? '0' : this.inquireForm.isOverdue == '是' ? '1' : undefined

        console.log('账单状态', billState);
        console.log('是否逾期', isOverdue);

        // 发送查询axios请求
        setTimeout(() => {
          console.log('发送查询请求');
        }, 1000)

      },

      // inquireForm重置
      handleReset() {
        this.$refs['formRef'].resetFields()
      },

      // 处理给每个table的行设置唯一key
      handleRowKey(row) {
        return row.id
      },

      // 处理点击当前行逻辑
      handleClickRow(row, column, cell, event) {

        if (column.label == '操作' || column.label == '合同编号') return

        let isExist = this.multipleSelection.some(item => item.id == row.id)
        // 存在则移出
        if (isExist) return this.$refs['tableDataRef'].toggleRowSelection(row, false)
        // 不存在则添加
        this.$refs['tableDataRef'].toggleRowSelection(row, true)
      },

      // 存储勾选的数据
      handleSelectionChange(selection) {
        this.multipleSelection = selection
        console.log(this.multipleSelection);
      },



      // 处理表格内的签订按钮
      handleSigned(isFlot, row) {
        if (isFlot == 'batch') {
          if (!(this.multipleSelection.length > 0)) return this.$message.error('请先勾选要签订的合同')
        }
        this.tableLoad = true
        switch (isFlot) {
          case 'batch':
            let requestData = this.multipleSelection.map(item => item.id)
            console.log(requestData);
            // 发起接口
            setTimeout(() => {

              // 刷新页面
              this.tableLoad = false

            }, 500);
            break;
          case 'sign':
            console.log(row.id);

            // 发起接口
            setTimeout(() => {

              // 刷新页面
              this.tableLoad = false

            }, 500);
            break;
        }

      },

      // 处理点击确认收款逻辑
      handleVerifyCollection(key, row) {
        // if (key == 'batch') {
        //   if (!this.multipleSelection.length > 0) return this.$message.warning('请先选择需要批量删除的逻辑')
        // }
        this.tableLoad = true
        let request = {}
        switch (key) {
          case 'batch':
            request = this.multipleSelection.map(item => {
              return item.id
            })
            console.log('request', request);
            // 发送批量确认收款接口 考虑使用async
            setTimeout(() => {
              this.tableLoad = false

            }, 400);
            break;

          case 'single':
            request = {
              id: row.id
            }
            console.log('request', request);
            // 发送单个确认收款接口 考虑使用async 转化成同步执行
            setTimeout(() => {
              this.tableLoad = false

            }, 400);
            break;
        }
        this.$message.success('确认收款成功')
        // this.tableLoad = false

      },


      // 共用一个删除逻辑  不确实是否可行 试一下
      delLogic(isFlot, row) {
        // if (isFlot == 'batch') {
        //   if (!(this.multipleSelection.length > 0)) return this.$message.error('请先勾选要确认收款的合同')
        // }
        this.tableLoad = true
        let request = {}
        switch (isFlot) {
          case 'batch':
            console.log(isFlot);

            request = this.multipleSelection.map(item => {
              return item.id
            })
            console.log('request', request);

            // 调用axiso接口 删除
            setTimeout(() => {
              this.$message.success('删除成功')
              this.tableLoad = false
            }, 1000)
            break;

          case 'single':
            console.log(isFlot);
            request = {
              id: row.id
            }
            console.log('request', request);

            // 调用axiso接口 删除
            setTimeout(() => {
              this.$message.success('删除成功')
              this.tableLoad = false
            }, 1000)
            break;
        }


      },

      // 处理点击合同编号跳转
      handleClickContractNumber(row) {
        this.$router.push({
          path: '/assets/contractDetails/contractDetails',
          query: {
            id: row.id
          }
        })
      },

      // 处理table编辑按钮
      handleEdit(row) {
        console.log(row.id);
        this.$router.push({
          path: `/assets/contractEdit/contractEdit`,
          query: {
            id: row.id
          }
        })

      },

      // 处理合同变更按钮
      handleContractAlter(row) {
        this.$router.push({
          path: `/assets/contractEdit/contractAlter`,
          query: {
            id: row.id
          }
        })
      },

      // 处理合同打印按钮
      handleContractPrint(row) {
        this.$router.push({
          path: `/assets/contractDetails/contractPrint`,
          query: {
            id: row.id
          }
        })
      },

      // 处理合同退租按钮
      handleSurrenderTenancyBtn(row) {
        this.$router.push(`/assets/surrenderTenancyEdit/${row.id}`)
      },

      // 处理合同预览按钮
      handlePreviewBtn(row) {
        this.$router.push({
          path: `/assets/contractDetails/contractDetails`,
          query: {
            id: row.id
          }
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

      /* 添加边框 */
      ::v-deep .el-dialog__header {
        border-bottom: 1px solid #dfdfdf;
      }

      ::v-deep .el-dialog__footer {

        border-top: 1px solid #dfdfdf;
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


  }
</style>