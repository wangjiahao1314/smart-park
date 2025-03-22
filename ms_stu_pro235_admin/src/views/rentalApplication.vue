<template>
    <div class="">
        <div class="container">
            <div style="background-color: white;padding: 20px;">
                <!-- 数据操作行 -->
                <el-row :gutter="10" class="flex-ai-center" style="margin-bottom: 20px;">
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
                <!-- 表格 -->
                <el-table :data="rentList">
                    <el-table-column prop="park" label="所属园区"></el-table-column>
                    <el-table-column prop="roomName" label="房间名称"></el-table-column>
                    <el-table-column prop="building" label="所属楼宇"></el-table-column>
                    <el-table-column prop="floor" label="所属楼层"></el-table-column>
                    <el-table-column prop="area" label="面积（平方米）"></el-table-column>
                    <el-table-column prop="rent" label="租金（元）"></el-table-column>
                    <el-table-column prop="rentType" label="租金类型"></el-table-column>
                    <el-table-column label="操作" width="200">
                        <template #default="{ row, column, $index }" class="flex-jc-between">
                            <el-button type="text" size="mini" @click="dialogVisible = true">租赁</el-button>
                            <el-button type="text" size="mini" @click="roomDialogVisible=true">详情</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!-- 点击租赁弹出信息确认对话框 -->
                <el-dialog title="信息确认" :visible.sync="dialogVisible" @closed="resetDialog('rentFormRef')">
                    <h2 align="center">确认个人信息，若有误请从个人中心修改。</h2>
                    <el-form :disabled="rentFormLock" ref="rentFormRef" :model="rentForm" :rules="rentFormRules"
                        label-width="100px" size="mini" disabled>
                        <el-form-item label="真实姓名：" prop="name">
                            <el-input v-model="rentForm.name"></el-input>
                        </el-form-item>
                        <el-form-item label="身份证号：" prop="idNumber">
                            <el-input v-model="rentForm.idNumber"></el-input>
                        </el-form-item>
                        <el-form-item label="个人邮箱：" prop="email">
                            <el-input v-model="rentForm.email"></el-input>
                        </el-form-item>
                        <el-form-item label="联系电话：" prop="phone">
                            <el-input v-model="rentForm.phone"></el-input>
                        </el-form-item>
                        <el-form-item label="家庭地址：" prop="homeAddress">
                            <el-input v-model="rentForm.homeAddress"></el-input>
                        </el-form-item>
                        <h2 align="center">是否确认申请租赁“xxxxx”？</h2>
                    </el-form>
                    <template #footer>
                        <el-row type="flex" justify="end">
                            <el-button size="mini" @click="$message('已取消租赁申请'); dialogVisible = false">取消</el-button>
                            <el-button size="mini" type="primary"
                                @click="dialogVisible = false; timeDialogVisible = true">确定</el-button>
                        </el-row>
                    </template>
                </el-dialog>
                <!-- 选择日期弹窗 -->
                <el-dialog title="请选择起始租日" :visible.sync="timeDialogVisible">
                    <div class="flex-jc-center">
                        <el-date-picker v-model="startTime" type="daterange" align="right" unlink-panels
                            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"
                            :picker-options="pickerOptions">
                        </el-date-picker>
                    </div>
                    <div class="flex-jc-end">
                        <el-button size="mini" @click="timeDialogVisible = false">取消</el-button>
                        <el-button type="primary" size="mini" @click="openSuccess">确定</el-button>
                    </div>
                </el-dialog>
                <!-- 点击详情弹出房间信息预览对话框 -->
                <el-dialog title="房间信息预览" :visible.sync="roomDialogVisible" @closed="resetDialog('roomFormRef')">
                    <el-form :disabled="roomFormLock" ref="roomFormRef" :model="roomForm" :rules="roomFormRules"
                        label-width="100px" size="mini" disabled>
                        <el-form-item label="所属楼宇：" prop="building">
                            <el-input v-model="roomForm.building"></el-input>
                        </el-form-item>
                        <el-form-item label="所属楼层：" prop="floor">
                            <el-input v-model="roomForm.floor"></el-input>
                        </el-form-item>
                        <el-form-item label="房间名称：" prop="roomName">
                            <el-input v-model="roomForm.roomName"></el-input>
                        </el-form-item>
                        <el-form-item label="租金：" prop="rent">
                            <el-input v-model="roomForm.rent"></el-input>
                        </el-form-item>
                        <el-form-item label="物业费：" prop="propertyFee">
                            <el-input v-model="roomForm.propertyFee"></el-input>
                        </el-form-item>
                        <el-form-item label="房间面积（平方米）：" prop="area">
                            <el-input v-model="roomForm.area"></el-input>
                        </el-form-item>
                        <el-form-item label="房型：" prop="roomType">
                            <el-input v-model="roomForm.roomType"></el-input>
                        </el-form-item>
                        <el-form-item label="层高（米）：" prop="storeyHeight">
                            <el-input v-model="roomForm.storeyHeight"></el-input>
                        </el-form-item>
                        <el-form-item label="装修类型：" prop="decorationType">
                            <el-input v-model="roomForm.decorationType"></el-input>
                        </el-form-item>
                        <el-form-item label="上传房间小图：" prop="roomPicture">
                            <el-input v-model="roomForm.roomPicture"></el-input>
                        </el-form-item>
                        <el-form-item label="房间简介（可选）" prop="roomBrief">
                            <el-input type="textarea" :rows="4" v-model="roomForm.roomBrief"></el-input>
                        </el-form-item>
                    </el-form>
                    <template #footer>
                        <el-row type="flex" justify="end">
                            <el-button size="mini" @click="">取消</el-button>
                            <el-button size="mini" type="primary" @click="">确定</el-button>
                        </el-row>
                    </template>
                </el-dialog>
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
                "roomName": undefined,
                "buildingName": undefined,
                "parkName": undefined
            },
            rentList: [{
                park: 'y'
            }],
            dialogVisible: false,
            rentFormLock: false,
            rentForm: {
                "name": undefined,
                "idNumber": undefined,
                "email": undefined,
                "phone": undefined,
                "homeAddress": undefined
            },
            rentFormRules: {
                "name": [{ required: true, message: '请输入真实姓名', trigger: ['blur'] }],
                "idNumber": [{ required: true, message: '请输入身份证号', trigger: ['blur'] }],
                "phone": [{ required: true, message: '请输入联系电话', trigger: ['blur'] }],
                "homeAddress": [{ required: true, message: '请输入家庭地址', trigger: ['blur'] }]
            },
            pickerOptions: {
                shortcuts: [{
                    text: '最近一周',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近一个月',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近三个月',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                        picker.$emit('pick', [start, end]);
                    }
                }]
            },
            startTime: '',
            timeDialogVisible: false,
            roomDialogVisible: false,
            roomFormLock: false,
            roomForm: {
                "building": undefined,
                "floor": undefined,
                "roomName": undefined,
                "rent": undefined,
                "propertyFee": undefined,
                "area": undefined,
                "roomType": undefined,
                "storeyHeight": undefined,
                "decorationType": undefined,
                "roomPicture": undefined,
                "roomBrief": undefined,
            },
            roomFormRules: {
                "building": [{ required: true, trigger: ['blur'] }],
                "floor": [{ required: true, trigger: ['blur'] }],
                "roomName": [{ required: true, trigger: ['blur'] }],
                "rent": [{ required: true, trigger: ['blur'] }],
                "propertyFee": [{ required: true, trigger: ['blur'] }],
                "area": [{ required: true, trigger: ['blur'] }],
                "roomType": [{ required: true, trigger: ['blur'] }],
                "decorationType": [{ required: true, trigger: ['blur'] }],
            }
        }
    },
    methods: {
        // 重置对话框内容
        resetDialog(name) {
            this.$refs[name].resetFields()
        },
        // 点击确认，申请租赁
        openSuccess() {
            this.$message({
                message: `已确定申请租赁“所属园区 所属楼宇 房间名称”，待管理员审核`,
                type: 'success'
            });
            this.timeDialogVisible = false
            this.startTime = ''
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
