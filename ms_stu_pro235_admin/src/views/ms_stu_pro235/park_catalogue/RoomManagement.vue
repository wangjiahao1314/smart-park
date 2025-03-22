<template>
  <div id="page-style">

    <el-skeleton style="width: 100%;" :loading="(dictLoad || initAxiosLoad)" animated :throttle="100">
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
        <div class="park-container" style="padding: 20px; background-color: #fff;">

          <div style="display: flex; justify-content: space-between;">
            <el-form ref="inquireFormRef" :inline="true" :model="inquireForm">
              <el-form-item label="所属园区:" prop="parkId">
                <el-select v-model="inquireForm.parkId" size="mini" placeholder="请选择园区" style="width: 130px;">
                  <el-option v-for="item in parkArr" :key="item.parkId" :label="item.value" :value="item.parkId">
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="所属楼宇:" prop="buildingName">
                <el-input v-model="inquireForm.buildingName" size="mini" placeholder='请输入楼宇名称'
                  @keyup.enter.native="handleInquire" style="width: 130px;"></el-input>
              </el-form-item>
              <el-form-item label="房间名称:" prop="roomName">
                <el-input v-model="inquireForm.roomName" size="mini" placeholder='请输入房间名称'
                  @keyup.enter.native="handleInquire" style="width: 130px;"></el-input>
              </el-form-item>


              <el-form-item label="租赁状态:" prop="leaseStatus">
                <el-radio-group v-model="inquireForm.leaseStatus" class="radio-group" size="small">
                  <el-radio-button label="已租" @click.native.prevent="handleRadioBtn"></el-radio-button>
                  <el-radio-button label="未租" @click.native.prevent="handleRadioBtn"></el-radio-button>
                </el-radio-group>
              </el-form-item>

              <el-form-item style="margin-left: 30px;">
                <el-button class="primary-btn" type="primary" size="mini" @click="handleInquire">查询</el-button>
                <el-button class="default-btn" type="default" size="mini" @click="handleReset">重置</el-button>
              </el-form-item>

            </el-form>

            <el-radio-group v-model="showModule" size="mini" style="margin-right: 2%;padding-top: 5px;"
              @input="handleShowChange">
              <el-radio-button label="列表模式"></el-radio-button>
              <el-radio-button label="房态模式"></el-radio-button>
            </el-radio-group>

          </div>

          <div style="padding-bottom: 20px;">
            <el-button class="primary-btn" style="margin-right: 10px;" type="primary" size="mini" icon="el-icon-plus"
              @click="addRoom">新建</el-button>

            <el-popconfirm v-if="multipleSelection.length>0" @confirm="delLogic('batch-del')" title="是否删除所选中的内容"
              placement="top">
              <el-button slot="reference" class="danger-btn" type="danger" size="mini"
                icon="el-icon-delete-solid">删除</el-button>
            </el-popconfirm>
          </div>

          <div v-show="showModule==='列表模式'">


            <el-skeleton :loading="listModuleLoad" animated>
              <template slot="template">
                <el-skeleton-item style="width: 100%; height: 470px;" variant="rect" />
              </template>
              <template>
                <div class="table-container">
                  <el-table v-loading="tableLoad" ref="tableDataRef" highlight-selection-row
                    @cell-click="(row, column, cell, event)=>{handelCellClick('tableDataRef',row, column, cell, event)}"
                    :data="tableData" @selection-change="handleSelectionChange" style="width: 100%">
                    <el-table-column type="selection" min-width="2%">
                    </el-table-column>
                    <el-table-column prop="parkName" label="所属园区" min-width="10%" show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column prop="roomName" label="房间名称" min-width="10%"
                      show-overflow-tooltip></el-table-column>
                    <el-table-column prop="buildingName" label="所属楼宇" min-width="10%"
                      show-overflow-tooltip></el-table-column>
                    <el-table-column prop="floorName" label="所属楼层" min-width="10%"
                      show-overflow-tooltip></el-table-column>
                    <el-table-column prop="area" label="面积 (平方米)" min-width="10%"
                      show-overflow-tooltip></el-table-column>
                    <el-table-column prop="leaseState" label="租赁状态" min-width="8%">
                      <template #default="{row}">
                        <div :class="row.leaseState == '0'?'success-style':'info-style'" style="width: 44px;">
                          {{getLeaseState(row.leaseState)}}
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column prop="rent" label="租金 (元)" min-width="10%" show-overflow-tooltip></el-table-column>
                    <el-table-column prop="rentType" label="租金类型" min-width="10%" show-overflow-tooltip>
                      <template #default="{row}">
                        <div>
                          {{getRentType(row.rentType)}}
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column prop="shelfTime" label="上架时间" min-width="10%" show-overflow-tooltip>
                      <template #default={row}>
                        <div v-if="row.isShelf == '1'">
                          {{row.shelfTime}}
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column prop="isShelf" label="是否上架" min-width="10%">
                      <template #default="{row}">
                        <div>
                          <el-switch v-model="row.isShelf" :disabled="controlSwitchDisabled(row.leaseState)"
                            active-value="1" inactive-value="0" @change="(value)=>{handleSwitchChange(value,row)}">
                          </el-switch>
                        </div>
                      </template>
                    </el-table-column>

                    <el-table-column label="操作" min-width="15%">
                      <template #default="{row}">
                        <div>
                          <el-button type="text" size="mini" @click="handleEdit(row)">编辑</el-button>

                          <span style="color: #dfdfdf;">|</span>

                          <el-popconfirm @confirm="delLogic('del',row)" title='是否确实删除该房间？' placement="top">
                            <el-button slot="reference" type="text" size="mini">删除</el-button>
                          </el-popconfirm>

                          <span style="color: #dfdfdf;">|</span>

                          <el-button type="text" size="mini" @click="handleOpenDrawer(row)">记录</el-button>
                          <!-- 抽屉组件 -->
                          <el-drawer size="50%" :visible.sync="drawerVisible">
                            <template #default>

                              <el-table :data="drawerTableData" style="width: 100%;">
                                <el-table-column prop="state" label="租户状态" fixed="left" width="100">
                                  <template #default="{row}">
                                    <div :class="row.state == '1'?'success-style':'info-style'" style="width: 44px;">
                                      {{getHouseState(row.state)}}
                                    </div>
                                  </template>
                                </el-table-column>
                                <el-table-column prop="name" label="租户名称" fixed="left" width="100">
                                </el-table-column>
                                <el-table-column label="入住时间" width="200">
                                  <template #default="{row}">
                                    <div>
                                      {{row.checkInTime}} - {{row.returnTime == ''?'至今':row.returnTime}}
                                    </div>
                                  </template>
                                </el-table-column>
                                <el-table-column prop="identityCardNumber" label="身份证号码" width="200">
                                </el-table-column>
                                <el-table-column prop="mail" label="邮箱" width="180"></el-table-column>
                                <el-table-column prop="phone" label="联系电话" width="120"></el-table-column>
                                <el-table-column label="家庭住址" width="250">
                                  <template #default="{row}">
                                    <div>
                                      {{row.homeArea}}+{{row.addressDetails}}
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

                            </template>

                            <template #title>
                              <div>
                                <span style="font-size: 20px;">{{row.roomName}} {{row.buildingName}}
                                  {{row.floorName}}层</span>
                              </div>
                            </template>
                          </el-drawer>
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
                        <el-option v-for="(item,index) in pageSizeArr" :key="index" :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>

                    </el-pagination>
                  </div>
                </div>
              </template>
            </el-skeleton>


          </div>
          <div v-show="showModule==='房态模式'">

            <el-skeleton :loading="roomModuleLoad" animated>
              <template slot="template">
                <el-skeleton :rows="13" animated />
              </template>
              <template>
                <div v-for="item in tableData" class="park-container">
                  <h3>{{item.parkName}}</h3>
                  <div v-for="cur in item.buildingArr" class="building-container">
                    <div v-for="nape in cur.floorArr" class="floor-container">
                      <!-- <div style="padding: 10px; background-color: #fafcfb;">
                        {{cur.buildingName}} {{nape.floorName}}楼
                      </div>
                      <div class="room-container">
                        <div v-for="pre in nape.roomArr" class="card"
                          :class="[{'leased-style':getLeaseState(pre.leaseState) == '在租'},{'unchartered-style':getLeaseState(pre.leaseState) == '未租'}]">
                          <div>{{pre.roomName}}</div>
                          <div style="color: #969393ee;">{{pre.area}}m<sup>2</sup> -
                            {{pre.rent}}{{getRentType(pre.rentType)}}</div>
                          <div v-if="getLeaseState(pre.leaseState) == '未租'" class="vacancy-day-container">
                            <img src="@/assets/images/fire.png" alt="">
                            空置10<span style="color: red;">天</span>
                          </div>
                        </div>
                      </div> -->
                      <el-collapse>
                        <el-collapse-item v-for="(nape,index) in cur.floorArr" class="floor-container" :key="index">
                          <template slot="title">

                            {{cur.buildingName}} {{nape.floorName}}楼

                          </template>
                          <div class="room-container">
                            <div v-for="pre in nape.roomArr" class="card" @click="handleCard(pre.id)"
                              :class="[{'leased-style':getLeaseState(pre.leaseState) == '在租'},{'unchartered-style':getLeaseState(pre.leaseState) == '未租'}]">
                              <div>{{pre.roomName}}</div>
                              <div style="color: #969393ee;">{{pre.area}}m<sup>2</sup> -
                                {{pre.rent}}{{getRentType(pre.rentType)}}</div>
                              <div v-if="getLeaseState(pre.leaseState) == '未租'" class="vacancy-day-container">
                                <img src="@/assets/images/fire.png" alt="">
                                空置{{pre.vacantDays}}<span style="color: red;">天</span>
                              </div>
                            </div>
                          </div>

                        </el-collapse-item>

                      </el-collapse>
                    </div>


                  </div>
                </div>
              </template>
            </el-skeleton>

          </div>

        </div>
      </template>
    </el-skeleton>
  </div>
</template>
</el-skeleton>

</div>
</template>
<script>
  //导入API
  import { getListPageQuery, getParkSelect, getRoomPageQuery, editsShelfState, deleteRoom, getRoomLogs } from '@/api/ms_stu_pro235/park_manage/room_management.js'

  export default {

    data() {
      return {
        inquireForm: {
          parkId: undefined,
          buildingName: undefined,
          leaseStatus: undefined,
          roomName: undefined,
        },

        // 控制查询逻辑
        isInquireControl: false,

        // 显示模式
        showModule: "列表模式",

        tableData: [
          // 列表
          // {
          //   id: 1,
          //   parkName: "明诚广场",
          //   roomName: "帝豪",
          //   buildingName: "华建大厦",
          //   floorName: "23",
          //   area: 170,
          //   leaseState: "1",  //1未租  0在租
          //   rent: "20000",
          //   rentType: "0",
          //   shelfTime: "2024-04-09",
          //   isShelf: 1,  //1上架  0下架
          // },

          // 房态
          // [
          // {
          //   parkName: '明诚广场',
          //     buildingArr: [
          //       {
          //         buildingName: '华建大厦',
          //         floorArr: [
          //           {
          //             floorName: '1',
          //             roomArr: [
          //               { id: '1', roomName: '天地会', area: '120', rent: '1213', leaseState: '1', rentType: '0', vacantDays: '10', },
          //               { id: '2', roomName: '帝豪', area: '130', rent: '2013', leaseState: '1', rentType: '0', vacantDays: '5', },
          //               { id: '3', roomName: '封神', area: '160', rent: '3013', leaseState: '0', rentType: '3', vacantDays: '5', },
          //               { id: '4', roomName: '封神', area: '160', rent: '3013', leaseState: '0', rentType: '3', vacantDays: '5', },
          //               { id: '5', roomName: '封神', area: '160', rent: '3013', leaseState: '0', rentType: '3', vacantDays: '5', },
          //               { id: '6', roomName: '封神', area: '160', rent: '3013', leaseState: '0', rentType: '3', vacantDays: '5', },
          //               { id: '7', roomName: '封神', area: '160', rent: '3013', leaseState: '0', rentType: '3', vacantDays: '5', },
          //             ]
          //           },
          //           {
          //             floorName: '2',
          //             roomArr: [
          //               { id: '8', roomName: '天地会', area: '120', rent: '1213', leaseState: '1', rentType: '0', vacantDays: '10', },
          //               { id: '9', roomName: '帝豪', area: '130', rent: '2013', leaseState: '1', rentType: '0', vacantDays: '5', },
          //               { id: '10', roomName: '封神', area: '160', rent: '3013', leaseState: '0', rentType: '3', vacantDays: '5', },
          //               { id: '11', roomName: '封神', area: '160', rent: '3013', leaseState: '0', rentType: '3', vacantDays: '5', },
          //               { id: '12', roomName: '封神', area: '160', rent: '3013', leaseState: '0', rentType: '3', vacantDays: '5', },
          //               { id: '13', roomName: '封神', area: '160', rent: '3013', leaseState: '0', rentType: '3', vacantDays: '5', },
          //               { id: '14', roomName: '封神', area: '160', rent: '3013', leaseState: '0', rentType: '3', vacantDays: '5', },
          //             ]
          //           },
          //         ]
          //       }
          //     ]
          // }
          // ]
        ],
        // 存储字典数组
        leaseStateDict: [],
        rentTypeDict: [],
        houseStateDict: [],
        // 控制字典加载及axios初始化加载
        dictLoad: true,
        initAxiosLoad: true,

        // 控制列表和房态模块的骨架屏
        roomModuleLoad: false,
        listModuleLoad: false,
        // 存储所属园区下拉数组数据
        parkArr: [
          // {
          //   parkId: '1',
          //   value: '明诚广场'
          // },

        ],

        // 控制当点击切换开关时,table的load加载
        tableLoad: false,

        multipleSelection: [],

        // 抽屉模块
        drawerVisible: false,
        drawerTableData: [
          // {
          //   state: '0',
          //   name: '张三',
          //   checkInTime: '2023-03-01 - 20234-03-01',
          //   identityCardNumber: '23123123123123123123',
          //   mail: '258273912@qq.com',
          //   phone: 12312312312,
          //   address: '213121d1e12e12',

          // }
        ],



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

      getLeaseState() {
        return (stateCode) => {
          const { dictLabel } = this.leaseStateDict.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },

      getHouseState() {
        return (codeState) => {
          const { dictLabel } = this.houseStateDict.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },

      getRentType() {
        return (stateCode) => {
          const { dictLabel } = this.rentTypeDict.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },

      // 根据租赁状态来控制Switch是否可以点击 0在租 1未租
      controlSwitchDisabled() {
        return (stateCode) => {
          return stateCode == '0' ? true : false
        }
      }



    },
    created() {
      this.getAllDicts()
      this.getAxiosData()
    },
    mounted() {
    },
    methods: {
      getAllDicts() {

        Promise.all([this.getDicts('park_tenant_status'), this.getDicts('park_rent_type'), this.getDicts('park_house_status')]).then(res => {
          console.log(res);
          this.dictLoad = false
          this.leaseStateDict = res[0].data
          this.rentTypeDict = res[1].data
          this.houseStateDict = res[2].data

        }).catch(err => {
          this.$message.error('请刷新页面,如不行,请稍后再试')
          console.log(err);

        })

      },

      // 封装查询/刷新分页 (列表)
      async refreshPage(pagination) {
        // 提取需要更名的属性
        let { parkId: zoneId, leaseStatus: rentalStatus, ...rest } = this.inquireForm
        // 设置一个字段 拿到对应的value
        let setObj = {
          '已租': '0',
          '未租': '1',
        }
        // 判断是否需要根据rentalStatus去转化数值 如果有选择租赁状态就处理
        if (rentalStatus !== undefined) rentalStatus = setObj[rentalStatus]
        console.log('rentalStatus:', rentalStatus);

        let inquireValueArr = []
        // 判断是否inquire里面是否有数值 有数值且查询逻辑开始 才会开始查询
        for (let i in this.inquireForm) {
          inquireValueArr.push(Boolean(this.inquireForm[i]))
        }
        let isInquireEmpty = inquireValueArr.some(item => item == true)
        // 设置两个接口相同的参数
        let requestObj = { zoneId, rentalStatus, ...rest, }
        try {
          let result = undefined
          // 判断是列表模式还是房态模式 发起不同的接口请求
          switch (this.showModule) {
            case '列表模式':
              this.tableLoad = true
              // 判断是否开启查询模式
              requestObj = this.isInquireControl && isInquireEmpty ? { ...requestObj, ...pagination } : { ...pagination }
              result = await getListPageQuery(requestObj)
              break;

            case '房态模式':
              this.roomModuleLoad = true
              if (zoneId == undefined) {
                this.roomModuleLoad = false
                return this.$message.error('请选择要查询的园区')
              }
              result = await getRoomPageQuery(requestObj)
              break;
          }

          this.total = result.total
          console.log(result);

          // 房态结构需要
          let roomArr = []
          roomArr.push(result.data)

          this.tableData = (this.showModule == '列表模式') ? result.rows.map(item => {
            return {
              id: item.roomId, //房间id
              parkName: item.zoneName,
              roomName: item.roomName,
              buildingName: item.buildingName,
              floorName: item.floorName,
              area: item.squareMeasure,
              leaseState: item.rentalStatus,  //1未租  0在租
              rent: item.rent,
              rentType: item.rentType,
              shelfTime: item.shelfTime,
              isShelf: item.shelfStatus,  //1上架  0下架
            }
          }) : roomArr.map(itemPark => {
            // 修改buildingArr的数据
            return {
              parkName: itemPark.zoneName,
              buildingArr: itemPark.buildingList.map(itemBuilding => {
                return {
                  buildingName: itemBuilding.buildingName,
                  floorArr: itemBuilding.floorList.map(itemFloor => {
                    return {
                      floorName: itemFloor.floorName,
                      roomArr: itemFloor.roomList.map(itemRoom => {
                        return {
                          id: itemRoom.roomId,
                          roomName: itemRoom.roomName,
                          area: itemRoom.squareMeasure,
                          rent: itemRoom.rent,
                          leaseState: itemRoom.rentalStatus,
                          rentType: itemRoom.rentType,
                          vacantDays: itemRoom.idleDays
                        }
                      })
                    }
                  })
                }
              }),
            }
          })

          console.log('tableData:', this.tableData);


        } catch (error) {
          console.log(error);
        }
        // 关闭加载
        if (this.showModule == '列表模式') {
          this.tableLoad = false
        } else {
          this.roomModuleLoad = false
        }
      },

      async getAxiosData() {
        // 发送接口 查询分页列表 获得园区下拉框
        getParkSelect().then(res => {
          this.parkArr = res.data.map(item => {
            return {
              parkId: item.zoneId,
              value: item.zoneName
            }
          })
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

      handleRadioBtn($event) {
        if (!(event.target.localName == 'span')) return
        // 当点击元素为自身时将leaseStatus改为undefined
        // 用于三元判断是否点击的是否是自身 根据value拿到label

        this.inquireForm.leaseStatus = this.inquireForm.leaseStatus == event.target.innerText ? undefined : event.target.innerText
        console.log(' this.inquireForm.leaseStatus:', this.inquireForm.leaseStatus);

      },

      // 当显示模式发现变化的时候
      async handleShowChange(value) {
        this.$refs['inquireFormRef'].resetFields()
        this.showModule = value
        let result = null
        // 更改查询数据,默认拿到第一个园区parkArr[0]
        if (this.showModule == '房态模式') this.inquireForm.parkId = this.parkArr[0].parkId
        if (this.showModule == '列表模式') {
          this.listModuleLoad = true
          await this.refreshPage(this.pagination)
          this.listModuleLoad = false
        } else {
          this.refreshPage(this.pagination)
        }
      },


      handleInquire() {
        let tempArr = []
        for (let item in this.inquireForm) {
          tempArr.push(Boolean(this.inquireForm[item]))
        }
        let isInquireEmpty = tempArr.some(item => item == true)
        if (!isInquireEmpty) return this.$message.warning('请至少输入一个查询数据')
        this.isInquireControl = true
        this.refreshPage(this.pagination)

      },

      handleReset() {
        this.$refs['inquireFormRef'].resetFields()
        if (this.showModule == '房态模式') this.inquireForm.parkId = this.parkArr[0].parkId
        this.refreshPage(this.pagination)
      },

      // 共用一个勾选逻辑
      handelCellClick(tableRef, row, column, cell, event) {
        if (column.label == '操作' || column.label == '是否上架') return
        let isExist = tableRef == 'tableDataRef' ? this.multipleSelection.some(item => item.id == row.id) : this.floorMultipleSelection.some(item => item.id == row.id)
        if (isExist) return this.$refs[tableRef].toggleRowSelection(row, false)
        this.$refs[tableRef].toggleRowSelection(row, true)

      },

      // 处理表格内的开关变化的回调函数  1上架  0下架
      handleSwitchChange(value, row) {
        console.log('value', value);
        console.log('row', row);
        // 要先通过房间id和上架下架状态去发送修改接口  修改接口之后再调用分页接口  用async
        console.log(row.id);
        this.tableLoad = true
        editsShelfState({ roomId: row.id, shelfStatus: value }).then(res => {
          this.tableLoad = false
        })
          .catch(err => {
            console.log(err);
          })

      },


      addRoom() {
        this.$router.push(`/park_catalogue/roomEdit/${null}`)
      },

      // 共用一个删除逻辑  
      async delLogic(isFlot, row) {
        try {
          let result = undefined
          switch (isFlot) {
            case 'batch-del':
              // 将对应的房间id提取出来
              let requestArr = this.multipleSelection.map(item => item.id)
              result = await deleteRoom(requestArr)

              break;

            case 'del':
              result = await deleteRoom(row.id)
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
        // 进行路由带参跳转 在另一个页面拿到id,发起接口
        const { id } = row
        this.$router.push(`/park_catalogue/roomEdit/${id}`)
      },

      // 房态卡片跳转编辑页面
      handleCard(id) {
        this.$router.push(`/park_catalogue/roomEdit/${id}`)

      },

      handleOpenDrawer(row) {
        this.drawerVisible = true
        // 发送axios请求
        getRoomLogs(row.id).then(res => {
          this.drawerTableData = res.data.map(item => {
            return {
              state: item.houseType,
              name: item.nickName,
              checkInTime: item.startTime,
              returnTime: item.returnTime,
              identityCardNumber: item.idCardNumber,
              mail: item.email,
              phone: item.phonenumber,
              homeArea: item.homeArea,
              addressDetails: item.addressDetails,
            }
          })
        }).catch(err => {
          console.log(err);
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

    ::v-deep .radio-group .el-radio-button {
      border: none;
      box-shadow: none;
    }

    .park-container {
      .building-container {

        ::v-deep .el-collapse-item__header {
          font-size: 20px;
          background-color: #fafcfe;
        }
      }

      .room-container {
        padding: 20px;
        box-sizing: border-box;

        display: grid;
        gap: 20px;
        grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
        /* grid-template-columns: 1fr 1fr 1fr;
        gap: 10px; */

        /* 已租样式 */
        .leased-style {
          border-left: 2px solid green !important;
        }

        /* 未租样式 */
        .unchartered-style {
          border-left: 2px solid red !important;
        }

        .card {

          display: flex;
          flex-direction: column;
          justify-content: space-around;

          padding: 30px;
          box-sizing: border-box;

          width: 300px;
          height: 150px;
          border-radius: 20px;
          border: 1px solid #cdcdcd;

          .vacancy-day-container {
            display: flex;
            align-items: center;
            color: #969393ee;


          }
        }
      }
    }

    .park-container {

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