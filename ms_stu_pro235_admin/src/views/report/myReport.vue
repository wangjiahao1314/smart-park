<template>
    <div class="">
        <div class="container">
            <div style="background-color: white;padding: 20px;">
                <!-- 数据操作行 -->
                <el-row :gutter="10" class="flex-ai-center">
                    <el-form :model="inquireForm" label-width="100px">
                        <el-col :span="1.5">
                            <el-form-item label="报修单号：" prop="RepairNumber">
                                <el-autocomplete placeholder="请输入报修单号"
                                    v-model="inquireForm.RepairNumber"></el-autocomplete>
                            </el-form-item>
                        </el-col>
                        <el-col :span="1.5">
                            <el-form-item label="工单状态：" prop="workState">
                                <el-radio-group v-model="inquireForm.workState">
                                    <el-radio-button label="待分配"></el-radio-button>
                                    <el-radio-button label="待处理"></el-radio-button>
                                    <el-radio-button label="已完成"></el-radio-button>
                                    <el-radio-button label="已取消"></el-radio-button>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-form>
                    <el-button size="mini" type="primary" @click="">查询</el-button>
                    <el-button size="mini" @click="">重置</el-button>
                </el-row>
                <!-- 新建按钮，判断租户状态 -->
                <router-link :to="'/report/newRepair'">
                    <el-button size="mini" type="primary" @click="">新建</el-button>
                </router-link>
                <!-- 表格 -->
                <el-table :data="repairList">
                    <el-table-column prop="maintenanceNumber" label="维修单号"></el-table-column>
                    <el-table-column prop="repairArea" label="报修区域"></el-table-column>
                    <el-table-column prop="contacts" label="联系人"></el-table-column>
                    <el-table-column prop="contactNumber" label="联系电话"></el-table-column>
                    <el-table-column prop="maintenanceWorker" label="维修工人"></el-table-column>
                    <el-table-column prop="state" label="状态"></el-table-column>
                    <el-table-column sortable prop="reportTime" label="报修时间"></el-table-column>
                    <el-table-column label="操作" width="200">
                        <template #default="{ row, column, $index }" class="flex-jc-between">
                            <!-- 状态为已完成/已取消 -->
                            <!-- <el-button type="text" size="mini" @click="">详情</el-button> -->
                            <!-- 状态为待分配 -->
                            <el-button type="text" size="mini" @click="$router.push('/report/newRepair')">修改</el-button>
                            <el-button type="text" size="mini" @click="cancelConfirm(row)">取消</el-button>
                            <el-button type="text" size="mini" @click="$router.push('/report/orderDetail')">详情</el-button>
                            <!-- 状态为待处理 -->
                            <!-- <el-button type="text" size="mini" @click="">完成</el-button>
                            <el-button type="text" size="mini" @click="">取消</el-button>
                            <el-button type="text" size="mini" @click="">详情</el-button> -->
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
                "RepairNumber": undefined,
                "workState": undefined,
            },
            repairList: [{
                maintenanceNumber: 111,
                repairArea: '报修区域',
                contacts: '小明',
                contactNumber: 18359534306,
                maintenanceWorker: '老王',
                state: '已完成',
                reportTime: '2025-03'
            }]
        }
    },
    methods: {
        // 取消弹窗
        cancelConfirm(rowData) {
            const h = this.$createElement
            this.$confirm('', {
                message: h('div', null, [
                    h('i', { class: 'el-icon-question', style: 'color:#f90;font-size:30px;' }),
                    h('span', { class: 'set_span', style: '' }, '确认取消'),
                    h('p', { style: 'margin:10px 0 0 40px;' }, `您确认要取消维修工单？`)
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
                        message: '取消成功!'
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

.el-form-item--medium {
    margin: 0;
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
