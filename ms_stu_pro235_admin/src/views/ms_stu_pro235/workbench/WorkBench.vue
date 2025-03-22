<template>
  <div id="page-style">

    <div class="top-container">
      <work-bench-card v-for="(item,index) in workBenchData" :key="index" :card-obj="item"></work-bench-card>
    </div>

    <div class="bottom-container">
      <el-tabs v-model="defaultActive" @tab-click="handleEchartsTab" class="tab-container">

        <el-tab-pane v-for="(item,index) in tabsList" :key="index" :label="item.label" :name="item.name">
          <div ref="chartDom" class="echarts-container">

          </div>
        </el-tab-pane>
      </el-tabs>
      <el-radio-group v-model="controltab" size="small" class="select-tab" @input="handleToggleTabShow">
        <el-radio-button label="本月"></el-radio-button>
        <el-radio-button label="本年"></el-radio-button>

      </el-radio-group>
    </div>
  </div>
</template>
<script>
  import WorkBenchCard from '@/components/ms_stu_pro235/workbench/WorkBenchCard.vue'
  import * as echarts from 'echarts'
  export default {
    components: {
      WorkBenchCard,
    },
    data() {
      return {
        // 模拟工作台卡片数据
        workBenchData: [
          { title: '本月应收账单', totalValue: 12123, alreadyPayment: 2132, remain: 3312 },
          { title: '物业管理费应收账单', totalValue: 12123, alreadyPayment: 2132, remain: 3312 },
          { title: '租金应收账单', totalValue: 12123, alreadyPayment: 2132, remain: 3312 },
          { title: '水电费应收账单', totalValue: 12123, alreadyPayment: 2132, remain: 3312 },
        ],
        //控制tabs默认选中第一个
        defaultActive: 'first',
        tabsList: [
          { label: '应收款', name: 'first', data: [], load: false },
          { label: '已收款', name: 'second', data: [], load: false },
          { label: '未收款', name: 'third', data: [], load: false },
        ],

        // 控制单项选择按钮
        controltab: "本月",
        // 存放echart实例
        echartContainer: [],
        // tab下标
        tabIndex: '0',
        // echarts的配置数据模板
        option: {
          title: {
            text: '本月应收款'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            },
            formatter: function (params) {
              // 只返回y轴数据，不返回x轴数据
              return `金额：${params[0].value}元`;
            }
          },
          xAxis: {
            axisTick: {
              show: true,
              alignWithLabel: true
            },
            data: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30']
          },
          yAxis: {},
          series: [
            {
              name: '租金',
              type: 'bar',
              data: [0, 45, 28, 33, 17, 22, 19, 31, 26, 40, 14, 29, 37, 11, 23, 16, 34, 27, 18, 25, 38, 13, 42, 21, 30, 15, 24, 32, 41, 10],
              itemStyle: {
                color: '#1890ff'
              },
            },

          ]
        }

      }
    },
    computed: {

    },
    created() {

    },
    mounted() {
      this.initEachart()
    },
    methods: {
      // 处理tab事件
      handleEchartsTab(tab, event) {

        // console.log('tab', tab);
        // 根据tab.index去更改data中的tabIndex 拿到当前下标
        this.tabIndex = tab.index
        this.$nextTick(() => {
          this.initEachart(this.tabIndex)
        })

      },

      // 处理控制echartsX轴事件
      handleToggleTabShow(value) {
        this.controltab = value
        this.initEachart(this.tabIndex)
      },
      // 更新X轴
      updataX(controlTab) {
        const curXArr = []
        let temp = ''
        // 判断当前echarts要渲染的时本月还是本年
        switch (controlTab) {
          case '本月':
            const currentDate = new Date();
            const currentYear = currentDate.getFullYear();
            const currentMonth = currentDate.getMonth();
            const daysInCurrentMonth = this.getDaysInMonth(currentYear, currentMonth);
            for (let i = 1; i <= daysInCurrentMonth; i++) {
              let temp = ''
              if (i < 10) {
                temp = '0' + i + '日'
              } else {
                temp = i + '日'
              }
              curXArr.push(temp)
            }
            break;

          case '本年':
            for (let i = 1; i <= 12; i++) {
              if (i < 10) {
                temp = '0' + i + '月'
              } else {
                temp = i + '月'
              }
              curXArr.push(temp)
            }
            break;
        }
        this.option.xAxis.data = curXArr
      },

      // 更新series
      updataSeries(index) {
        index = index.toString()
        let data = undefined
        switch (index) {
          case '0':
            // 模拟数据
            data = this.controltab === '本月' ? ['5678', '2349', '1035', '6782', '3416', '7893', '4527', '9014', '6758', '8920', '3469', '1205', '5680', '2316', '7841', '4592', '8963', '5624', '1275', '3486', '2357', '6798', '4519', '8930', '5642', '7861', '3470', '9025'] : ['8765', '3490', '2156', '7832', '4519', '6703', '9824', '1265', '5637', '8940', '2376', '4581']

            return this.option.series[0].data = data

          case '1':
            data = this.controltab === '本月' ? ['3456', '7890', '2345', '6789', '1245', '3678', '4567', '8901', '5678', '9012', '6789', '0123', '7890', '1234', '8901', '2345', '9012', '3456', '0123', '4567', '1234', '5678', '2345', '6789', '3456', '7890', '4567', '8901'] : ['4321', '6789', '1034', '5690', '2378', '9016', '3457', '7829', '6540', '1283', '5607', '8912']

            return this.option.series[0].data = data

          case '2':
            data = this.controltab === '本月' ? ['2145', '5678', '1023', '4567', '9876', '3456', '6789', '1234', '7890', '5678', '8901', '2345', '6789', '1023', '5678', '8901', '3456', '7890', '2345', '6789', '1234', '4567', '8901', '3456', '7890', '2345', '6789', '1023'] : ['9876', '1234', '5609', '8743', '2198', '6750', '3412', '7865', '4590', '1238', '5671', '9042']

            return this.option.series[0].data = data
        }

      },

      // 实例化echarts
      initEachart(index = 0) {

        this.destroy(index)

        const chartDomAll = this.$refs['chartDom']
        const curChartDom = chartDomAll[index]
        this.echartContainer[index] = echarts.init(curChartDom)

        // 更新echartsX轴  根据当前天数更改x轴的数据
        this.updataX(this.controltab)

        // 更新title
        this.option.title.text = this.controltab + this.tabsList[index].label

        //更新series[0]的data
        this.updataSeries(index)


        if (this.option && typeof this.option === 'object') {
          this.echartContainer[index].setOption(this.option);
        }




      },

      // 销毁echarts实例 只有在当点击本月或者本年的时候才会需要销毁
      destroy(index) {
        // const chartDomAll = this.$refs['chartDom']
        // console.log('传入下标', index);
        // const curChartDom = chartDomAll[index]
        if (this.echartContainer[index]) {
          this.echartContainer[index].dispose()
          this.echartContainer[index] = null

        }
      },

      // 得到当前月份的天数
      getDaysInMonth(year, month) {
        // 注意：Date 对象的月份是从 0 开始计数的，所以这里传入 month + 1
        // 而日期设为 0，表示上个月的最后一天
        return new Date(year, month + 1, 0).getDate();
      }
    },

  }
</script>
<style lang="scss" scoped>
  #page-style {
    display: flex;
    flex-direction: column;
    justify-content: space-around;

    min-height: calc(100vh - 131px);
    padding: 20px;
    background-color: #f1f2f6;
    box-sizing: border-box;

    .top-container {
      display: flex;
      justify-content: space-between;
      padding-bottom: 20px;
    }

    .bottom-container {
      position: relative;

      background-color: #fff;

      /* 通过深度选择器修改elementUI tabs样式 */
      ::v-deep .el-tabs__item {
        height: 50px;
        padding: 0px 30px;
        line-height: 50px;
      }

      .select-tab {
        position: absolute;
        right: 0;
        top: 10px;
        right: 2%;
      }

      .echarts-container {
        padding: 20px;

        width: 100%;
        height: 350px;
        box-sizing: border-box;
      }


    }
  }
</style>