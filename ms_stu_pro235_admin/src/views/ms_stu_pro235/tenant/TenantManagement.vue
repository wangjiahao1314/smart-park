<template>
  <div id="page-style">
    <div class="teant-container" style="padding: 20px; background-color: #fff;">

      <el-skeleton style="width: 100%;" class="skeleton" :loading="dictLoad || initAxiosLoad" animated>
        <template slot="template">
          <div style=" padding: 20px; background-color: #fff;">
            <div style="padding: 10px 0px;">
              <el-skeleton-item variant="h2" />
            </div>

            <el-skeleton-item style="width: 100%; height: 470px;" variant="rect" />
          </div>
        </template>
        <template>

          <inquire ref="inquireComponentRef" label="租户名称" placeholder='请输入租户名称' :inquire-form="inquireForm"
            :form-rules="inquireFormRules" @to-inquire="handleInquire" @to-reset="handleReset">
          </inquire>

          <div style="padding-bottom: 20px;">
            <el-button class="primary-btn" style="margin-right: 10px;" type="primary" size="mini" icon="el-icon-plus"
              @click="addTeant">新建</el-button>

            <el-popconfirm v-if="multipleSelection.length>0" key="1" @confirm="delTeant('batch-del')" title="是否删除所选中的内容"
              placement="top">
              <el-button slot="reference" class="danger-btn" type="danger" size="mini"
                icon="el-icon-delete-solid">删除</el-button>
            </el-popconfirm>
          </div>


          <div class=" table-container">
            <el-table v-loading="tableLoad" ref="tableDataRef" highlight-selection-row :data="tableData"
              @cell-click="handelCellClick" @selection-change="handleSelectionChange" style="width: 100%">
              <el-table-column type="selection" min-width="2%">
              </el-table-column>

              <el-table-column prop="name" label="租户名称" min-width="7%" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="identityCardNumber" label="身份证号码" min-width="13%" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="mail" label="邮箱" min-width="11%" show-overflow-tooltip></el-table-column>
              <el-table-column prop="phone" label="联系电话" min-width="8%" show-overflow-tooltip></el-table-column>
              <el-table-column prop="address" label="家庭住址" min-width="25%" show-overflow-tooltip></el-table-column>
              <el-table-column prop="state" label="租户状态" min-width="6%">
                <template #default="{row}">
                  <div :class="row.state == '0'?'success-style':'info-style'" style="width: 44px;">
                    {{getTeantState(row.state)}}
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="room" label="在租房间" min-width="6%" show-overflow-tooltip>
                <template #default="{row}">
                  <div v-if="row.state == '0'">
                    <span v-for="item in row.room">
                      {{item.roomName}}
                    </span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="操作" min-width="8%">
                <template #default="{row}">
                  <div>
                    <el-button type="text" size="mini" @click="handleEdit(row)">编辑</el-button>
                    <span style="color: #dfdfdf;">|</span>
                    <el-popconfirm @confirm="delTeant('del',row)" title='是否确实删除该租户？' placement="top">
                      <el-button slot="reference" type="text" size="mini">删除</el-button>
                    </el-popconfirm>
                  </div>
                </template>
              </el-table-column>



              <template slot="empty">
                <el-empty :image-size="Number(80)">
                  <template slot="image">
                    <img src="@/assets/images/envelope.png" alt="">
                  </template>
                </el-empty>
              </template>
            </el-table>

            <div style="display: flex; justify-content: end;  font-size: 13px; color: #606266;">
              <span style="line-height: 32px;">显示从 1 到 {{pagination.pageSize}} , 总 {{total}} 条记录</span>
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

        </template>
      </el-skeleton>




      <div class="dialog-container">
        <el-dialog :title="module=='add'?'创建用户':'编辑用户'" :visible.sync="dialogFormVisible" @close="handleDialogCancel">
          <div class="dialog-form-container">
            <el-form ref="dialogFormRef" :model="dialogForm" :rules="dialogFormRules" label-width="120px"
              label-position="right">
              <el-form-item label="账户名" prop="username">
                <el-input v-model="dialogForm.username" size="mini" :disabled="module == 'edit'"></el-input>
              </el-form-item>
              <el-form-item label="真实姓名" prop="name">
                <el-input size="mini" v-model="dialogForm.name"></el-input>
              </el-form-item>
              <el-form-item label="身份证号码" prop="identityCardNumber">
                <el-input v-model="dialogForm.identityCardNumber" size="mini" :disabled="module == 'edit'"></el-input>
              </el-form-item>
              <el-form-item label="邮箱" prop="mail">
                <el-input size="mini" v-model="dialogForm.mail"></el-input>
              </el-form-item>
              <el-form-item label="联系电话" prop="phone">
                <el-input v-model="dialogForm.phone" size="mini" :disabled="module == 'edit'"></el-input>
              </el-form-item>
              <el-form-item label="家庭区域" prop="homeArea">
                <el-input v-model="dialogForm.homeArea" size="mini"></el-input>
              </el-form-item>
              <el-form-item label="家庭详情地址" prop="addressDetails">
                <el-input v-model="dialogForm.addressDetails" size="mini"></el-input>
              </el-form-item>
              <el-form-item v-if="module !== 'edit'" label="密码" prop="password">
                <el-input size="mini" v-model="dialogForm.password" show-password></el-input>
              </el-form-item>
              <el-form-item v-if="module !== 'edit'" label="确认密码" prop="confirmPassword">
                <el-input size="mini" v-model="dialogForm.confirmPassword" show-password></el-input>
              </el-form-item>
            </el-form>
          </div>
          <div slot="footer" class="dialog-footer">
            <el-button @click="handleDialogCancel">取 消</el-button>
            <el-button type="primary" @click="handleSureBtn">确 定</el-button>
          </div>
        </el-dialog>
      </div>

    </div>
  </div>
</template>
<script>
  import Inquire from '@/components/ms_stu_pro235/common/Inquire.vue'
  // 导入api
  import { getPageQuery, addTenantry, inquireTenantry, editTenantry, deleteTenantry } from '@/api/ms_stu_pro235/tenant/tenant.js'
  export default {
    components: {
      Inquire
    },
    data() {
      return {
        dictLoad: true,
        initAxiosLoad: true,

        inquireForm: {
          name: undefined
        },

        inquireFormRules: {
          name: [
            {
              required: true, message: '租户名称不能为空', trigger: 'blur'
            }
          ]
        },

        // 为true表示开始名称模糊查询
        isInquireControl: false,

        // 控制表格的加载状态
        tableLoad: false,

        tableData: [
          {
            // id: 1,
            // name: "李四",
            // identityCardNumber: "TZ202207151301309258",
            // mail: "lisi@163.com",
            // phone: "12345678901",
            // address: "上海市浦东新区明珠路 88 号海悦花园 3 栋 2 单元 302 室",
            // state: "1",
            // room: "5 栋 201"
          },
        ],
        taentStateDict: [],
        multipleSelection: [],
        module: 'add',

        pagination: {
          pageNum: 1,
          pageSize: 10
        },

        pageSizeArr: [
          { label: '10 条/页', value: 10 },
          { label: '15 条/页', value: 15 },
        ],

        total: 0,

        // 对话框
        dialogFormVisible: false,
        dialogForm: {
          id: undefined,
          username: undefined,
          name: undefined,
          identityCardNumber: undefined,
          mail: undefined,
          phone: undefined,
          homeArea: undefined,
          addressDetails: undefined,
          password: undefined,
          confirmPassword: undefined
        },
        dialogFormRules: {
          username: [
            { required: true, message: '账户名不能为空', trigger: 'blur' }

          ],
          name: [
            { required: true, message: '姓名不能为空', trigger: 'blur' },
          ],
          identityCardNumber: [
            { required: true, message: '身份证号码不能为空', trigger: 'blur' },
            {
              pattern: /(^\d{8}(0\d|10|11|12)([0-2]\d|30|31)\d{3}$)|(^\d{6}(18|19|20)\d{2}(0\d|10|11|12)([0-2]\d|30|31)\d{3}[0-9Xx]$)|(^\d{6}\*{6}\d{4}$)/,
              message: '请输入正确的身份证号码',
              trigger: 'blur'
            },
          ],
          phone: [
            { required: true, message: '联系手机号码不能为空', trigger: 'blur' },
            {
              pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\*{4}[0-9]{4}$|^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])[0-9]{8}$/,
              message: '请输入正确的手机号码',
              trigger: 'blur'
            },
          ],
          homeArea: [
            { required: true, message: '家庭区域不能为空', trigger: 'blur' }
          ],
          addressDetails: [
            { required: true, message: '家庭详细地址不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' },
            {
              pattern: /^[a-zA-Z0-9_]{6,20}$/,
              message: '请输入长度大于等于6位且不大于20位的密码',
              trigger: 'blur'
            },
          ],
          confirmPassword: [
            { required: true, message: '确认密码不能为空', trigger: 'blur' },
            { validator: this.handleConfirmPassword, trigger: 'blur' }
          ]

        },

        // 测试if
        testIf: false

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
      getAllDicts() {

        Promise.all([this.getDicts('park_tenant_status')]).then(res => {
          this.taentStateDict = res[0].data
          this.dictLoad = false
        }).catch(err => {
          console.log(err);
        })

      },

      // 封装查询/刷新分页
      async refreshPage(pagination) {
        const { name: nickName } = this.inquireForm
        // 判断是否开启了模糊查询
        let requestObj = this.isInquireControl && nickName !== undefined ? { nickName, ...pagination } : { ...pagination }
        try {
          this.tableLoad = true
          let result = await getPageQuery(requestObj)
          this.total = result.total
          this.tableData = result.rows.map(item => {
            return {
              id: item.tenantId,
              name: item.nickName,
              identityCardNumber: item.idCardNumber,
              mail: item.email,
              phone: item.phonenumber,
              address: item.homeArea + item.addressDetails,
              state: item.tenantStatus,
              room: item.rentingRooms,
            }
          })
          this.tableLoad = false
        } catch (error) {
          console.log(error);

        }
      },


      // 初始化发送查询请求
      async getInitAxiosData() {
        // 得到分页数据
        try {
          await this.refreshPage(this.pagination)
        } catch (error) {
          console.log(error);
        }
        this.initAxiosLoad = false

      },



      handleInquire() {
        this.isInquireControl = true
        this.refreshPage(this.pagination)
      },

      // inquireForm重置
      handleReset() {
        this.isInquireControl = false
        this.refreshPage(this.pagination)

      },


      handelCellClick(row, column, cell, event) {
        if (column.label == '操作') return
        let isExist = this.multipleSelection.some(item => item.id == row.id)
        if (isExist) return this.$refs['tableDataRef'].toggleRowSelection(row, false)
        this.$refs['tableDataRef'].toggleRowSelection(row, true)

      },

      addTeant() {
        this.module = 'add'
        this.dialogFormVisible = true

      },

      async delTeant(key, row) {
        try {
          let result = undefined
          switch (key) {
            case 'batch-del':
              // 将对应的租户id提取出来
              let requestArr = this.multipleSelection.map(item => item.id)
              result = await deleteTenantry(requestArr)
              break;

            case 'del':
              result = await deleteTenantry(row.id)
              break;
          }
          this.$message.success('删除成功')
        } catch (error) {
          console.log(error);

        }
        this.refreshPage(this.pagination)



      },
      // 处理table编辑按钮
      handleEdit(row) {
        this.module = 'edit'
        // 根据row拿到对应id  发起查询详细接口
        inquireTenantry(row.id).then(res => {
          this.dialogFormVisible = true
          this.$nextTick(() => {
            let data = res.data
            this.dialogForm = {
              id: data.tenantId,
              username: data.userName,
              name: data.nickName,
              identityCardNumber: data.idCardNumber,
              mail: data.email,
              phone: data.phonenumber,
              homeArea: data.homeArea,
              addressDetails: data.addressDetails,
              password: undefined,
            }
          })
        }).catch(err => {
          console.log(err);

        })

      },

      // 处理dialog取消逻辑
      handleDialogCancel() {
        this.$refs['dialogFormRef'].resetFields()
        this.dialogFormVisible = false
      },

      handleSureBtn() {
        this.$refs['dialogFormRef'].validate(async (valid) => {
          if (!valid) return
          const { id: tenantId, username: userName, name: nickName, identityCardNumber: idCardNumber, mail: email, phone: phonenumber, homeArea, addressDetails, password: password } = this.dialogForm
          let requestObj = {
            userName,
            nickName,
            idCardNumber,
            email,
            phonenumber,
            homeArea,
            addressDetails,
            password,
          }
          try {
            let result = undefined
            switch (this.module) {
              case 'add':
                result = await addTenantry(requestObj)
                this.$message.success('新增租户成功')
                break;

              case 'edit':
                // 把不需要的参数提取出来
                const { userName, idCardNumber, phonenumber, password, ...rest } = requestObj
                result = await editTenantry({ tenantId, ...rest })
                this.$message.success('编辑租户成功')

                break;
            }
            this.dialogFormVisible = false
            // 把控制查询的逻辑关闭
            this.isInquireControl = false
            this.refreshPage(this.pagination)
          } catch (error) {
            console.log(errow);
          }
        })
      },


      handleSelectionChange(selection) {
        this.multipleSelection = selection
      },

      handleConfirmPassword(rule, value, callback) {
        this.dialogForm.password === this.dialogForm.confirmPassword ? callback() : callback(new Error('两次密码输入不一致'))
      },

      handleCurrentChange(value) {
        this.pagination.pageNum = value
        this.refreshPage(this.pagination)
      },

      handleSizeChange(value) {
        // 重置pageNum
        this.pagination.pageNum = 1
        this.pagination.pageSize = value
        this.refreshPage(this.pagination)
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

    .teant-container {
      /* .skeleton {
        .table-container {

          ::v-deep .el-table__empty-block .el-empty__description {
            margin: 0;
            line-height: 14px;
          }

          ::v-deep .selection-row>td {
            background-color: #f5f7fa !important;
          }

          ::v-deep .el-table--medium {
            margin-bottom: 10px;
          }
        }

      } */

      ::v-deep .el-table__empty-block .el-empty__description {
        margin: 0;
        line-height: 14px;
      }

      ::v-deep .selection-row>td {
        background-color: #f5f7fa !important;
      }

      ::v-deep .el-table--medium {
        margin-bottom: 10px;
      }


      .dialog-container {
        .dialog-form-container {
          width: 60%;
          margin: 0 auto;
        }

        ::v-deep .el-dialog__header {
          border-bottom: 1px solid #dfdfdf;
        }

        ::v-deep .el-dialog__footer {

          border-top: 1px solid #dfdfdf;
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