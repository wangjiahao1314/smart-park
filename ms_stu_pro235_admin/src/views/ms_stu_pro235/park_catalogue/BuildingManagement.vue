<template>
  <div id="page-style">
    <div class="building-container" style="padding: 20px; background-color: #fff;">
      <el-skeleton style="width: 100%" :loading="initAxiosLoad" animated>
        <template slot="template">
          <div style=" padding: 20px; background-color: #fff;">
            <div style="padding: 10px 0px;">
              <el-skeleton-item variant="h2" />
            </div>

            <el-skeleton-item class="table-container" style="width: 100%; height: 470px;" variant="rect" />
          </div>
        </template>
        <template>
          <el-form ref="formRef" :inline="true" :model="inquireForm" :rules="inquireFormRules">
            <el-form-item label="园区名称" style="margin-right: 30px;" prop="parkName">
              <el-input v-model="inquireForm.parkName" size="mini" placeholder='请输入园区名称'
                @keyup.enter.native="handleInquire"></el-input>
            </el-form-item>

            <el-form-item label="楼宇名称" style="margin-right: 30px;" prop="buildingName">
              <el-input v-model="inquireForm.buildingName" size="mini" placeholder='请输入楼宇名称'
                @keyup.enter.native="handleInquire"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button class="primary-btn" type="primary" size="mini" @click="handleInquire">查询</el-button>
              <el-button class="default-btn" type="default" size="mini" @click="handleReset">重置</el-button>
            </el-form-item>
          </el-form>

          <div style="padding-bottom: 20px;">
            <el-button class="primary-btn" style="margin-right: 10px;" type="primary" size="mini" icon="el-icon-plus"
              @click="addBuilding">新建</el-button>

            <el-popconfirm v-if="multipleSelection.length>0" key="1" @confirm="delLogic('batch-del')" title="是否删除所选中的内容"
              placement="top">
              <el-button slot="reference" class="danger-btn" type="danger" size="mini"
                icon="el-icon-delete-solid">删除</el-button>
            </el-popconfirm>
          </div>


          <div class="table-container">
            <el-table v-loading="tableLoad" ref="tableDataRef" highlight-selection-row :data="tableData"
              @cell-click="(row, column, cell, event)=>{handelCellClick('tableDataRef',row, column, cell, event)}"
              @selection-change="(selection)=>{handleSelectionChange(selection,'building')}" style="width: 100%">
              <el-table-column type="selection" min-width="2%">
              </el-table-column>
              <el-table-column prop="parkName" label="所属园区" min-width="10%" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="buildingName" label="楼宇名称" min-width="10%" show-overflow-tooltip>
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
                    <el-button icon="el-icon-s-operation" type="text" size="mini"
                      @click="handleFloorBtn(row)">楼层</el-button>
                    <span style="color: #dfdfdf;">|</span>

                    <el-popconfirm @confirm="delLogic('del',row)" title='是否确实删除该楼宇？' placement="top">
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

        </template>
      </el-skeleton>



      <div class="edit-dialog-container">
        <el-dialog :title="module=='add'?'创建楼宇':'编辑楼宇'" :visible.sync="dialogFormVisible"
          @close="handleDialogCancel('dialogFormRef')">
          <div class="dialog-form-container">

            <el-form ref="dialogFormRef" :model="dialogForm" :rules="dialogFormRules" label-width="100px"
              label-position="right">

              <el-form-item label="园区名称" prop="parkId">
                <!-- <el-input placeholder='请输入园区名称' size="mini" v-model="dialogForm.parkId"></el-input> -->
                <el-select v-model="dialogForm.parkId" style="width: 100%;" size="mini" placeholder="请选择园区">
                  <el-option v-for="item in parkSelect" :key="item.zoneId" :label="item.zoneName" :value="item.zoneId">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="楼宇名称" prop="buildingName">
                <el-input placeholder='请输入楼宇名称' size="mini" v-model="dialogForm.buildingName"></el-input>
              </el-form-item>
              <el-form-item label="备注" prop="remark">
                <el-input :autosize="{ minRows: 4, maxRows: 8}" resize="none" type="textarea" rows='4'
                  v-model="dialogForm.remark"></el-input>
              </el-form-item>

            </el-form>
          </div>
          <div slot="footer" class="dialog-footer">
            <el-button @click="handleDialogCancel('dialogFormRef')">取 消</el-button>
            <el-button type="primary" @click="handleSureBtn">确 定</el-button>
          </div>
        </el-dialog>
      </div>

      <!-- 楼层dialog -->
      <div class="floor-dialog-container">
        <el-dialog width="70%" title="楼层列表" :visible.sync="floorDialogVisible">
          <div class="dialog-form-container">

            <div style="padding-bottom: 20px;">
              <el-button class="primary-btn" style="margin-right: 10px;" type="primary" size="mini" icon="el-icon-plus"
                @click="handleAddFloor">新建</el-button>
              <el-popconfirm v-if="floorMultipleSelection.length>0" @confirm="delFloor('batch-del')" title="是否删除所选中的内容"
                placement="top">
                <el-button slot="reference" class="danger-btn" type="danger" size="mini"
                  icon="el-icon-delete-solid">删除</el-button>
              </el-popconfirm>
            </div>

            <el-table v-loading="floorDialogdTableLoad" ref="floorTableDataRef" highlight-selection-row
              :data="floorDialogdata"
              @cell-click="(row, column, cell, event)=>{handelCellClick('floorTableDataRef',row, column, cell, event)}"
              @selection-change="(selection)=>{handleSelectionChange(selection,'floor')}" style="width: 100%">
              <el-table-column type="selection" min-width="2%">
              </el-table-column>
              <el-table-column prop="floorNum" label="楼层" min-width="10%" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="name" label="楼层名称" min-width="10%" show-overflow-tooltip>
              </el-table-column>
              <el-table-column prop="createTime" label="创建时间" min-width="10%" show-overflow-tooltip
                sortable></el-table-column>
              <el-table-column prop="createBy" label="创建人" min-width="10%" show-overflow-tooltip></el-table-column>

              <el-table-column label="操作" min-width="8%">
                <template #default="{row}">
                  <div>
                    <el-button type="text" size="mini" @click="handleEditFloor(row)">编辑</el-button>
                    <span style="color: #dfdfdf;">|</span>

                    <el-popconfirm @confirm="delFloor('del',row)" title='是否确实删除该楼层？' placement="top">
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
          </div>

          <!-- 内嵌dialog -->
          <el-dialog :title="dialogModule=='add'?'创建楼层':'编辑楼层'" :visible.sync="innerDialogVisible" append-to-body
            @close="handleDialogCancel('floorDialogFormRef')">
            <div class="dialog-form-container">

              <el-form ref="floorDialogFormRef" :model="innerDialogForm" :rules="innerDialogFormRules"
                label-width="100px" label-position="right">

                <el-form-item label="楼层" prop="floorNum">
                  <el-input-number v-model="innerDialogForm.floorNum" controls-position="right" size="small" :min="1"
                    label="楼层"></el-input-number>

                </el-form-item>
                <el-form-item label="楼层名称" prop="name">
                  <el-input v-model="innerDialogForm.name" style="width: 90%;" placeholder='请输入楼宇名称' size="mini"
                    @keyup.enter.native="sureDialogBtn"></el-input>
                </el-form-item>

              </el-form>
            </div>
            <div slot="footer" class="dialog-footer">
              <el-button @click="handleDialogCancel('floorDialogFormRef')">取 消</el-button>
              <el-button type="primary" @click="sureDialogBtn">确 定</el-button>
            </div>
          </el-dialog>
        </el-dialog>
      </div>

    </div>
  </div>
</template>
<script>
  // 导入api
  import { getPageQuery, getParkSelect, addBuillding, deleteBuilding, inquireBuilding, editBuilding, getFloorPageQuery, addFloor, deleteFloor, editFloor } from '@/api/ms_stu_pro235/park_manage/building_management.js'
  export default {

    data() {
      return {

        initAxiosLoad: true,
        inquireForm: {
          parkName: undefined,
          buildingName: undefined,
        },

        // 控制查询逻辑
        isInquireControl: false,

        inquireFormRules: {
          parkName: [
            {
              validator: this.leastInputOne, trigger: 'blur'
            }
          ],
          buildingName: [
            {
              validator: this.leastInputOne, trigger: 'blur'
            }
          ]
        },

        // 控制table的加载
        tableLoad: true,

        tableData: [
          // {
          //   id: 1,
          //   parkName: "明诚广场",
          //   buildingName: "华夏大厦",
          //   remark: "1111",
          //   createTime: "2024-04-10 09:15:20",
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

        // 存储园区下拉数组
        parkSelect: [],

        // edit/new对话框
        dialogFormVisible: false,
        dialogForm: {
          parkId: undefined,
          buildingName: undefined,
          remark: undefined,
        },
        dialogFormRules: {
          parkId: [
            { required: true, message: '请输入园区名称', trigger: 'change' }
          ],
          buildingName: [
            { required: true, message: '请输入楼宇名称', trigger: 'blur' }
          ],

        },

        // 存储当前楼宇id为后面楼层新增/编辑使用
        curBuildingID: undefined,

        //楼层dialog
        floorDialogVisible: false,
        floorMultipleSelection: [],
        floorDialogdTableLoad: true,
        floorDialogdata: [
          // {
          //   id: 1,
          //   floorNum: '1',
          //   name: '111',
          //   createTime: '2024-04-10 09:15:20',
          //   createBy: '111',
          // },
        ],

        // 内嵌dialog add/edit楼层
        dialogModule: 'add',
        innerDialogVisible: false,
        innerDialogForm: {
          floorNum: undefined,
          name: undefined,
        },
        innerDialogFormRules: {
          floorNum: [
            { required: true, message: '请输入楼层', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '请输入楼层名称', trigger: 'blur' }
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

      leastInputOne(rule, value, callback) {
        if (!(this.inquireForm.parkName) && !(this.inquireForm.buildingName)) return callback(new Error('请至少输入一个查询数据'))
        callback()
      },

      // 封装查询/刷新分页
      async refreshPage(pagination) {
        const { parkName: zoneName, buildingName } = this.inquireForm
        // 判断是否开启了模糊查询
        let requestObj = this.isInquireControl && (zoneName !== undefined || buildingName !== undefined) ? { zoneName, buildingName, ...pagination } : { ...pagination }
        try {
          this.tableLoad = true
          let result = await getPageQuery(requestObj)
          this.total = result.total
          this.tableData = result.rows.map(item => {
            return {
              // 没有接收园区id
              id: item.buildingId,
              parkName: item.zoneName,
              buildingName: item.buildingName,
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
        // 获得园区下拉框
        getParkSelect().then(res => {
          this.parkSelect = res.data
        }).catch(err => {
          console.log(err);
        })

        try {
          await this.refreshPage(this.pagination)
        } catch (error) {
          console.log(error);
        }
        this.initAxiosLoad = false
      },

      handleInquire() {
        this.$refs['formRef'].validate(valid => {
          if (!valid) return
          this.isInquireControl = true
          this.refreshPage(this.pagination)
        })

      },

      // inquireForm重置
      handleReset() {
        this.$refs['formRef'].resetFields()
        this.isInquireControl = false
        this.refreshPage(this.pagination)
      },

      // 共用与楼层table一个勾选逻辑
      handelCellClick(tableRef, row, column, cell, event) {
        if (column.label == '操作') return
        let isExist = tableRef == 'tableDataRef' ? this.multipleSelection.some(item => item.id == row.id) : this.floorMultipleSelection.some(item => item.id == row.id)
        if (isExist) return this.$refs[tableRef].toggleRowSelection(row, false)
        this.$refs[tableRef].toggleRowSelection(row, true)

      },

      addBuilding() {
        this.module = 'add'
        this.dialogFormVisible = true

      },

      // 共用一个删除逻辑  
      async delLogic(isFlot, row) {
        try {
          let result = undefined
          switch (isFlot) {
            case 'batch-del':
              // 将对应的租户id提取出来
              let requestArr = this.multipleSelection.map(item => item.id)
              result = await deleteBuilding(requestArr)

              break;

            case 'del':
              result = await deleteBuilding(row.id)
              break;
          }
        } catch (error) {
          console.log(error);
        }
        this.$message.success('删除成功')
        this.refreshPage(this.pagination)
      },
      // 处理table编辑按钮
      handleEdit(row) {
        this.module = 'edit'
        this.dialogFormVisible = true
        inquireBuilding(row.id).then(res => {
          const data = res.data
          this.$nextTick(() => {
            this.dialogForm = {
              parkId: undefined,
              buildingId: data.buildingId,
              buildingName: data.buildingName,
              remark: data.remark
            }
          })
        }).catch(err => {
          console.log(err);

        })

      },



      // 处理dialog取消逻辑
      handleDialogCancel(ref) {
        if (!ref) return
        this.$refs[ref].resetFields()

        switch (ref) {
          case 'dialogFormRef':
            this.dialogFormVisible = false
            break;

          case 'floorDialogFormRef':
            this.innerDialogVisible = false
            break;
        }

      },

      handleSureBtn() {
        this.$refs['dialogFormRef'].validate(async (valid) => {
          if (!valid) return
          try {
            let result = undefined
            const { parkId: zoneId, buildingId, ...rest } = this.dialogForm
            let requestObj = {
              zoneId,
              ...rest
            }
            switch (this.module) {
              case 'add':
                result = await addBuillding(requestObj)
                this.$message.success('添加成功')
                break;

              case 'edit':
                requestObj = {
                  buildingId,
                  ...requestObj
                }
                result = await editBuilding(requestObj)
                this.$message.success('编辑成功')
                break;
            }
          } catch (error) {
            console.log(error);
          }
          this.dialogFormVisible = false
          this.isInquireControl = false
          this.refreshPage(this.pagination)
        })
      },

      handleSelectionChange(selection, key) {
        switch (key) {
          case 'building':
            this.multipleSelection = selection
            break;

          case 'floor':
            this.floorMultipleSelection = selection
            break;
        }


      },

      // 自定义校验规则
      handleConfirmPassword(rule, value, callback) {
        this.dialogForm.password === this.dialogForm.confirmPassword ? callback() : callback(new Error('两次密码输入不一致'))
      },

      handleCurrentChange(value) {
        this.pagination.pageNum = value
        this.refreshPage(this.pagination)

      },

      // 处理自定义的select
      handleSizeChange(value) {
        this.pagination.pageNum = 1
        this.pagination.pageSize = value
        this.refreshPage(this.pagination)
      },

      // 封装得到楼层列表接口
      getFloorList(id = undefined) {
        let requestObj = undefined
        if (id !== undefined) requestObj = { buildingId: id }
        this.floorDialogdTableLoad = true
        getFloorPageQuery(requestObj).then(res => {
          this.$nextTick(() => {
            this.floorDialogdata = res.data.map(item => {
              return {
                id: item.floorId,
                floorNum: item.floorNum,
                name: item.floorName,
                createTime: item.createTime,
                createBy: item.createBy
              }
            })
          })
          this.floorDialogdTableLoad = false

        }).catch(err => {
          console.log(err);
        })
      },

      // 处理楼层点击按钮
      handleFloorBtn(row) {
        this.floorDialogVisible = true
        // 将当前楼宇id存储下来
        this.curBuildingID = row.id
        this.getFloorList(row.id)
      },

      // 新建楼层
      handleAddFloor() {
        this.dialogModule = 'add'
        this.innerDialogVisible = true
      },

      // 编辑楼层
      handleEditFloor(row) {
        this.dialogModule = 'edit'
        this.innerDialogVisible = true
        this.$nextTick(() => {
          this.innerDialogForm = {
            floorId: row.id,
            floorNum: row.floorNum,
            name: row.name
          }
        })
      },

      // 删除楼层按钮
      async delFloor(key, row) {
        try {
          let result = undefined
          switch (key) {
            case 'batch-del':
              // 将对应的租户id提取出来
              let requestArr = this.floorMultipleSelection.map(item => item.id)
              result = await deleteFloor(requestArr)
              break;

            case 'del':
              result = await deleteFloor(row.id)
              break;
          }
        } catch (error) {
          console.log(error);
        }
        this.$message.success('删除成功')
        // 刷新楼层列表
        this.getFloorList(this.curBuildingID)
      },


      sureDialogBtn() {
        this.$refs['floorDialogFormRef'].validate(async (valid) => {
          if (!valid) return
          let result = undefined
          const { floorId, floorNum, name: floorName } = this.innerDialogForm
          let requestObj = {
            buildingId: this.curBuildingID,
            floorNum,
            floorName
          }
          switch (this.dialogModule) {
            case 'add':
              requestObj.buildingId = this.curBuildingID
              result = await addFloor(requestObj)
              this.$message.success('新建成功')
              break;

            case 'edit':
              requestObj.floorId = floorId
              result = await editFloor(requestObj)
              this.$message.success('编辑成功')

              break;
          }
          this.innerDialogVisible = false
          this.getFloorList(this.curBuildingID)
        })

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
      /* .table-container {} */

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

      .floor-dialog-container {

        /* 只更改楼层dialog的宽度 */
        ::v-deep .el-dialog {
          width: 70%;
        }

        /* .dialog-form-container {
          width: 80%;
          margin: 0 auto;
        } */

      }



      .edit-dialog-container {
        .dialog-form-container {
          width: 80%;
          margin: 0 auto;
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