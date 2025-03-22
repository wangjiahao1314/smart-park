<template>
    <div class="">
        <div class="container">
            <div style="background-color: white;padding: 20px;">
                <!-- 数据操作行 -->
                <el-row :gutter="10" class="flex-ai-center">
                    <el-form :model="inquireForm" label-width="100px">
                        <el-col :span="1.5">
                            <el-form-item label="合同编号：" prop="contractNumber">
                                <el-autocomplete placeholder="请输入合同编号"
                                    v-model="inquireForm.contractNumber"></el-autocomplete>
                            </el-form-item>
                        </el-col>
                        <el-col :span="1.5">
                            <el-form-item label="账单月份：" prop="billMonth">
                                <el-date-picker v-model="inquireForm.billMonth" type="month" placeholder="选择月">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="1.5">
                            <el-form-item label="账单状态：" prop="billState">
                                <el-radio-group v-model="inquireForm.billState">
                                    <el-radio-button label="未收"></el-radio-button>
                                    <el-radio-button label="已收"></el-radio-button>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="1.5">
                            <el-form-item label="是否逾期：" prop="overdue">
                                <el-radio-group v-model="inquireForm.overdue">
                                    <el-radio-button label="否"></el-radio-button>
                                    <el-radio-button label="是"></el-radio-button>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-form>
                </el-row>
                <el-row :gutter="10" class="flex-jc-center" style="margin-bottom: 20px;">
                    <el-button size="mini" type="primary" @click="">查询</el-button>
                    <el-button size="mini" @click="">重置</el-button>
                </el-row>
                <!-- 付款按钮 -->
                <el-button :class="{ 'visible-hidden': showPay }" size="mini" type="primary" @click="">付款</el-button>
                <!-- 表格 -->
                <el-table :data="billList" @selection-change="billSelect">
                    <el-table-column type="selection"></el-table-column>
                    <el-table-column fixed prop="accountPeriod" label="账期"></el-table-column>
                    <el-table-column fixed prop="tenantName" label="租户名称"></el-table-column>
                    <el-table-column prop="contractName" label="合同名称"></el-table-column>
                    <el-table-column prop="contractNumber" label="合同编号"></el-table-column>
                    <el-table-column prop="billStatus" label="账单状态"></el-table-column>
                    <el-table-column prop="overdue" label="是否逾期"></el-table-column>
                    <el-table-column sortable prop="collectDeadline" label="收款截止日"></el-table-column>
                    <el-table-column prop="rentPayable" label="应付租金（元）"></el-table-column>
                    <el-table-column prop="deposit" label="押金（元）"></el-table-column>
                    <el-table-column prop="propertyFee" label="物业管理费（元）"></el-table-column>
                    <el-table-column prop="totalReceivable" label="合计应收（元）"></el-table-column>
                    <el-table-column prop="remark" label="备注"></el-table-column>
                    <el-table-column fixed="right" label="操作" width="200">
                        <template #default="{ row, column, $index }" class="flex-jc-between">
                            <el-button type="text" size="mini" @click="payConfirm(row)">付款</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: "",
    data() {
        return {
            inquireForm: {
                "contractNumber": undefined,
                "billMonth": undefined,
                "billState": undefined,
                "overdue": undefined
            },
            billList: [{
                accountPeriod: '11'
            }],
            selectLength: 0
        }
    },
    computed: {
        showPay() {
            return !this.selectLength
        }
    },
    methods: {
        // 选中多选框
        billSelect(select) {
            this.selectLength = select.length
        },
        // 付款弹窗
        payConfirm(rowData) {
            const h = this.$createElement
            this.$confirm('', {
                message: h('div', null, [
                    h('i', { class: 'el-icon-question', style: 'color:#f90;font-size:30px;' }),
                    h('span', { class: 'set_span', style: '' }, '确认付款'),
                    h('p', { style: 'margin:10px 0 0 40px;' }, `您确认要付款？`)
                ]),
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            })
                .then(() => {
                    deleteSupplier({
                        supplierIds: rowData.supplierId
                    }).then(res => {
                    })
                    this.$message({
                        type: 'success',
                        message: '即将跳转至付款页面!'
                    });
                    // console.log(rowData.name);

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
        },
    }
}
</script>
<style lang="scss" scoped>
.container {
    background-color: #F1F3F5;
    width: 100%;
    height: 100vh;
    padding: 20px;
    box-sizing: border-box;
}

.set_span {
    margin-left: 10px;
    font-size: 16px;
    line-height: 30px;
    font-weight: 600;
    vertical-align: top;
}

.set_i {
    color: #f90;
    font-size: 30px;
}
</style>
