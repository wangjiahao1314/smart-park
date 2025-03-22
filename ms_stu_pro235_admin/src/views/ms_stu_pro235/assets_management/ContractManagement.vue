<template>
  <div id="page-style">

    <el-skeleton style="width: 100%" :loading="dictLoad ||initAxiosLoad" animated>
      <template slot="template">
        <div style=" padding: 20px; background-color: #fff;">
          <el-skeleton-item variant="h2" />
          <div style="padding: 20px 0px;">
            <el-skeleton-item variant="button" />
          </div>

          <el-skeleton-item style="width: 100%; height: 470px;" variant="rect" />
        </div>
      </template>
      <template>
        <div class="building-container" style="padding: 20px; background-color: #fff;">
          <el-form ref="formRef" :inline="true" :model="inquireForm" :rules="inquireFormRules">
            <el-form-item label="合同名称:" style="margin-right: 30px;" prop="contractName">
              <el-input v-model="inquireForm.contractName" size="mini" placeholder='请输入合同名称'
                @keyup.enter.native="handleInquire"></el-input>
            </el-form-item>

            <el-form-item label="合同编号:" style="margin-right: 30px;" prop="contractNumber">
              <el-input v-model="inquireForm.contractNumber" size="mini" placeholder='请输入合同编号'
                @keyup.enter.native="handleInquire"></el-input>
            </el-form-item>
            <el-form-item label="客户名称:" style="margin-right: 30px;" prop="customerName">
              <el-input v-model="inquireForm.customerName" size="mini" placeholder='请输入客户名称'
                @keyup.enter.native="handleInquire"></el-input>
            </el-form-item>

            <el-form-item label="合同状态:" prop="contractState">
              <el-select v-model="inquireForm.contractState" size="mini" placeholder="请选择合同状态" style="width: 130px;">
                <el-option v-for="item in contractStateDicts" :key="item.dictValue" :label="item.dictLabel"
                  :value="item.dictValue">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button class="primary-btn" type="primary" size="mini" @click="handleInquire">查询</el-button>
              <el-button class="default-btn" type="default" size="mini" @click="handleReset">重置</el-button>
            </el-form-item>
          </el-form>

          <div style="padding-bottom: 20px;">
            <el-button class="primary-btn" type="primary" size="mini" icon="el-icon-plus"
              @click="handleAdd">新建</el-button>

            <el-popconfirm v-if="tableData.length>0" @confirm="handleSigned('batch')" title="是否签订所选中的内容" placement="top"
              style="margin: 0px 20px;">
              <el-button slot="reference" class="primary-btn" type="primary" size="mini">签订</el-button>
            </el-popconfirm>

            <el-popconfirm v-if="tableData.length>0" @confirm="delLogic('batch')" title="是否删除所选中的内容" placement="top">
              <el-button slot="reference" class="danger-btn" type="danger" size="mini"
                icon="el-icon-delete-solid">删除</el-button>
            </el-popconfirm>

          </div>


          <div class="table-container">
            <el-table v-loading="tableLoad" ref="tableDataRef" :data="tableData" style="width: 100%"
              :default-sort="currentSortProp" @sort-change="handleSortChange" @selection-change="handleSelectionChange">
              <el-table-column :selectable="handleSelectDisabled" type="selection" min-width="2%">
              </el-table-column>
              <el-table-column prop="contractName" label="合同名称" min-width="10%" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="contractState" label="合同状态" min-width="8%" show-overflow-tooltip>
                <template #default="{row}">
                  <div
                    :class="['default-state',{'wait-sign':row.contractState == '0'},{'in-execution':row.contractState == '1'},{'terminated':row.contractState == '2'},{'apply-end-lease':row.contractState == '3'}]">
                    {{getContractValue(row.contractState)}}
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="contractNumber" label="合同编号" min-width="10%"
                show-overflow-tooltip></el-table-column>
              <el-table-column prop="signDate" label="签订日期" min-width="10%" sortable="custom"
                show-overflow-tooltip></el-table-column>
              <el-table-column prop="leaseStart" label="租赁开始期限" min-width="10%" sortable="custom"
                show-overflow-tooltip></el-table-column>
              <el-table-column prop="leaseEnd" label="租赁结束期限" min-width="10%" sortable="custom"
                show-overflow-tooltip></el-table-column>
              <el-table-column prop="phone" label="联系电话" min-width="10%" show-overflow-tooltip>
                <template #default="{row}">
                  <div>
                    {{hiddenPhone(row.phone)}}
                  </div>
                </template>
              </el-table-column>

              <el-table-column label="操作" min-width="12%">
                <template #default="{row}">
                  <div style="display: flex; align-items: center;">


                    <!-- 编辑 | 签订 | -->
                    <div v-if="row.contractState =='0'">
                      <el-button type="text" size="mini" @click="handleEdit(row)">编辑</el-button>
                      <span style="color: #dfdfdf;padding: 0px 3px;">|</span>

                      <el-popconfirm v-if="tableData.length>0" @confirm="handleSigned('sign',row)" title="是否确认合同已签订"
                        placement="top">
                        <el-button slot="reference" type="text" size="mini">签订</el-button>
                      </el-popconfirm>
                      <span style="color: #dfdfdf;padding: 0px 3px;">|</span>
                    </div>

                    <!-- 变更 | -->
                    <div v-if="row.contractState == '1' || row.contractState == '3'">
                      <el-button type="text" size="mini" @click="handleContractAlter(row)">变更</el-button>
                      <span style="color: #dfdfdf;padding: 0px 3px;">|</span>
                    </div>


                    <!-- 打印 只有待签订 执行中 已终止 申请退租才有 -->
                    <div v-if="row.contractState == '0' || row.contractState == '1' || row.contractState == '3'">
                      <el-button type="text" size="mini" @click="handleContractPrint(row)">打印</el-button>
                    </div>

                    <!-- | 退租 -->
                    <div v-if="row.contractState == '1' || row.contractState == '3'">
                      <span style="color: #dfdfdf;padding: 0px 3px;">|</span>

                      <el-button type="text" size="mini" @click="handleSurrenderTenancyBtn(row)">退租</el-button>
                    </div>

                    <!-- | 删除 -->
                    <div v-if="row.contractState == '0'">
                      <span style="color: #dfdfdf; padding: 0px 3px;">|</span>

                      <el-popconfirm v-if="tableData.length>0" @confirm="delLogic('del',row)" title="是否删除当前行的内容"
                        placement="top">
                        <el-button slot="reference" type="text" size="mini">删除</el-button>
                      </el-popconfirm>
                    </div>

                    <!-- 预览 -->
                    <div v-if="row.contractState == '2'">
                      <el-button type="text" size="mini" @click="handlePreviewBtn(row)">预览</el-button>
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
              <span style="line-height: 32px;">显示从 1 到 {{total}} , 总 {{total}} 条记录</span>
              <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="pagination.pageNum" :page-sizes="[10, 15]" :page-size="pagination.pageSize"
                layout="  prev, pager, next,slot" :total="total">

                <el-select size='mini' v-model="pagination.pageSize" @change="handleSizeChange">
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

  export default {

    data() {
      return {

        // 存储合同状态字典值
        contractStateDicts: [],

        // 控制初始化骨架屏 默认打开true
        dictLoad: true,
        initAxiosLoad: true,

        inquireForm: {
          contractName: undefined,
          contractNumber: undefined,
          customerName: undefined,
          contractState: undefined,
        },

        inquireFormRules: {
          contractName: [
            {
              validator: this.leastInputOne, trigger: 'blur'
            }
          ],
          contractNumber: [
            {
              validator: this.leastInputOne, trigger: 'blur'
            }
          ],
          customerName: [
            {
              validator: this.leastInputOne, trigger: 'blur'
            }
          ],
          contractState: [
            {
              validator: this.leastInputOne, trigger: 'blur'
            }
          ],
        },

        // 控制table的加载状态 true打开,false关闭
        tableLoad: false,

        tableData: [
          // {
          //   id: 1,
          //   contractName: "房产合同",
          //   contractState: "0",
          //   contractNumber: "145323",
          //   signDate: "2024-04-10",
          //   leaseStart: "2024-04-10",
          //   leaseEnd: "2025-04-10",
          //   phone: "13213123112",
          // },

        ],
        multipleSelection: [],
        currentSortProp: {},
        pagination: {
          pageNum: 1,
          pageSize: 10
        },

        pageSizeArr: [
          { label: '10 条/页', value: 10 },
          { label: '15 条/页', value: 15 },
        ],

        total: 0,


      }
    },
    computed: {

      getContractValue() {
        return (stateCode) => {
          const { dictLabel } = this.contractStateDicts.find(item => item.dictValue == stateCode) || {}
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
        Promise.all([this.getDicts('park_contract_status')]).then(res => {
          this.contractStateDicts = res[0].data
          console.log('合同状态字典值:', this.contractStateDicts);
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
              contractName: "房产合同",
              contractState: "0",
              contractNumber: "145323",
              signDate: "2024-04-10",
              leaseStart: "2024-04-10",
              leaseEnd: "2025-04-10",
              phone: "13213123112",
            },
            {
              id: 2,
              contractName: "资产合同",
              contractState: "1",
              contractNumber: "123134533",
              signDate: "2024-05-11",
              leaseStart: "2024-05-14",
              leaseEnd: "2025-05-14",
              phone: "13214343112",
            },
            {
              id: 3,
              contractName: "资本合同",
              contractState: "2",
              contractNumber: "12353533",
              signDate: "2024-06-11",
              leaseStart: "2024-06-14",
              leaseEnd: "2025-06-14",
              phone: "1354343112",
            },
            {
              id: 4,
              contractName: "资级合同",
              contractState: "3",
              contractNumber: "22353533",
              signDate: "2024-07-11",
              leaseStart: "2024-07-14",
              leaseEnd: "2025-07-14",
              phone: "1384343112",
            },
          ]

          this.tableData = data
          this.initAxiosLoad = false
        }, 500);
      },

      leastInputOne(rule, value, callback) {
        if (!(this.inquireForm.contractName) && !(this.inquireForm.contractNumber) && !(this.inquireForm.customerName) && !(this.inquireForm.contractState)) return callback(new Error('请至少输入一个查询数据'))
        callback()
      },

      handleInquire() {
        this.$refs['formRef'].validate(valid => {
          if (!valid) return
          // 发送查询axios请求
          setTimeout(() => {
            console.log('发送查询请求');
          }, 1000)
        })

      },

      // inquireForm重置
      handleReset() {
        this.$refs['formRef'].resetFields()
      },

      handleSortChange({ column, prop, order }) {
        this.currentSortProp.prop = prop
        this.currentSortProp.order = order
      },

      // 处理复选框的勾选状态
      handleSelectDisabled(row, index) {
        if (row.contractState == '1' || row.contractState == '2' || row.contractState == '3') return false
        return true
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

      // 共用一个删除逻辑  不确实是否可行 试一下
      delLogic(isFlot, row) {
        if (isFlot == 'batch') {
          if (!(this.multipleSelection.length > 0)) return this.$message.error('请先勾选要签订的合同')
        }
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

          case 'del':
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



      handleCurrentChange(value) {
        this.pagination.pageNum = value
        console.log(this.pagination);

      },

      // 处理自定义的select
      handleSizeChange(value) {
        this.pagination.pageSize = value
        console.log(this.pagination);

      },

      // 处理点击新建逻辑
      handleAdd() {
        this.$router.push(`/assets/contractEdit/contractNew`)
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



    .building-container {
      .table-container {

        /* state共用样式 */
        .default-state {
          width: 70px;
          height: 30px;
          line-height: 30px;
          text-align: center;
          border-radius: 5px;
        }

        /* 待签订样式 */
        .wait-sign {
          color: #ffba00;
          background-color: #fff8e6;
        }

        /* 执行中样式 */
        .in-execution {
          color: #13d1a9;
          background-color: #e7faf0;
        }

        /* 已终止样式 */
        .terminated {
          color: #9093a5;
          background-color: #f4f4f5;
        }


        /* 申请退租样式 */
        .apply-end-lease {
          color: #ff4949;
          background-color: #ffeded;
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