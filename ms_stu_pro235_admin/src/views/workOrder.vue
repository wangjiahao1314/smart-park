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
                                    <el-radio-button label="待处理"></el-radio-button>
                                    <el-radio-button label="已完成"></el-radio-button>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-form>
                    <el-button size="mini" type="primary" @click="">查询</el-button>
                    <el-button size="mini" @click="">重置</el-button>
                </el-row>
                <!-- 表格 -->
                <el-table :data="repairList" style="margin-top: 20px;">
                    <el-table-column prop="maintenanceNumber" label="维修单号"></el-table-column>
                    <el-table-column prop="repairArea" label="报修区域"></el-table-column>
                    <el-table-column prop="contacts" label="联系人"></el-table-column>
                    <el-table-column prop="contactNumber" label="联系电话"></el-table-column>
                    <el-table-column prop="state" label="状态"></el-table-column>
                    <el-table-column sortable prop="reportTime" label="报修时间"></el-table-column>
                    <el-table-column label="操作" width="200">
                        <template #default="{ row, column, $index }" class="flex-jc-between">
                            <el-button type="text" size="mini" @click="">详情</el-button>
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
                state: '已完成',
                reportTime: '2025-03'
            }]
        }
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
</style>
