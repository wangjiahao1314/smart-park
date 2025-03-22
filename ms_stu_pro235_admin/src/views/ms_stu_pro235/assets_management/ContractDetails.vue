<template>
  <div id="page-style">
    <div class="contract-details-container">
      <el-skeleton style="width: 100%;" :loading="dictLoad || initAxiosLoad" animated>
        <template slot="template">

          <div style="padding: 14px;">
            <el-skeleton-item variant="h3" style="width: 100%;padding: 20px; margin-bottom: 20px;" />
            <div style="width: 85%; margin: auto;">
              <el-skeleton :rows="12" animated />
            </div>
          </div>
        </template>
        <template>
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="合同信息" name="contract-info">
              <el-skeleton style="width: 100%" :loading="contractLoad" animated>
                <template slot="template">
                  <el-skeleton :rows="12" animated />
                </template>
                <template>
                  <div class="skeleton-content">
                    <div class="common-content-container">
                      <!-- 列表1 -->
                      <el-descriptions class="content-descriptions" :column="4" size="medium">

                        <template #title>
                          <div style="color:#c4c4c4 ;">
                            主要信息
                          </div>
                        </template>

                        <el-descriptions-item label="合同编号">2131231231</el-descriptions-item>
                        <el-descriptions-item label="合同名称">明诚广场122</el-descriptions-item>
                        <el-descriptions-item label="合同状态">{{getContractState('1')}}</el-descriptions-item>
                        <el-descriptions-item label="租赁日期">
                          2021-05-01-2031-04-30
                        </el-descriptions-item>
                        <el-descriptions-item label="经办人">mawra</el-descriptions-item>
                        <el-descriptions-item label="合同版本">1</el-descriptions-item>
                        <el-descriptions-item label="备注">~</el-descriptions-item>
                      </el-descriptions>

                    </div>
                    <el-divider></el-divider>

                    <div class="common-content-container">
                      <el-descriptions class="content-descriptions" :column="2" size="medium" :colon="false">

                        <template #title>
                          <div style="color:#c4c4c4 ;">
                            乙方
                          </div>
                        </template>

                        <el-descriptions-item label="租户信息:">反正然</el-descriptions-item>
                        <el-descriptions-item></el-descriptions-item>
                        <el-descriptions-item label="身份证:">231231231231231</el-descriptions-item>
                        <el-descriptions-item label="个人邮箱:">
                          23123123123@qq.com
                        </el-descriptions-item>
                        <el-descriptions-item label="联系电话:">12312312312</el-descriptions-item>
                        <el-descriptions-item label="家庭地址:">1123123123123</el-descriptions-item>

                      </el-descriptions>
                    </div>

                    <el-divider></el-divider>

                    <div class="common-content-container">
                      <div style="color: #c4c4c4; margin-bottom: 20px;">
                        租赁场地
                      </div>

                      <div style="margin-bottom: 20px;">
                        <el-table :data="contractTableData" style="width: 100%">
                          <el-table-column prop="roomName" label="房间名称" min-width="10%"
                            show-overflow-tooltip></el-table-column>
                          <el-table-column prop="parkName" label="所属园区" min-width="10%" show-overflow-tooltip>
                          </el-table-column>
                          <el-table-column prop="buildingName" label="所属楼宇" min-width="10%"
                            show-overflow-tooltip></el-table-column>
                          <el-table-column prop="floorName" label="所属楼层" min-width="10%"
                            show-overflow-tooltip></el-table-column>
                          <el-table-column prop="area" label="收租面积 (平方米)" min-width="10%"
                            show-overflow-tooltip></el-table-column>
                        </el-table>
                      </div>

                      <div style="margin-bottom: 20px;">
                        <el-descriptions class="content-descriptions" :column="3" size="medium">

                          <template #title>
                            <div style="color:#c4c4c4 ;">
                              固定租金
                            </div>
                          </template>

                          <el-descriptions-item label="租金单价">
                            23213 {{getRentType('0')}}
                          </el-descriptions-item>
                          <el-descriptions-item label="日租金">12312元</el-descriptions-item>
                          <el-descriptions-item label="月租金">12312312元</el-descriptions-item>
                          <el-descriptions-item label="管理费单价">
                            31231 {{getManagementFeeType('0')}}
                          </el-descriptions-item>
                          <el-descriptions-item label="日管理费">12.1元</el-descriptions-item>
                          <el-descriptions-item label="月管理费">12312元</el-descriptions-item>
                        </el-descriptions>
                      </div>

                      <el-descriptions class="content-descriptions" :column="1" size="medium">

                        <template #title>
                          <div style="color:#c4c4c4 ;">
                            押付方式
                          </div>
                        </template>

                        <el-descriptions-item label="押">2付: 1</el-descriptions-item>

                      </el-descriptions>
                    </div>
                  </div>
                </template>
              </el-skeleton>

            </el-tab-pane>
            <el-tab-pane label="账单信息" name="bill-info">

              <el-skeleton style="width: 100%" :loading="billLoad" animated>
                <template slot="template">
                  <el-skeleton :rows="4" animated />
                </template>
                <template>
                  <el-table :data="billTableData">
                    <el-table-column type="index" label="序号" min-width="10%"></el-table-column>
                    <el-table-column property="billStartTime" label="账单开始时间" min-width="10%"
                      show-overflow-tooltip></el-table-column>
                    <el-table-column property="billEndTime" label="账单结束时间" min-width="10%"
                      show-overflow-tooltip></el-table-column>
                    <el-table-column property="billCollectionDay" label="账单收款日" min-width="10%"
                      show-overflow-tooltip></el-table-column>
                    <el-table-column property="rent" label="租金 (元)" min-width="10%"
                      show-overflow-tooltip></el-table-column>
                    <el-table-column property="billState" label="状态" min-width="10%"
                      show-overflow-tooltip></el-table-column>
                    <el-table-column property="remark" label="备注" min-width="10%"
                      show-overflow-tooltip></el-table-column>
                    <template slot=" empty">
                      <el-empty :image-size="Number(80)">
                        <template slot="image">
                          <img src="@/assets/images/envelope.png" alt="">
                        </template>
                      </el-empty>
                    </template>
                  </el-table>

                  <div style="display: flex; justify-content: flex-end; margin-top: 20px;">
                    <el-pagination background @current-change="handleCurrentChange"
                      :current-page="billPagination.pageNum" :page-sizes="[5]" :page-size="billPagination.pageSize"
                      layout="  prev, pager, next" :total="billTableDataTotal">
                    </el-pagination>
                  </div>
                </template>
              </el-skeleton>

            </el-tab-pane>

          </el-tabs>

          <div class="flex-justify-center">
            <el-button class="default-btn" type="default" @click="$router.go(-1)">返回</el-button>
            <el-button v-if="$route.params.title == 'contractPrint'" class="primary-btn" type="primary"
              @click="handleContentPrint">打印</el-button>
          </div>
        </template>
      </el-skeleton>

    </div>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        // 页面标题
        title: '',

        // 骨架屏 控制初始化加载
        dictLoad: true,
        initAxiosLoad: true,

        // 骨架屏 控制tab切换
        contractLoad: true,
        billLoad: true,

        // 存储 租金类型单位 管理费单位 合同状态字典值
        rentTypeDicts: [],
        managementFeeTypeDicts: [],
        contractStateDicts: [],

        // 存储当前tab的index 后面打印要根据index去打印的是什么页面
        curIndex: '0',
        // 当前选中的activeName
        activeName: 'contract-info',

        contractTableData: [
          {
            id: 1,
            roomName: "帝豪",
            parkName: "明诚广场",
            buildingName: "华建大厦",
            floorName: "23",
            area: 170,
          }
        ],

        billTableData: [
          {
            id: 1,
            billStartTime: '2025-04-01',
            billEndTime: '2025-04-30',
            billCollectionDay: '2025-03-20',
            rent: 124123,
            billState: '已收款',
            remark: ''
          }
        ],

        billPagination: {
          pageNum: 1,
          pageSize: 5,
        },
        billTableDataTotal: 0,


      }
    },
    computed: {
      getRentType() {
        return (stateCode) => {
          const { dictLabel } = this.rentTypeDicts.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },
      getManagementFeeType() {
        return (stateCode) => {
          const { dictLabel } = this.managementFeeTypeDicts.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },
      getContractState() {
        return (stateCode) => {
          const { dictLabel } = this.contractStateDicts.find(item => item.dictValue == stateCode) || {}
          return dictLabel || '-'
        }
      },
    },

    watch: {

    },
    created() {
      this.title = this.$route.params.title
      this.getAllDicts()
      this.getInitAxiosData()
    },
    methods: {
      getAllDicts() {
        Promise.all([this.getDicts('park_rent_type'), this.getDicts('park_property_unit'), this.getDicts('park_contract_status')]).then(res => {

          this.rentTypeDicts = res[0].data
          this.managementFeeTypeDicts = res[1].data
          this.contractStateDicts = res[2].data
          console.log(res);

          //更改骨架屏中的其中一个load
          this.dictLoad = false
        }).catch(err => {
          console.log(err);
          this.$message.error('请刷新页面,如不行,请稍后再试')

        })
      },

      getInitAxiosData() {
        // 更改页面标题
        this.$store.dispatch('settings/setTitle', this.title == 'contractPrint' ? '合同打印' : this.title == 'contractDetails' ? '合同详细' : this.$store.settings.title)
        // 如果是新建页面没有传入id 则表示是新建合同,不用axios
        if (this.$route.query.id == undefined) return this.initAxiosLoad = false

        // 模拟数据 后面接axios时改成promise.all 
        setTimeout(() => {
          // 控制初始化骨架屏
          this.initAxiosLoad = false
          // 控制tab的骨架屏
          this.contractLoad = false
        }, 1000);
      },

      // 处理tab点击 根据tab.index来发送不同的axios
      handleClick(tab, event) {
        console.log('tab:', tab);
        // 根据下标切换打开骨架屏
        if (tab.index == '0') this.contractLoad = true
        if (tab.index == '1') this.billLoad = true
        switch (tab.index) {
          // 合同信息
          case '0':
            // 发送axios接口 可以考虑使用async
            setTimeout(() => {

              this.contractLoad = false

            }, 1000);
            break;

          // 账单信息
          case '1':
            // 发送axios接口 可以考虑使用async
            setTimeout(() => {

              this.billLoad = false

            }, 1000);
            break;
        }
        // 更新curIndex 打印需要
        this.curIndex = tab.index

      },

      // 处理合同打印
      handleContentPrint() {
        console.log('打印合同');

      },

      // 账单逻辑
      handleCurrentChange(value) {
        this.billPagination.pageNum = value
      },
    }
  }
</script>
<style scoped lang="scss">
  #page-style {
    /* display: flex;
    flex-direction: column;
    justify-content: space-around; */

    min-height: calc(100vh - 131px);
    padding: 20px;
    background-color: #f1f2f6;
    box-sizing: border-box;

    .contract-details-container {
      padding: 20px 40px;
      background-color: #ffffff;

      .common-content-container {
        width: 85%;
        margin: auto;

        /* .content-title-style {
          color: #c4c4c4;
          padding: 20px;

        } */
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