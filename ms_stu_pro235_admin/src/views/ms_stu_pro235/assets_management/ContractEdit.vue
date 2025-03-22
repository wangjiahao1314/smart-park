<template>
  <div ref="pageRef" id="page-style">
    <div style=" padding: 30px; background-color: #ffffff;">
      <div class="steps-container">
        <el-steps class="steps-content" :active="activeIndex" finish-status="success">
          <el-step>
            <template #title>
              <div class="step-title-style">
                请填写基础信息
              </div>
            </template>
          </el-step>
          <el-step>
            <template #title>
              <div class="step-title-style">
                请填写费项信息
              </div>
            </template>
          </el-step>
          <el-step>
            <template #title>
              <div class="step-title-style">
                完成
              </div>
            </template>
          </el-step>
        </el-steps>
      </div>

      <div v-show="activeIndex == 0">
        <div class="common-container">
          <el-form ref="contractFormRef" class="contract-form-container" :model="contractForm"
            :rules="contractFormRules" label-width="140px" label-position="right">
            <el-form-item label="合同名称:" prop="contractName">
              <el-input v-model="contractForm.contractName" size="mini" placeholder='请输入合同名称'></el-input>
            </el-form-item>
            <el-form-item label="合同号:" prop="contractNumber">
              <el-input :value="contractForm.contractNumber" disabled size="mini"></el-input>
            </el-form-item>
            <el-form-item label="备注:" prop="remark">
              <el-input v-model="contractForm.remark" type="textarea" :rows="4" placeholder="请输入备注" resize='horizontal'>
              </el-input>
            </el-form-item>

          </el-form>
        </div>

        <el-divider content-position="left">乙方</el-divider>
        <el-button type="primary" size="mini" icon="el-icon-plus" @click="handleTenantryModule('tenantry')">
          导入租户信息
        </el-button>

        <div class="common-container">
          <el-form ref="tenantryFormRef" class="tenantry-form-container" :model="tenantryForm"
            :rules="tenantryFormRules" label-width="140px" label-position="right">
            <el-form-item label="租户名称:" prop="tenantryName">
              <el-input v-model="tenantryForm.tenantryName" size="mini" disabled placeholder='请输入租户名称'></el-input>
            </el-form-item>
            <el-form-item label="身份证号:" prop="identityCardNumber">
              <el-input v-model="tenantryForm.identityCardNumber" size="mini" disabled placeholder='身份证号'></el-input>
            </el-form-item>
            <el-form-item label="个人邮箱:" prop="mail">
              <el-input :value="tenantryForm.mail" disabled size="mini" placeholder='请输入个人邮箱'></el-input>
            </el-form-item>
            <el-form-item label="联系电话:" prop="phone">
              <el-input :value="tenantryForm.phone" disabled size="mini" placeholder='请输入联系电话'></el-input>
            </el-form-item>
            <el-form-item label="家庭地址:" prop="address">
              <el-input :value="tenantryForm.address" disabled size="mini" placeholder='请输入家庭地址'></el-input>
            </el-form-item>
          </el-form>
        </div>
        <el-divider content-position="left">选择租赁场地</el-divider>
        <el-button style="margin-bottom: 20px;" type="primary" size="mini" icon="el-icon-plus"
          @click="handleTenantryModule('room')">
          选择房间
        </el-button>
        <el-button class="default-btn" type="default" size="mini" @click="roomData = []">
          重置
        </el-button>

        <el-table :data="roomData" style="margin-bottom: 20px;">

          <el-table-column property="roomName" label="房间名称" min-width="10%" show-overflow-tooltip></el-table-column>
          <el-table-column property="parkName" label="所属园区" min-width="10%" show-overflow-tooltip></el-table-column>
          <el-table-column property="buildingName" label="所属楼宇" min-width="10%" show-overflow-tooltip></el-table-column>
          <el-table-column property="floorName" label="所属楼层" min-width="10%" show-overflow-tooltip></el-table-column>
          <el-table-column property="area" label="出租面积 (平方米)" min-width="10%" show-overflow-tooltip></el-table-column>

          <template slot="empty">
            <el-empty :image-size="Number(80)">
              <template slot="image">
                <img src="@/assets/images/envelope.png" alt="">
              </template>
            </el-empty>
          </template>
        </el-table>

        <div style="display: flex; justify-content: center;">
          <el-button style="margin-right: 10px;" type="default" @click="$router.push('/assets/contract')">返回</el-button>
          <el-button class="primary-btn" type="primary" @click="handleActiveIndex">
            下一步
          </el-button>
        </div>

        <!-- dialog -->
        <el-dialog width='70%' :title="module == 'tenantry'?'导入租户信息':'选择房间'" :visible.sync="commonDialogVisible"
          @close="handleCloneCommonDialog">

          <div v-show="module == 'tenantry'">
            <el-form ref="tenantryInfoDialogFormRef" :inline="true" :model="tenantryInfoDialogForm">
              <el-form-item label="租户名称:" style="margin-right: 30px;" prop="tenantryName">
                <el-input v-model="tenantryInfoDialogForm.tenantryName" size="mini" placeholder='请输入租户名称'
                  @keyup.enter.native="handleCommonInquire"></el-input>
              </el-form-item>

              <el-form-item label="联系电话:" style="margin-right: 30px;" prop="phone">
                <el-input v-model="tenantryInfoDialogForm.phone" size="mini" placeholder='请输入联系电话'
                  @keyup.enter.native="handleCommonInquire"></el-input>
              </el-form-item>


              <el-form-item>
                <el-button class="primary-btn" type="primary" size="mini" @click="handleCommonInquire">查询</el-button>
                <el-button class="default-btn" type="default" size="mini"
                  @click="$refs['tenantryInfoDialogFormRef'].resetFields()">重置</el-button>
              </el-form-item>
            </el-form>

            <el-table ref="tenantryInfoTableRef" :data="tenantryInfoDialogData"
              @current-change="(currentRow,oldCurrentRow)=>{handleTableCurChange(currentRow,oldCurrentRow,'tenantryInfoTableRef')}"
              @select="(selection,row)=>{handleCurrentRowChange(selection,row,'tenantryInfoTableRef')}"
              @select-all="(selection)=>{handleAllSelection(selection,'tenantryInfoTableRef')}">
              <el-table-column type="selection" :selectable="handleSelecTable" min-width="2%"></el-table-column>
              <el-table-column property="tenantryName" label="租户名称" min-width="8%"
                show-overflow-tooltip></el-table-column>
              <el-table-column property="identityCardNumber" label="身份证号码" min-width="14%"
                show-overflow-tooltip></el-table-column>
              <el-table-column property="mail" label="邮箱" min-width="15%" show-overflow-tooltip></el-table-column>
              <el-table-column property="phone" label="家庭地址" min-width="20%" show-overflow-tooltip></el-table-column>
              <el-table-column property="address" label="联系电话" min-width="13%" show-overflow-tooltip></el-table-column>
              <template slot=" empty">
                <el-empty :image-size="Number(80)">
                  <template slot="image">
                    <img src="@/assets/images/envelope.png" alt="">
                  </template>
                </el-empty>
              </template>
            </el-table>


            <div style="display: flex; justify-content: flex-end; margin-top: 20px;">
              <el-pagination background @current-change="handleCurrentChange" :current-page="tenantryPagination.pageNum"
                :page-sizes="[5]" :page-size="tenantryPagination.pageSize" layout="  prev, pager, next"
                :total="roomTotal">
              </el-pagination>
            </div>
          </div>

          <div v-show="module == 'room'">
            <el-form ref="roomDialogFormRef" :inline="true" :model="roomDialogForm">
              <el-form-item label="房间名称:" style="margin-right: 30px;" prop="roomName">
                <el-input v-model="roomDialogForm.roomName" size="mini" placeholder='请输入房间名称'
                  @keyup.enter.native="handleCommonInquire"></el-input>
              </el-form-item>

              <el-form-item label="所属楼宇:" style="margin-right: 30px;" prop="building">
                <el-input v-model="roomDialogForm.building" size="mini" placeholder='请输入所属楼宇名称'
                  @keyup.enter.native="handleCommonInquire"></el-input>
              </el-form-item>


              <el-form-item>
                <el-button class="primary-btn" type="primary" size="mini" @click="handleCommonInquire">查询</el-button>
                <el-button class="default-btn" type="default" size="mini"
                  @click="$refs['roomDialogFormRef'].resetFields()">重置</el-button>
              </el-form-item>
            </el-form>

            <el-table ref="roomTableRef" :data="roomDialogData"
              @current-change="(currentRow,oldCurrentRow)=>{handleTableCurChange(currentRow,oldCurrentRow,'roomTableRef')}"
              @select="(selection,row)=>{handleCurrentRowChange(selection,row,'roomTableRef')}"
              @select-all="(selection)=>{handleAllSelection(selection,'roomTableRef')}">
              <el-table-column :selectable="handleSelecTable" type="selection" min-width="2%"></el-table-column>
              <el-table-column property="roomName" label="房间名称" min-width="8%" show-overflow-tooltip></el-table-column>
              <el-table-column property="parkName" label="所属园区" min-width="14%" show-overflow-tooltip></el-table-column>
              <el-table-column property="buildingName" label="所属楼宇" min-width="15%"
                show-overflow-tooltip></el-table-column>
              <el-table-column property="floorName" label="所属楼层" min-width="20%"
                show-overflow-tooltip></el-table-column>
              <el-table-column property="area" label="出租面积 (平方米)" min-width="13%"
                show-overflow-tooltip></el-table-column>
              <el-table-column property="rentType" label="租金类型" min-width="13%" show-overflow-tooltip>
                <template #default="{row}">
                  <div>
                    {{getTeantState(row.rentType)}}
                  </div>
                </template>
              </el-table-column>
              <el-table-column property="rent" label="租金 (元)" min-width="13%" show-overflow-tooltip></el-table-column>
              <template slot="empty">
                <el-empty :image-size="Number(80)">
                  <template slot="image">
                    <img src="@/assets/images/envelope.png" alt="">
                  </template>
                </el-empty>
              </template>
            </el-table>


            <div style="display: flex; justify-content: flex-end; margin-top: 20px;">
              <el-pagination background @current-change="handleCurrentChange"
                :current-page="roomDialogPagination.pageNum" :page-sizes="[10, 15]"
                :page-size="roomDialogPagination.pageSize" layout="  prev, pager, next" :total="roomTotal">
              </el-pagination>
            </div>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="handleCloneCommonDialog">取 消</el-button>
            <el-button type="primary" @click="handleCommonSure">确 定</el-button>
          </span>
        </el-dialog>
      </div>

      <div v-show="activeIndex == 1">
        <div class="charges-info-container">
          <el-form ref="dateFormRef" :model="dateForm" :rules="dateFormRules" label-width="160px">
            <el-form-item label="签订日期:" prop="signDate">
              <el-date-picker style="width: 73%" v-model="dateForm.signDate" type="date" placeholder="请选择日期"
                :picker-options="handlePickerOptions('sign')" @change="(value)=>{handleDateChange(value,'sign-date')}">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="租赁期限:" prop="leaseEnd">

              <el-date-picker v-model="dateForm.leaseStart" type="date" placeholder="请选择日期"
                :picker-options="handlePickerOptions('start')" @change="(value)=>{handleDateChange(value)}">
              </el-date-picker>
              ~
              <el-date-picker v-model="dateForm.leaseEnd" type="date" placeholder="请选择日期"
                :disabled="dateForm.leaseStart == undefined" :picker-options="handlePickerOptions('end')"
                @change="(value)=>{handleDateChange(value,'end-date')}">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-radio-group v-model="dateForm.timeLimit" @input="handleDateSelect">
                <el-radio-button :disabled="dateForm.leaseStart == undefined? true:false" label="1年"></el-radio-button>
                <el-radio-button :disabled="dateForm.leaseStart == undefined? true:false" label="2年"></el-radio-button>
                <el-radio-button :disabled="dateForm.leaseStart == undefined? true:false" label="3年"></el-radio-button>
                <el-radio-button :disabled="dateForm.leaseStart == undefined? true:false" label="4年"></el-radio-button>
                <el-radio-button :disabled="dateForm.leaseStart == undefined? true:false" label="5年"></el-radio-button>
              </el-radio-group>
            </el-form-item>

          </el-form>
        </div>
        <div style="color: #b4a2a2;">固定租金</div>
        <div class="charges-info-container rent-container">

          <div class="rent-content-title">
            计算方式: 按单元面积&nbsp;&nbsp;&nbsp;租赁面积{{roomData.reduce((pre,cur)=>pre+=cur.area,0)}}平米
          </div>
          <el-form ref="rentFormRef" style="border: 1px solid #a0a0a0;" :model="rentForm" :rules="rentFormRules"
            label-width="120px">
            <div class="flex" style="border-bottom: 1px solid #a0a0a0;">
              <el-form-item style="border-right: 1px solid #a0a0a0; flex:1;" label="租金单价:" prop="rentPrice">

                <el-input-number v-model="rentForm.rentPrice" size="small" controls-position="right"
                  @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'rentPrice')}"
                  :min="0"></el-input-number>
                <el-select v-model="rentForm.rentType" style="width: 35%;" @change="handleRentTypeChange">
                  <el-option v-for="item in rentTypeDicts" :key="item.dictValue" :label="item.dictLabel"
                    :value="item.dictValue">
                  </el-option>
                </el-select>

              </el-form-item>
              <el-form-item style="flex:1;" label="日租金:" prop="dayRent">
                <el-input-number v-model="rentForm.dayRent" size="small" controls-position="right"
                  @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'dayRent')}"
                  :min="0"></el-input-number>
                <span> 元/天</span>
              </el-form-item>
            </div>

            <div class="flex" style="border-bottom: 1px solid #a0a0a0;">
              <el-form-item class="rent-calculation-mode" style="flex: 1;">
                <span>月租金计算方式: </span>
                <el-radio-group v-model="rentForm.rentCalculationMode" @input="handleRentCalculationMode">
                  <el-radio label="1">按固定租金</el-radio>
                  <el-radio label="2">按时间天数</el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item style="flex: 1;" label="月租金:" prop="monthRent">
                <div>
                  <el-input-number v-model="rentForm.monthRent" size="small" controls-position="right"
                    @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'monthRent')}"
                    :min="0"></el-input-number>
                  <span> 元/月</span>
                </div>

              </el-form-item>
            </div>

            <div class="flex-justify-center" style="border-bottom: 1px solid #a0a0a0;">
              <el-form-item label="管理费单价: " prop="managementFee">
                <el-input-number v-model="rentForm.managementFee" size="small" controls-position="right"
                  @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'managementFee')}"
                  :min="0"></el-input-number>
                <el-select v-model="rentForm.managementFeeType" style="width: 35%;">
                  <el-option v-for="item in managementFeeTypeDicts" :key="item.dictValue" :label="item.dictLabel"
                    :value="item.dictValue">
                  </el-option>
                </el-select>
              </el-form-item>
            </div>

            <div class="flex" style="border-bottom: 1px solid #a0a0a0;">
              <el-form-item style="flex: 1;" label="日管理费" prop="dayManagementFee">
                <el-input-number v-model="rentForm.dayManagementFee" size="small" controls-position="right"
                  @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'dayManagementFee')}"
                  :min="0"></el-input-number>
                <span> 元/天</span>
              </el-form-item>

              <el-form-item style="flex: 1;" label="月管理费:" prop="monthManagementFee">
                <div inert>
                  <el-input-number v-model="rentForm.monthManagementFee" size="small" controls-position="right"
                    @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'monthManagementFee')}"
                    :min="0"></el-input-number>
                  <span> 元28天</span>
                </div>

              </el-form-item>
            </div>

            <div class="flex-justify-center">
              <el-form-item label="押付方式: " prop="payRent">
                <span>押 </span>
                <el-input-number v-model="rentForm.paySecurityRent" size="small" controls-position="right"
                  @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'paySecurityRent')}"
                  :min="1"></el-input-number>
                <span> 付 </span>
                <el-input-number v-model="rentForm.payRent" size="small" controls-position="right"
                  @change="(currentValue,oldValue)=>{handleNumInputChange(currentValue,oldValue,'payRent')}"
                  :min="0"></el-input-number>
                <span> 元</span>
              </el-form-item>
            </div>

          </el-form>
          <div class="flex-justify-center" style="margin-top: 20px;">
            <el-button class="default-btn" type="default" @click="$router.push('/assets/contract')">返回</el-button>
            <el-button class="primary-btn" type="primary" :loading="saveLoading" @click="handleSaveBtn">保存
            </el-button>
            <el-button class="default-btn" type="default" @click="activeIndex--">上一步</el-button>
          </div>
        </div>
      </div>

      <div v-show="activeIndex == 2">
        <el-result icon="success" title="成功提示">
          <template #title>
            <div>
              操作成功
            </div>
          </template>
          <template slot="extra">
            <el-button type="primary" size="medium" @click="$router.push('/assets/contract')">返回</el-button>
          </template>
        </el-result>
      </div>

    </div>
  </div>
</template>
<script>
  // 导入封装的方法
  import { getSubmitDate } from '@/utils/ms_stu_pro235/public.js'

  export default {
    data() {
      return {
        // 步骤条激活下标
        activeIndex: 0,

        // 网页标题
        title: '',

        // 控制字典dictLoad
        dictLoad: false,

        // 控制axiox初始化是否完成
        initAxiosLoad: false,

        // 控制按钮加载
        submitLoading: false,

        // 存储租金类型字典值
        rentTypeDicts: [],
        // 合同form
        contractForm: {
          contractName: undefined,
          contractNumber: 1231231,
          remark: undefined,

        },
        contractFormRules: {
          contractName: [
            {
              required: true, message: '请输入合同名称', trigger: 'blur'
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

        // 租户form
        tenantryForm: {
          id: undefined,
          tenantryName: undefined,
          identityCardNumber: undefined,
          mail: undefined,
          phone: undefined,
          address: undefined,
        },

        tenantryFormRules: {
          tenantryName: [
            {
              required: true, message: '请选择租户', trigger: 'blur'
            }
          ],
          identityCardNumber: [
            {
              required: true, message: '请选择身份证号码', trigger: 'blur'
            }
          ],
          phone: [
            {
              required: true, message: '请选择联系号码', trigger: 'blur'
            }
          ],
          address: [
            {
              required: true, message: '请选择家庭地址', trigger: 'blur'
            }
          ],
        },

        // 共用同一个dialog
        commonDialogVisible: false,
        module: 'tenantry',

        // 存储勾选当前行的对象 两个dialog共用
        curRow: {},
        // 控制导入租户信息dialog
        tenantryInfoDialogForm: {
          tenantryName: undefined,
          phone: undefined,
        },
        tenantryInfoDialogData: [
          {
            id: 1,
            tenantryName: '张三',
            identityCardNumber: '123123112231231222',
            mail: '123123@qq.com',
            phone: '123123123',
            address: '打扫房间爱上了你草率',
          },
          {
            id: 2,
            tenantryName: '张三',
            identityCardNumber: '12312312312312',
            mail: '123123@qq.com',
            phone: '123123123',
            address: '打扫房间爱上了你草率',
          },
        ],
        tenantryPagination: {
          pageNum: 1,
          pageSize: 10
        },
        tenantryTotal: 0,

        // 房间dialog模块
        roomDialogForm: {
          roomName: undefined,
          building: undefined,
        },
        roomDialogData: [
          {
            id: 1,
            roomName: 'adad',
            parkName: '挖的啊啊',
            buildingName: '达到啊啊啊',
            floorName: '12',
            area: 123,
            rentType: '0',
            rent: 200,
            managementFee: '10',
            managementFeeType: '0'
          },
          {
            id: 2,
            roomName: 'www',
            parkName: '挖啊啊',
            buildingName: '达到啊啊啊',
            floorName: '02',
            area: 123,
            rentType: '1',
            rent: 150,
            managementFee: '1',
            managementFeeType: '1'
          },
          {
            id: 3,
            roomName: '11111',
            parkName: '挖的啊123',
            buildingName: '达到啊啊啊',
            floorName: '10',
            area: 123,
            rentType: '2',
            rent: 120,
            managementFee: '20',
            managementFeeType: '2'
          },
          {
            id: 4,
            roomName: 'dawdad',
            parkName: '挖的啊123',
            buildingName: '达到啊啊啊',
            floorName: '10',
            area: 123,
            rentType: '3',
            rent: 120,
            managementFee: '30',
            managementFeeType: '3'
          },
        ],

        roomDialogPagination: {
          pageNum: 1,
          pageSize: 10
        },

        roomTotal: 0,

        roomData: [
          // {
          //   id: 1,
          //   roomName: '2312',
          //   parkName: '挖的啊啊',
          //   buildingName: '达到啊啊啊',
          //   floorName: '12',
          //   area: 123,

          // },

        ],


        // 费项模块    
        // 控制监听器开关
        limitRentPriceWatch: false,
        limitDayRentWatch: false,
        limitMonthRentWatch: false,

        limitManagementFee: false,
        limitDayManagementFee: false,
        limitMonthManagementFee: false,


        // 用来控制日租金逻辑走向
        controlToMonthRent: false,
        controlToRentPrice: false,

        // 用来控制日管理费的逻辑走向
        controlToManagementFee: false,
        controlToMonthManagementFee: false,

        managementFeeTypeDicts: [],  //存储管理费单位字典数组

        dateForm: {
          signDate: undefined,
          leaseStart: undefined,
          leaseEnd: undefined,
          timeLimit: undefined
        },

        // 租赁总天数 //为下面表格计算使用
        leaseTotalDay: 0,
        leaseTotalMonth: undefined, // 一共住了几个月

        dateFormRules: {
          signDate: [
            {
              required: true, message: '请选择签订日期', trigger: 'change'
            }
          ],
          leaseEnd: [
            {
              required: true, message: "请选择租赁日期", trigger: "blur"
            },
            {
              validator: this.leaseDateRules, trigger: 'blur'
            },

          ]
        },

        rentForm: {
          rentPrice: undefined,
          rentType: undefined,
          dayRent: undefined,
          rentCalculationMode: '1',
          monthRent: undefined,

          managementFee: undefined,
          managementFeeType: undefined,
          dayManagementFee: undefined,
          monthManagementFee: undefined,

          paySecurityRent: undefined,
          payRent: undefined,

        },
        rentFormRules: {
          rentPrice: [
            {
              required: true, message: '请输入租金', trigger: 'blur'
            }
          ],
          dayRent: [
            {
              required: true, message: '请输入日租金', trigger: 'blur'
            }
          ],
          monthRent: [
            {
              required: true, message: '请输入月租金', trigger: 'blur'
            }
          ],
          managementFee: [
            {
              required: true, message: '请输入管理费单击', trigger: 'blur'
            }
          ],
          dayManagementFee: [
            {
              required: true, message: '请输入日管理费', trigger: 'blur'
            }
          ],
          monthManagementFee: [
            {
              required: true, message: '请输入月管理费', trigger: 'blur'
            }
          ],
          payRent: [
            {
              required: true, message: '请输入押付方式', trigger: 'blur'
            }
          ]
        },

        // 控制按钮的加载状态
        saveLoading: false


      }
    },


    computed: {
      getTeantState() {
        return (stateCode) => {
          const { dictLabel } = this.rentTypeDicts.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },

    },

    watch: {
      // 通过租金类型去自动生成租金
      'rentForm.rentType': {
        handler(newValue, oldValue) {
          // 一旦监听到rentForm.rentType的值变化为'0'/'1'就执行日租金自动生成
          if (newValue == undefined) return
          // 打开日租金监听器 
          this.limitDayRentWatch = true
          // 打开日租金监听器流向月租金的控制器
          this.controlToMonthRent = true
          this.rentForm.dayRent = this.basisTypeGetDayExpense(newValue, this.rentForm.rentPrice)
          // 被basisTypeGetDayExpense封装的逻辑
          // switch (newValue) {
          //   case '0':
          //     // 元/平米/天
          //     this.rentForm.dayRent = this.roomData.reduce((pre, cur) => pre += cur.area, 0) * this.rentForm.rentPrice
          //     break;

          //   case '1':
          //     // 元/平米/月
          //     this.rentForm.dayRent = (this.roomData.reduce((pre, cur) => pre += cur.area, 0) * this.rentForm.rentPrice / 30).toFixed(1)
          //     break;

          //   case '2':
          //     // 元/天
          //     this.rentForm.dayRent = this.rentForm.rentPrice
          //     break;

          //   case '3':
          //     // 元/月
          //     this.rentForm.dayRent = (this.rentForm.rentPrice / 30).toFixed(1)
          //     break;
          // }

        }
      },
      // 监听租金单价发生变化
      'rentForm.rentPrice': {

        handler(newValue, oldValue) {
          // 控制是否开启单价监听
          if (!this.limitRentPriceWatch || newValue == undefined) return
          console.log('开启了单价监听');
          this.limitRentPriceWatch = false
          // if (newValue == undefined) return

          this.rentForm.dayRent = this.basisTypeGetDayExpense(this.rentForm.rentType, newValue)
          // 被basisTypeGetDayExpense封装的逻辑
          // // 判断租金类型不同的类型有不同的计算方式
          // switch (this.rentForm.rentType) {

          //   case '0':
          //     //元/平米/天
          //     this.rentForm.dayRent = newValue * this.roomData.reduce((pre, cur) => pre += cur.area, 0)
          //     break;

          //   case '1':
          //     //元/平米/月
          //     this.rentForm.dayRent = (newValue * this.roomData.reduce((pre, cur) => pre += cur.area, 0) / 30).toFixed(1)
          //     break;

          //   case '2':
          //     //元/天
          //     this.rentForm.dayRent = newValue
          //     break;

          //   case '3':
          //     // 元/月
          //     this.rentForm.dayRent = (newValue / 30).toFixed(1)
          //     break;
          // }


        }
      },
      // 通过监听日租金去生成月租金 只要日租金一旦发生变化就根据rent.rentCalculationMode去更改月租金的值
      'rentForm.dayRent': {
        deep: true,
        handler(newValue, oldValue) {
          // 控制是否开启租金监听
          if (!this.limitDayRentWatch || newValue == undefined) return
          console.log('开启了日租金监听');
          //关闭日租金监听
          this.limitDayRentWatch = false
          // if (newValue == undefined) return
          // 通过判断设置的控制bool值来决定逻辑走向  //根据日租金反推单价
          if (this.controlToRentPrice) {
            // 关闭控制流向逻辑
            this.controlToRentPrice = false
            // 根据日租金反推单价
            this.rentForm.rentPrice = this.basisDayFeeGetPrice(this.rentForm.rentType, newValue)
            // 被basisDayFeeGetPrice封装的逻辑  根据日租金/日管理费反推单价
            // switch (this.rentForm.rentType) {
            //   case '0':
            //     this.rentForm.rentPrice = (newValue / this.roomData.reduce((pre, cur) => pre += cur.area, 0)).toFixed(1)
            //     break;

            //   case '1':
            //     this.rentForm.rentPrice = (newValue * 30 / this.roomData.reduce((pre, cur) => pre += cur.area, 0)).toFixed(1)
            //     break;

            //   case '2':
            //     this.rentForm.rentPrice = newValue
            //     break;

            //   case '3':
            //     this.rentForm.rentPrice = (newValue * 30).toFixed(1)
            //     break;
            // }
          }
          if (this.controlToMonthRent) {
            // 关闭控制流向逻辑
            this.controlToMonthRent = false
            // 修改月租金
            let monthRent = undefined
            switch (this.rentForm.rentCalculationMode) {
              case '1':
                monthRent = newValue * 30
                break;

              case '2':
                // 防止用户没有选择租赁日期直接点击 下面计算需要用到总天数
                if (this.leaseTotalDay == 0) return this.$message.warning('请先选择完租赁日期')
                // 已经选完租赁日期
                // 月租金算法 总租金 / 住了几个月
                monthRent = (newValue * this.leaseTotalDay / this.leaseTotalMonth).toFixed(1)
                console.log('租赁天数:', this.leaseTotalDay);
                console.log('住了几个月:', this.leaseTotalMonth);
                console.log('总租金:', newValue * this.leaseTotalDay);

                break;
            }
            this.rentForm.monthRent = monthRent
          }


        }
      },

      // 监听月租金 当月租金发生变化的时候 去修改日租金的值
      'rentForm.monthRent': {
        deep: true,
        handler(newValue, oldValue) {
          // 控制是否开启租金监听
          if (!this.limitMonthRentWatch || newValue == undefined) return
          console.log('开启了月租金监听');
          this.limitMonthRentWatch = false
          // 打开日租金Watch中的修改单价控制器
          this.limitRentPriceWatch = true
          // 判断选择月租金的计算方式
          switch (this.rentForm.rentCalculationMode) {
            // 根据月租金的值去修改日租金
            case '1':
              this.rentForm.dayRent = (newValue / 30).toFixed(1)
              break;

            case '2':
              if (this.leaseTotalDay == 0) return this.$message.warning('请先选择租赁日期')
              // 已选择日期 月反推日
              this.rentForm.dayRent = (newValue * this.leaseTotalMonth / this.leaseTotalDay).toFixed(1)
              break;
          }

        }
      },
      // 通过监听管理费类型(物业费单位) 来说自动生成日管理费
      'rentForm.managementFeeType': {
        handler(newValue, oldValue) {
          // 一旦监听到rentForm.rentType的值变化为'0'/'1'就执行日租金自动生成
          console.log('开启管理费类型监听器');

          if (newValue == undefined) return
          // 打开日管理费监听器 
          this.limitDayManagementFee = true
          // 打开流向月管理费的控制器
          this.controlToMonthManagementFee = true
          // 用basisTypeGetDayExpense方法封装了根据类型拿到对应日租金或者日管理费
          this.rentForm.dayManagementFee = this.basisTypeGetDayExpense(newValue, this.rentForm.managementFee)
          // 被basisTypeGetDayExpense封装的逻辑
          // switch (newValue) {
          //   case '0':
          //     // 元/平米/天
          //     this.rentForm.dayManagementFee = this.roomData.reduce((pre, cur) => pre += cur.area, 0) * this.rentForm.managementFee
          //     break;

          //   case '1':
          //     // 元/平米/月
          //     this.rentForm.dayManagementFee = (this.roomData.reduce((pre, cur) => pre += cur.area, 0) * this.rentForm.managementFee / 30).toFixed(1)
          //     break;

          //   case '2':
          //     // 元/天
          //     this.rentForm.dayManagementFee = this.rentForm.managementFee
          //     break;

          //   case '3':
          //     // 元/月
          //     this.rentForm.dayManagementFee = (this.rentForm.managementFee / 30).toFixed(1)
          //     break;
          // }

        }
      },

      // 监听管理费单价
      'rentForm.managementFee': {
        handler(newValue, oldValue) {
          if (!this.limitManagementFee || newValue == undefined) return
          this.limitManagementFee = false
          // 根据管理费类型(物业费单位)和单价得到日管理费
          this.rentForm.dayManagementFee = this.basisTypeGetDayExpense(this.rentForm.managementFeeType, newValue)
        }
      },

      //监听日管理费
      'rentForm.dayManagementFee': {
        handler(newValue, oldValue) {
          if (!this.limitDayManagementFee || newValue == undefined) return
          this.limitDayManagementFee = false
          // 流向管理费单价逻辑
          if (this.controlToManagementFee) this.rentForm.managementFee = this.basisDayFeeGetPrice(this.rentForm.managementFeeType, newValue)
          if (this.controlToMonthManagementFee) {
            // 已选择日期  根据日管理费得到月管理费
            this.rentForm.monthManagementFee = newValue * this.leaseTotalDay
          }
        }
      },

      // 监听月管理费
      'rentForm.monthManagementFee': {
        handler(newValue, oldValue) {
          if (!this.limitMonthManagementFee || newValue == undefined) return
          this.limitMonthManagementFee = false
          if (this.leaseTotalDay == 0) return this.$message.warning('请先选择租赁日期')
          // 已选择租赁日期  根据月管理费得到日管理费 日管理费 = 月管理费 * 住了几个月 / 住了几天
          this.rentForm.dayManagementFee = (newValue * this.leaseTotalMonth / this.leaseTotalDay).toFixed(1)
        }
      },

      // 监听租赁结束日期的变化 防止用户先填空了表格模块
      'dateForm.leaseEnd': {
        handler(newValue, oldValue) {
          if (newValue == undefined) return
          // 月租金
          let monthRent = undefined
          // 租赁总天数
          let leaseTotalDay = ((newValue.getTime() + 1000 * 60 * 60 * 24) - this.dateForm.leaseStart.getTime()) / (1000 * 60 * 60 * 24)
          // 修改租赁总天数
          this.leaseTotalDay = leaseTotalDay
          //住了几个月 如果只租45天(对30取余!=0)则+1就是两个月,总租金除以月份按月交钱
          let leaseTotalMonth = leaseTotalDay % 30 == 0 ? parseInt(leaseTotalDay / 30) : parseInt(leaseTotalDay / 30) + 1
          // 存储住了几个月
          this.leaseTotalMonth = leaseTotalMonth
          // 如果当用户先点击了切换月租金计算方式按钮 按钮模块会引导用户先去选择日期 这个switch是为当用户选择日期之后数据直接回显
          switch (this.rentForm.rentCalculationMode) {
            case '1':
              monthRent = this.rentForm.dayRent * 30
              break;
            case '2':
              // 处理实际天数的月租金
              // 监听的时候不能直接拿data里面的数据,因为方法写在下面,数据还没有更新
              // console.log(this.leaseTotalDay);
              // console.log(new Date(newValue.getTime() - this.dateForm.leaseStart.getTime()).getDate());
              console.log('租赁天数:', this.leaseTotalDay);
              console.log('租赁了几个月', leaseTotalMonth);
              // 月租金计算方式:总租金 / 租赁分月(对30取余不满进1)
              console.log('总租金:', this.rentForm.dayRent * this.leaseTotalDay);
              monthRent = (this.rentForm.dayRent * this.leaseTotalDay / this.leaseTotalMonth).toFixed(1)

              break;
          }
          this.rentForm.monthRent = monthRent
          console.log('月租金:', this.rentForm.monthRent);

          // 选择完日期之后拿到月管理费
          this.rentForm.monthManagementFee = (this.rentForm.dayManagementFee * this.leaseTotalDay / this.leaseTotalMonth).toFixed(1)

        }
      },

    },

    created() {
      console.log(this.$route.params);
      console.log(this.$route.query);
      // 更改标题
      this.title = this.$route.params.title
      //根据时间戳生成合同编码和4个随机数
      this.contractForm.contractNumber = new Date().getTime().toString() + Math.floor(Math.random() * 10000).toString()
      this.getAllDicts()
      this.getInitAxiosData()
    },
    mounted() {
    },
    methods: {

      // 封装根据租金类型/管理费类型(物业费单位)类型得到日租金或者日管理费
      //feeUnit费用单位 price日租金或者日管理的单价  
      basisTypeGetDayExpense(feeUnit, price) {
        let dataFeeValue = undefined
        switch (feeUnit) {
          case '0':
            // 元/平米/天
            dataFeeValue = this.roomData.reduce((pre, cur) => pre += cur.area, 0) * price
            break;

          case '1':
            // 元/平米/月
            dataFeeValue = (this.roomData.reduce((pre, cur) => pre += cur.area, 0) * price / 30).toFixed(1)
            break;

          case '2':
            // 元/天
            dataFeeValue = price
            break;

          case '3':
            // 元/月
            dataFeeValue = (price / 30).toFixed(1)
            break;
        }
        return dataFeeValue
      },

      // 封装根据日租金/日管理费反推单价逻辑
      // feeUnit费用单位  dayFee日租金/日管理费
      basisDayFeeGetPrice(feeUnit, dayFee) {
        let dataPriceValue = undefined
        switch (feeUnit) {
          case '0':
            dataPriceValue = (dayFee / this.roomData.reduce((pre, cur) => pre += cur.area, 0)).toFixed(1)
            break;

          case '1':
            dataPriceValue = (dayFee * 30 / this.roomData.reduce((pre, cur) => pre += cur.area, 0)).toFixed(1)
            break;

          case '2':
            dataPriceValue = dayFee
            break;

          case '3':
            dataPriceValue = (dayFee * 30).toFixed(1)
            break;
        }
        return dataPriceValue
      },



      handleActiveIndex() {
        let isPassContract = null
        let isPassTenantry = null
        let ispassRoom = !this.roomData.length == 0
        this.$refs['contractFormRef'].validate(valid => {
          isPassContract = valid
        })
        this.$refs['tenantryFormRef'].validate(valid => {
          isPassTenantry = valid

        })
        if (!isPassContract) return
        if (!isPassTenantry) return this.$message.warning('请导入租户信息')
        if (!ispassRoom) return this.$message.warning('请选择租赁场地')
        if (!(isPassContract && isPassTenantry && ispassRoom)) return

        this.$refs['pageRef'].scrollIntoView({
          behavior: 'smooth', // 平滑滚动
          block: 'start' // 元素顶部与视口顶部对齐
        })
        this.activeIndex += 1
      },

      // 只有当全部成功时才会去更改骨架屏的load的其中一个,还有初始请求的axios的数据
      getAllDicts() {
        Promise.all([this.getDicts('park_rent_type'), this.getDicts('park_property_unit')]).then(res => {

          this.rentTypeDicts = res[0].data
          this.managementFeeTypeDicts = res[1].data
          console.log(res);

          //更改骨架屏中的其中一个load
          this.dictLoad = true
        }).catch(err => {
          console.log(err);
          this.$message.error('请刷新页面,如不行,请稍后再试')

        })
      },

      getInitAxiosData() {
        // 更改页面标题
        this.$store.dispatch('settings/setTitle', this.title == 'contractNew' ? '合同创建' : this.title == 'contractEdit' ? '合同编辑' : this.title == 'contractAlter' ? '合同变更' : this.$store.settings.title)
        // 如果是新建页面没有传入id 则表示是新建合同,不用axios
        if (this.$route.query.id == undefined) return this.initAxiosLoad = true

        // 模拟数据 后面接axios时改成promise.all 
        setTimeout(() => {
          this.initAxiosLoad = true
        }, 1000);
      },

      // key=模式
      handleTenantryModule(key) {
        this.module = key
        this.commonDialogVisible = true
      },


      handleCurrentChange(value) {
        switch (this.module) {
          case 'tenantry':
            this.tenantryPagination.pageNum = value
            break;

          case 'room':
            this.roomDialogPagination.pageNum = value
            break;
        }
        // 切换分页的时候对当前行的数据进行重置
        this.curRow = {}
      },

      // 处理查询逻辑
      handleCommonInquire() {
        switch (this.module) {
          case 'tenantry':
            if (!(this.tenantryInfoDialogForm.tenantryName || this.tenantryInfoDialogForm.phone)) return this.$message.warning('请至少输入一个数据')

            break;

          case 'room':
            if (!(this.roomDialogForm.roomName || this.roomDialogForm.building)) return this.$message.warning('请至少输入一个数据')
            break;
        }

      },

      // 处理点击table行 而不是复选框逻辑
      handleTableCurChange(currentRow, oldCurrentRow, ref) {
        if (!(oldCurrentRow == undefined)) this.$refs[ref].toggleRowSelection(oldCurrentRow, false)
        this.$refs[ref].toggleRowSelection(currentRow, true)
        this.curRow = currentRow
        console.log('current');

      },

      // 处理当前已经选择过的数据复选框禁用
      handleSelecTable(row, index) {
        switch (this.module) {
          case 'tenantry':
            if (this.tenantryForm.id == undefined) return true
            if (this.tenantryForm.id == row.id) return false
            break;

          case 'room':
            if (this.roomData.length == 0) return true
            return !this.roomData.some(item => item.id == row.id)
            break;
        }
        return true
      },

      // 处理单选逻辑
      handleCurrentRowChange(selection, row, ref) {
        // 将当前行的数据给存储来下,已选择的数据大于二的时候,证明一定点击了第二个勾选框,则把存储下拉的当前行(上一行)通过toggleRowSelection取消勾选
        console.log(selection);

        if (selection.length > 1) {
          this.$refs[ref].toggleRowSelection(this.curRow, false)
        } else if (selection.length == 0) {
          this.curRow = {}
          console.log('已勾选的行:', this.curRow);
          return
        }
        this.curRow = row
        console.log('已勾选的行:', this.curRow);

      },

      // 对全选框的数据进行处理
      handleAllSelection(selection, ref) {
        this.$refs[ref].clearSelection()
        return
      },

      handleCloneCommonDialog() {
        switch (this.module) {
          case 'tenantry':
            this.commonDialogVisible = false

            if (!Object.keys(this.curRow).length == 0) this.$refs['tenantryInfoTableRef'].toggleRowSelection(this.curRow, false)
            this.$refs['tenantryInfoDialogFormRef'].resetFields()

            break;

          case 'room':
            this.commonDialogVisible = false
            if (!Object.keys(this.curRow).length == 0) this.$refs['roomTableRef'].toggleRowSelection(this.curRow, false)

            this.$refs['roomDialogFormRef'].resetFields()
            break;
        }

        this.curRow = {}
      },

      handleCommonSure() {
        // 将不需要的参数提取出来
        const { rentType = undefined, rent = undefined, managementFee = undefined, managementFeeType = undefined, ...rest } = this.curRow || {}
        console.log(rest);

        switch (this.module) {
          case 'tenantry':
            this.tenantryForm = rest
            break;

          case 'room':
            if (this.roomData.length >= 1) return this.$message.warning('只能选择一个房间')

            // 根据文档要求把选择房间拿到的租金和租金类型自动填充到费项信息
            this.rentForm.rentPrice = rent
            this.rentForm.rentType = rentType
            this.rentForm.managementFee = managementFee
            this.rentForm.managementFeeType = managementFeeType

            this.roomData.push(rest)
            break;
        }
        this.commonDialogVisible = false

      },


      // 费项信息逻辑


      // 封装更改时间快捷键函数 startDate时间戳 添加时间
      updateDateFn(startDate, addDate) {
        let endDate = new Date(startDate)
        endDate.setFullYear(endDate.getFullYear() + Number(addDate))
        if (isNaN(endDate.getDate())) {
          endDate.setMonth(endDate.getMonth + 1)
          endDate.setDate(1)
        }
        return endDate
      },

      // 处理租赁日期的自定义规则
      leaseDateRules(rule, value, callback) {
        if (this.dateForm.leaseStart == undefined) callback(new Error('请输入租赁开始日期'))
        if (this.dateForm.leaseEnd == undefined) callback(new Error('请输入租赁结束日期'))
        callback()
      },

      handleDateSelect(value) {
        let addDate = undefined
        switch (value) {
          case '1年':
            addDate = 1
            break;
          case '2年':
            addDate = 2
            break;
          case '3年':
            addDate = 3
            break;
          case '4年':
            addDate = 4
            break;
          case '5年':
            addDate = 5
            break;
        }
        this.dateForm.leaseEnd = this.updateDateFn(this.dateForm.leaseStart.getTime(), addDate)
        console.log('this.dateForm.leaseEnd', this.dateForm.leaseEnd);

      },



      // 控制签订日期变化的时候去修改开始租赁的值
      handleDateChange(value, key) {
        console.log(getSubmitDate(value));
        switch (key) {
          case 'sign-date':
            // 默认签订日期就是租赁开始日期
            this.dateForm.leaseStart = value

            break;

          case 'end-date':
            this.dateForm.timeLimit = undefined
            this.dateForm.leaseEnd = value
            this.dateForm.leaseEnd.setHours(23)
            this.dateForm.leaseEnd.setMinutes(59)
            this.dateForm.leaseEnd.setSeconds(59)
            console.log(getSubmitDate(this.dateForm.leaseEnd));

            // this.dateForm.leaseEnd = value
            // this.leaseTotalDay = ((this.dateForm.leaseEnd.getTime() + 3600 * 1000 * 24) - this.dateForm.leaseStart.getTime()) / (3600 * 1000 * 24)
            // console.log('租赁天数:', this.leaseTotalDay);

            break;
        }


      },

      // 处理签订和租赁开始的日期限制 isEnd双重限制,不然一旦选择开始租赁时间,禁用走不到下面的return,前面的disabled规则就乱了
      handlePickerOptions(key) {
        return {
          disabledDate: (time) => {
            // // 限制不能选择签订之前的日期 
            // if (isEnd && this.dateForm.leaseStart) return time.getTime() < this.dateForm.leaseStart.getTime(); 
            switch (key) {
              case 'sign':
                return time.getTime() < Date.now() - 3600 * 1000 * 24;

              case 'start':
                if (this.dateForm.signDate == undefined) return time.getTime() < Date.now() - 3600 * 1000 * 24;
                return time.getTime() < this.dateForm.signDate.getTime();

              case 'end':
                // 租赁日期要走的禁用逻辑
                if (this.dateForm.signDate == undefined) return time.getTime() < Date.now() - 3600 * 1000 * 24;
                return time.getTime() < this.dateForm.leaseStart.getTime();

            }

            // if (isEnd) return time.getTime() < this.dateForm.signDate.getTime();

            // return time.getTime() < Date.now() - 3600 * 1000 * 24;

          },
        }
      },

      // 处理租赁结束限制
      handleEndPickerOptions() {
        return {
          disabledDate: (time) => {
            // 限制不能选择租赁开始日期之前的日期
            if (this.dateForm.leaseStart) return time.getTime() < this.dateForm.leaseStart.getTime();

          },
        }
      },


      // 处理计步器输入变化
      handleNumInputChange(currentValue, oldValue, key) {
        console.log('currentValue', currentValue);

        switch (key) {
          case 'rentPrice':
            // 打开对应的监听器
            this.limitRentPriceWatch = true
            this.limitDayRentWatch = true
            // 打开日租金Watch流向月租金的控制器
            this.controlToMonthRent = true
            this.rentForm.rentPrice = currentValue
            break;

          case 'dayRent':
            // 打开对应的监听器
            this.limitDayRentWatch = true
            // 打开日租金流向单价和月租金的控制器
            this.controlToMonthRent = true
            this.controlToRentPrice = true
            this.rentForm.dayRent = currentValue
            break;

          case 'monthRent':
            // 打开对应的监听器
            this.limitDayRentWatch = true
            this.limitMonthRentWatch = true
            // 打开日租金管理器流向单价的控制器
            this.controlToRentPrice = true
            this.rentForm.monthRent = currentValue

            break;

          case 'managementFee':
            // 打开对应的监听器
            this.limitManagementFee = true
            this.limitDayManagementFee = true
            // 打开流向月管理费的控制器
            this.controlToMonthManagementFee = true
            this.rentForm.managementFee = currentValue
            break;

          case 'dayManagementFee':
            // 打开对应的监听器
            this.limitDayManagementFee = true
            // 打开流向月管理费的控制器和流向管理费单价控制器
            this.controlToMonthManagementFee = true
            this.controlToManagementFee = true
            this.rentForm.dayManagementFee = currentValue
            break;

          case 'monthManagementFee':
            // 打开对应的监听器
            this.limitMonthManagementFee = true
            this.limitDayManagementFee = true
            // 打开流向管理费单价控制器
            this.controlToManagementFee = true
            this.rentForm.monthManagementFee = currentValue
            break;
          case 'paySecurityRent':
            this.rentForm.paySecurityRent = currentValue
            break;
          case 'payRent':
            this.rentForm.payRent = currentValue
            break;
        }
      },


      // 处理费项信息保存逻辑
      handleRentInofSave() {
        let isPassDateForm = false
        let isPassRentForm = false
        this.$refs['dateFormRef'].validate(valid => {
          console.log(valid);
          isPassDateForm = valid
        })
        this.$refs['rentFormRef'].validate(valid => {
          console.log(valid);
          isPassRentForm = valid

        })
        if (!(isPassDateForm && isPassRentForm)) return
        console.log('通过校验');

      },

      // 处理租金单价的变化
      handleRentTypeChange(value) {
        this.rentForm.rentType = value
      },

      // 处理月租金的计算方式 改变月租金的金额
      handleRentCalculationMode(label) {

        switch (label) {
          case '1':
            this.rentForm.monthRent = (this.rentForm.dayRent * 30).toFixed(1)
            break;

          case '2':
            if (this.leaseTotalDay == 0) {
              this.rentForm.monthRent = undefined
              return this.$message.warning('请先选择完租赁日期')
            }

            // let leaseTotalMonth = this.leaseTotalDay % 30 == 0 ? parseInt(this.leaseTotalDay / 30) : parseInt(this.leaseTotalDay / 30) + 1
            // 月租金算法 总租金 / 住了几个月
            this.rentForm.monthRent = this.rentForm.dayRent * this.leaseTotalDay / this.leaseTotalMonth
            break;
        }
      },

      // 处理费项模块的保存按钮
      handleSaveBtn() {
        let isPassDateForm = false
        let isPassRentForm = false
        this.$refs['dateFormRef'].validate(valid => {
          isPassDateForm = valid
        })
        this.$refs['rentFormRef'].validate(valid => {
          isPassRentForm = valid

        })

        if (!isPassDateForm || !isPassRentForm) return

        // 打开按钮加载状态
        this.saveLoading = true

        //注意:是合同编辑还是合同变更可以根据title来进行区分
        console.log(this.title);

        // 发起提交接口
        setTimeout(() => {
          console.log('发起保存按钮提交接口');
          // 关闭按钮加载
          this.saveLoading = false
          this.$message.success('保存成功')
          this.activeIndex++
        }, 500);
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

    .steps-container {
      width: 96%;

      .steps-content {

        /* 根据模式更改step样式 */
        ::v-deep .el-step__head.is-success .el-step__line {
          border-color: rgba(17, 146, 254, 0.7) !important;
          background-color: rgba(17, 146, 254, 0.7) !important;
        }

        ::v-deep .el-step__main {
          position: absolute;
          z-index: 2;
          top: -8px;
          left: 24px;
          width: 120px;
          height: 38px;
          line-height: 38px;
          background-color: #fff;
        }



        ::v-deep .el-step__head.is-process .el-step__icon {
          font-size: 10px;
          color: #fff;
          background-color: #1093fe;
          border: 1px solid #1093fe;
        }

        ::v-deep .el-step__head.is-success .el-step__icon {
          font-size: 10px;
          color: rgba(17, 146, 254, 0.7);
          border: 1px solid rgba(17, 146, 254, 0.7);
        }




        ::v-deep .el-step__title.is-success .step-title-style {
          color: #a2a2a2;
        }

        .step-title-style {
          padding-left: 5px;
        }

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

    .common-container {
      width: 70%;
      margin: auto;
      padding: 20px;


    }

    /* 更改页码样式 */
    ::v-deep .el-pagination .active {
      border: 1px solid #1790fd;
      color: #1790fd;
    }

    ::v-deep .el-pagination .el-pager .number {
      background-color: #fff;
    }

    ::v-deep .el-pagination .btn-prev,
    ::v-deep .el-pagination .btn-next {
      background-color: #fff;
      border: 1px solid #eeeeee;
    }


    .charges-info-container {

      padding: 20px;
      width: 70%;
      margin: 20px auto;




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
    }

    ::v-deep .rent-container .el-form-item {
      padding: 20px 0px;
      margin-bottom: 0px !important;
    }



    .rent-container {

      ::v-deep .button-container .el-form-item__content {
        margin-left: 0px !important;
      }

      ::v-deep .rent-calculation-mode .el-form-item__content {
        margin-left: 0px !important;
        padding-left: 20px;
      }

      .rent-content-title {

        width: 100%;
        height: 80px;
        color: #a0a0a0;
        border: 1px solid #a0a0a0;
        border-bottom: none;
        text-align: center;
        line-height: 80px;
        background-color: #f7f7f7;
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