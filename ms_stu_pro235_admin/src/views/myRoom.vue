<template>
    <div class="">
        <div class="container">
            <div style="background-color: white;padding: 20px;">
                <!-- 数据操作行 -->
                <el-row :gutter="10" class="flex-ai-center">
                    <el-form :model="inquireForm" label-width="100px">
                        <el-col :span="1.5">
                            <el-form-item label="房间名称：" prop="roomName">
                                <el-autocomplete placeholder="请输入房间名称" v-model="inquireForm.roomName"></el-autocomplete>
                            </el-form-item>
                        </el-col>
                        <el-col :span="1.5">
                            <el-form-item label="所属楼宇：" prop="buildingName">
                                <el-autocomplete placeholder="请输入所属楼宇名称"
                                    v-model="inquireForm.buildingName"></el-autocomplete>
                            </el-form-item>
                        </el-col>
                        <el-col :span="1.5">
                            <el-form-item label="园区名称：" prop="parkName">
                                <el-autocomplete placeholder="请输入园区名称" v-model="inquireForm.parkName"></el-autocomplete>
                            </el-form-item>
                        </el-col>
                    </el-form>
                    <el-button size="mini" type="primary" @click="">查询</el-button>
                    <el-button size="mini" @click="">重置</el-button>
                </el-row>
                <!-- 新建按钮 -->
                <el-button size="mini" type="primary" @click="">新建</el-button>
                <!-- 表格 -->
                <el-table :data="roomList">
                    <template slot="empty">
                        <el-empty description="暂无租房"></el-empty>
                        <el-button type="primary" @click="$router.push('/rentalApplication')"
                            style="margin-bottom: 35px">租房申请</el-button>
                    </template>
                    <el-table-column type="selection"></el-table-column>
                    <el-table-column prop="park" label="所属园区"></el-table-column>
                    <el-table-column prop="roomName" label="房间名称"></el-table-column>
                    <el-table-column prop="buildingName" label="所属楼宇"></el-table-column>
                    <el-table-column prop="floorId" label="所属楼层"></el-table-column>
                    <el-table-column prop="squareMeasure" label="面积（平方米）"></el-table-column>
                    <el-table-column prop="rent" label="租金（元）"></el-table-column>
                    <el-table-column prop="rentType" label="租金类型"></el-table-column>
                    <el-table-column prop="applicationTime" label="申请时间"></el-table-column>
                    <el-table-column prop="approvalState" label="审批状态">
                        <template #default="scope">
                            <el-popover placement="top-start" width="200" trigger="hover"
                                content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">
                                <template #reference>
                                    <dict-tag :options="dict.type.park_approval_status"
                                        :value="scope.row.approvalState" />
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>
                    <!-- 不满足三种状态，即为空 -->
                    <el-table-column prop="houseState" label="房屋状态">
                        <template #default="scope">
                            <el-popover placement="top-start" width="200" trigger="hover"
                                content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">
                                <template #reference>
                                    <dict-tag :options="dict.type.park_house_status" :value="scope.row.houseState" />
                                </template>
                            </el-popover>
                        </template>
                    </el-table-column>
                    <el-table-column prop="startTime" label="申请租赁开始时间"></el-table-column>
                    <el-table-column prop="endTime" label="申请租赁结束时间"></el-table-column>
                    <el-table-column label="操作" width="200">
                        <template #default="{ row, column, $index }" class="flex-jc-between">
                            <!-- 未签订合同 -->
                            <el-button type="text" size="mini" @click="">房屋详情</el-button>
                            <!-- 未签订合同 -->
                            <el-button type="text" size="mini" @click="">取消申请</el-button>
                            <!-- 已签订合同 -->
                            <!-- <el-button type="text" size="mini" @click="">合同</el-button> -->
                            <!-- 已签订合同且在租 -->
                            <!-- <el-button type="text" size="mini" @click="">退租</el-button> -->
                        </template>
                    </el-table-column>
                </el-table>
                <!-- 分页 -->
                <el-row type="flex" justify="end" v-if="roomList.length != 0">
                    <el-pagination :current-page.sync="pagination.pageNum" :page-sizes="pagination.pageSizes"
                        :page-size.sync="pagination.pageSize" layout="total, sizes, prev, pager, next, jumper"
                        :total="pagination.total" @current-change="">
                    </el-pagination>
                </el-row>
            </div>
        </div>
    </div>
</template>
<script>
import { pageList } from '../api/myRoom';

export default {
    name: "",
    dicts: ['park_approval_status', 'park_house_status'],
    data() {
        return {
            inquireForm: {
                "roomName": undefined,
                "buildingName": undefined,
                "parkName": undefined
            },
            roomList: [],
            pagination: {
                total: 100,
                currentPage: 1,
                pageSize: 10,
                pageSizes: [10, 20, 50, 100, 200],
                pageNum: 1
            },
            approvalStateDicts: []
        }
    },
    created() {
        this.listRoom()
    },
    methods: {
        // 分页查询房间信息
        listRoom() {
            const { pageNum, pageSize } = this.pagination
            pageList({ pageNum, pageSize }).then(res => {
                console.log(res);
                this.roomList=res.rows
            })
        }
    }
}
</script>
<style lang="scss" scoped>
html,
body,
#app {
    margin: 0;
    padding: 0;
    height: 100%;
}

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
