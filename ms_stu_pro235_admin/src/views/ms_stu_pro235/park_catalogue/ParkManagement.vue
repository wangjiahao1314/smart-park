<template>
  <div id="page-style">
    <div class="park-container" style="padding: 20px; background-color: #fff;">

      <el-skeleton style="width: 100%" :loading="initAxiosLoad" animated>
        <template slot="template">
          <div style=" padding: 20px; background-color: #fff;">
            <div style="padding: 10px 0px;">
              <el-skeleton-item variant="h2" />
            </div>

            <el-skeleton-item style="width: 100%; height: 470px;" variant="rect" />
          </div>
        </template>
        <template>

          <inquire ref="inquireComponentRef" label="园区名称" placeholder='请输入' :inquire-form="inquireForm"
            :form-rules="inquireFormRules" @to-inquire="handleInquire" @to-reset="handleReset">
          </inquire>

          <div style="padding-bottom: 20px;">
            <el-button class="primary-btn" style="margin-right: 10px;" type="primary" size="mini" icon="el-icon-plus"
              @click="addPark">新建</el-button>

            <el-popconfirm v-if="multipleSelection.length>0" key="1" @confirm="delPark('batch-del')" title="是否删除所选中的内容"
              placement="top">
              <el-button slot="reference" class="danger-btn" type="danger" size="mini"
                icon="el-icon-delete-solid">删除</el-button>
            </el-popconfirm>
          </div>


          <div class="table-container">
            <el-table v-loading="tableLoad" ref="tableDataRef" highlight-selection-row :data="tableData"
              @cell-click="handelCellClick" @selection-change="handleSelectionChange" style="width: 100%">
              <el-table-column type="selection" min-width="2%">
              </el-table-column>
              <el-table-column prop="parkName" label="园区名称" min-width="10%" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="remark" label="备注" min-width="10%" show-overflow-tooltip></el-table-column>
              <el-table-column prop="createTime" label="创建时间" min-width="10%" sortable
                show-overflow-tooltip></el-table-column>
              <el-table-column prop="createBy" label="创建人" min-width="10%" show-overflow-tooltip></el-table-column>

              <el-table-column label="操作" min-width="8%">
                <template #default="{row}">
                  <div>
                    <el-button type="text" size="mini" @click="handleEdit(row)">编辑</el-button>
                    <span style="color: #dfdfdf;">|</span>
                    <el-popconfirm @confirm="delPark('del',row)" title='是否确实删除该园区？' placement="top">
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
              <el-pagination background @current-change="handleCurrentChange" :current-page="pagination.pageNum"
                :page-sizes="[10, 15]" :page-size="pagination.pageSize" layout="  prev, pager, next,slot"
                :total="total">

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
        <el-dialog :title="module=='add'?'创建园区':'编辑园区'" :visible.sync="dialogFormVisible" @close="handleDialogCancel">
          <div class="dialog-form-container">
            <el-form ref="dialogFormRef" :model="dialogForm" :rules="dialogFormRules" label-width="100px"
              label-position="right" @submit.native.prevent>

              <el-form-item label="园区名称" prop="parkName">
                <el-input placeholder='请输入园区名称' size="mini" v-model="dialogForm.parkName"></el-input>
              </el-form-item>
              <el-form-item label="备注" prop="remark">
                <el-input :autosize="{ minRows: 4, maxRows: 8}" resize="none" type="textarea" rows='4'
                  v-model="dialogForm.remark"></el-input>
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
  import { getPageQuery, addPark, inquirePark, editPark, deletePark } from '@/api/ms_stu_pro235/park_manage/park_management.js'
  export default {
    components: {
      Inquire
    },
    data() {
      return {
        // 控制骨架屏的开关逻辑
        initAxiosLoad: true,

        inquireForm: {
          name: undefined
        },

        inquireFormRules: {
          name: [
            {
              required: true, message: '请输入园区名称', trigger: 'blur'
            }
          ]
        },

        // 控制开始查询逻辑
        isInquireControl: false,

        // 控制table加载逻辑
        tableLoad: false,

        tableData: [
          // {
          //   id: 1,
          //   parkName: "明诚广场",
          //   remark: "1111",
          //   createTime: "2024-04-09 09:15:20",
          //   createBy: "111",
          // },
        ],
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
          parkName: undefined,
          remark: undefined,
        },
        dialogFormRules: {
          parkName: [
            { required: true, message: '请输入园区名称', trigger: 'blur' }
          ],

        }

      }
    },
    computed: {
    },
    created() {
      this.getInitAxiosData()
    },
    mounted() {
    },
    methods: {

      // 封装查询/刷新分页
      async refreshPage(pagination) {
        const { name: zoneName } = this.inquireForm
        // 判断是否开启了模糊查询
        let requestObj = this.isInquireControl && zoneName !== undefined ? { zoneName, ...pagination } : { ...pagination }
        try {
          this.tableLoad = true
          let result = await getPageQuery(requestObj)
          console.log(result);

          this.total = result.total
          this.tableData = result.rows.map(item => {
            return {
              id: item.zoneId,
              parkName: item.zoneName,
              remark: item.remark,
              createTime: item.createTime,
              createBy: item.createBy,
            }
          })
          this.tableLoad = false
        } catch (error) {
          console.log(error);
        }
      },

      async getInitAxiosData() {
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

      addPark() {
        this.module = 'add'
        this.dialogFormVisible = true

      },

      async delPark(key, row) {

        try {
          let result = undefined
          switch (key) {
            case 'batch-del':
              // 将对应的租户id提取出来
              let requestArr = this.multipleSelection.map(item => item.id)
              result = await deletePark(requestArr)
              break;

            case 'del':
              result = await deletePark(row.id)
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
        this.dialogFormVisible = true
        inquirePark(row.id).then(res => {
          console.log(res);
          const data = res.data
          this.$nextTick(() => {
            this.dialogForm = {
              id: data.zoneId,
              parkName: data.zoneName,
              remark: data.remark
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
        console.log(this.dialogForm);

      },

      handleSureBtn() {
        this.$refs['dialogFormRef'].validate(async (valid) => {
          if (!valid) return
          try {
            let result = undefined
            // 处理数据
            const { id: zoneId, parkName: zoneName, remark } = this.dialogForm
            let requestObj = {
              zoneName,
              remark
            }
            switch (this.module) {
              case 'add':
                console.log('add');
                result = await addPark(requestObj)
                this.$message.success('添加成功')
                break;

              case 'edit':
                console.log('edit');
                result = await editPark({ zoneId, ...requestObj })
                this.$message.success('编辑成功')
                break;
            }
            console.log(result);

          } catch (error) {
            console.log(error);
          }
          this.dialogFormVisible = false
          this.isInquireControl = false
          this.refreshPage(this.pagination)
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

    .park-container {
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

      /* .table-container {} */

      .dialog-container {
        .dialog-form-container {
          width: 80%;
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