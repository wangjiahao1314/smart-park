<template>
    <div class="">
        <div class="container flex-direction-column">
            <!-- 维修进度 -->
            <div style="background-color: white;">
                <div style="padding: 20px 0 0 20px;">维修进度</div>
                <el-divider></el-divider>
                <el-steps :active="active" align-center style="padding: 20px 40px;">
                    <el-step title="待分配">
                        <template #description>
                            <div>租户</div>
                            <div>2025-03-09</div>
                        </template>
                    </el-step>
                    <el-step title="待处理">
                        <template #description>
                            <div>租户</div>
                            <div>2025-03-09</div>
                        </template>
                    </el-step>
                    <el-step title="已完成">
                        <template #description>
                            <div>租户</div>
                            <div>2025-03-09</div>
                        </template>
                    </el-step>
                </el-steps>
            </div>
            <!-- 报修信息 -->
            <div style="background-color: white;">
                <div style="padding: 20px 0 0 20px;">报修信息</div>
                <el-divider></el-divider>
                <div class="repairInfo">
                    <span>租户：</span>
                    <span>维修单号：</span>
                    <span>所属园区：</span>
                    <span>联系人：</span>
                    <span>报修时间：</span>
                    <span>所属楼宇：</span>
                    <span>联系电话：</span>
                    <span>报修区域：</span>
                    <span>所属楼层：</span>
                    <span>上门时间：</span>
                    <span>描述：</span>
                    <span>房间名称：</span>
                    <span>维修申请照片：</span>
                </div>
            </div>
            <!-- 维修信息 -->
            <div style="background-color: white;">
                <div style="padding: 20px 0 0 20px;">维修信息</div>
                <el-divider></el-divider>
                <div class="maintenanceInfo">
                    <span>维修员：</span>
                    <span>联系电话：</span>
                    <span>开始时间：</span>
                    <span>结束时间：</span>
                </div>
            </div>
            <!-- 维修日志 -->
            <div style="background-color: white;">
                <div style="padding: 20px 0 0 20px;">维修日志</div>
                <el-divider></el-divider>
                <el-table :data="maintenanceLogList" style="padding: 0 20px;">
                    <el-table-column prop="operateType" label="操作类型"></el-table-column>
                    <el-table-column prop="operator" label="操作人"></el-table-column>
                    <el-table-column prop="participant" label="参与人员"></el-table-column>
                    <el-table-column prop="operateTime" label="操作时间"></el-table-column>
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
            active: 2,
            maintenanceLogList: []
        }
    }
}
</script>
<style lang="scss" scoped>
.container {
    width: 100%;
    height: 100%;
    padding: 20px;
    box-sizing: border-box;
    background-color: #F1F3F5;
    gap: 20px;
}

.repairInfo {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    padding: 0 20px 20px 20px;
    gap: 10px;
}

.maintenanceInfo {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    padding: 0 20px 20px 20px;
    gap: 10px;
}
</style>
