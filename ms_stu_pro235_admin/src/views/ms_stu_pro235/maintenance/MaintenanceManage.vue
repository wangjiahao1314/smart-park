<template>
  <div id="page-style">
    <div class="maintenance-manage-container" style="padding: 20px; background-color: #fff;">
      <inquire ref="inquireComponentRef" label="维修员名称" placeholder='请输入维修员名称' :inquire-form="inquireForm"
        :form-rules="inquireFormRules" @to-inquire="handleInquire" @to-reset="handleReset">
      </inquire>

      <div style="padding-bottom: 20px;">
        <el-button class="primary-btn" type="primary" size="mini" icon="el-icon-plus" @click="addTeant">新建</el-button>

        <el-popconfirm v-if="tableData.length>0" @confirm="delTeant('batch-del')" title="是否删除所选中的内容" placement="top">
          <el-button slot="reference" class="danger-btn" type="danger" size="mini"
            icon="el-icon-delete-solid">删除</el-button>
        </el-popconfirm>

      </div>


      <div class="table-container">
        <el-table ref="tableDataRef" highlight-selection-row :data="tableData" @selection-change="handleSelectionChange"
          style="width: 100%">
          <el-table-column type="selection" min-width="2%">
          </el-table-column>

          <el-table-column prop="name" label="维修员名称" min-width="5%" show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="identityCardNumber" label="身份证号码" min-width="13%" show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="mail" label="邮箱" min-width="11%" show-overflow-tooltip></el-table-column>
          <el-table-column prop="phone" label="联系电话" min-width="8%" show-overflow-tooltip></el-table-column>
          <el-table-column prop="address" label="家庭住址" min-width="25%" show-overflow-tooltip></el-table-column>

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

      <div class="dialog-container">
        <el-dialog :title="module=='add'?'创建用户':'编辑用户'" :visible.sync="dialogFormVisible" @close="handleDialogCancel">
          <div class="dialog-form-container">
            <el-form ref="dialogFormRef" :model="dialogForm" :rules="dialogFormRules" label-width="120px"
              label-position="right">
              <el-form-item label="账户名" prop="username">
                <el-input size="mini" v-model="dialogForm.username"></el-input>
              </el-form-item>
              <el-form-item label="真实姓名" prop="name">
                <el-input size="mini" v-model="dialogForm.name"></el-input>
              </el-form-item>
              <el-form-item label="身份证号码" prop="identityCardNumber">
                <el-input size="mini" v-model="dialogForm.identityCardNumber"></el-input>
              </el-form-item>
              <el-form-item label="邮箱" prop="mail">
                <el-input size="mini" v-model="dialogForm.mail"></el-input>
              </el-form-item>
              <el-form-item label="联系电话" prop="phone">
                <el-input size="mini" v-model="dialogForm.phone"></el-input>
              </el-form-item>
              <el-form-item label="家庭住址" prop="address">
                <el-input size="mini" v-model="dialogForm.address"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input size="mini" v-model="dialogForm.password" show-password></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="confirmPassword">
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

  export default {
    components: {
      Inquire
    },
    data() {
      return {
        inquireForm: {
          name: undefined
        },

        inquireFormRules: {
          name: [
            {
              required: true, message: '请输入维修工名称', trigger: 'blur'
            }
          ]
        },

        tableData: [
          {
            id: 1,
            name: "李四",
            identityCardNumber: "TZ202207151301309258",
            mail: "lisi@163.com",
            phone: "12345678901",
            address: "上海市浦东新区明珠路 88 号海悦花园 3 栋 2 单元 302 室",
          },
          {
            id: 2,
            name: "王五",
            identityCardNumber: "TZ202208161402319369",
            mail: "wangwu@foxmail.com",
            phone: "10000",
            address: "广州市天河区珠江大道 66 号星河天地 2 栋 3 单元 403 室",
          },
          {
            id: 3,
            name: "赵六",
            identityCardNumber: "TZ202209171503329470",
            mail: "zhaoliu@hotmail.com",
            phone: "12345",
            address: "深圳市南山区科技路 99 号科创家园 4 栋 4 单元 504 室",
          }
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
          username: undefined,
          name: undefined,
          identityCardNumber: undefined,
          mail: undefined,
          phone: undefined,
          address: undefined,
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
              pattern: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
              message: '请输入正确的身份证号码',
              trigger: 'blur'
            },
          ],
          phone: [
            { required: true, message: '联系手机号码不能为空', trigger: 'blur' },
            {
              pattern: /^1[3-9]\d{9}$/,
              message: '请输入正确的手机号码',
              trigger: 'blur'
            },
          ],
          address: [
            { required: true, message: '家庭地址不能为空', trigger: 'blur' }

          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' },

          ],
          confirmPassword: [
            { required: true, message: '确认密码不能为空', trigger: 'blur' },
            { validator: this.handleConfirmPassword, trigger: 'blur' }
          ]

        }

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
    },
    mounted() {
    },
    methods: {
      getAllDicts() {
        this.getDicts('park_tenant_status').then(res => {
          this.taentStateDict = res.data
          console.log(this.taentStateDict);


        }).catch(err => {
          console.log(err);

        })
      },
      handleInquire() {
        // 发送查询axios请求
        setTimeout(() => {
          console.log('发送查询请求');
        }, 1000)
      },

      // inquireForm重置
      handleReset() {

      },

      addTeant() {
        this.module = 'add'
        this.dialogFormVisible = true

      },

      delTeant(key, row) {
        console.log(key);

        switch (key) {
          case 'batch-del':
            if (!this.multipleSelection.length > 0) return this.$message.warning('请勾选要批量删除的租户')
            // 调用axios接口 批量删除
            setTimeout(() => {
              this.$message.success('删除成功')
            }, 1000);
            break;

          case 'del':
            console.log(row);
            // 调用axiso接口 删除
            setTimeout(() => {
              this.$message.success('删除成功')
            }, 1000);
            break;
        }



      },
      // 处理table编辑按钮
      handleEdit(row) {
        this.module = 'edit'
        // 根据row拿到对应id  发起查询详细接口
        setTimeout(() => {
          // 模拟数据
          const copyRow = JSON.parse(JSON.stringify(row))
          this.dialogFormVisible = true
          this.$nextTick(() => {
            this.dialogForm = copyRow
          })
        }, 1000);

      },

      // 处理dialog取消逻辑
      handleDialogCancel() {
        this.$refs['dialogFormRef'].resetFields()
        this.dialogFormVisible = false
        console.log(this.dialogForm);

      },

      handleSureBtn() {
        this.$refs['dialogFormRef'].validate(valid => {
          if (!valid) return
          switch (this.module) {
            case 'add':
              console.log('add');

              break;

            case 'edit':
              console.log('edit');

              break;
          }
          this.dialogFormVisible = false
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
        console.log(this.pagination);

      },

      handleSizeChange(value) {
        this.pagination.pageSize = value
        console.log(this.pagination);

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

    .maintenance-manage-container {
      .table-container {

        ::v-deep .el-empty__description {
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