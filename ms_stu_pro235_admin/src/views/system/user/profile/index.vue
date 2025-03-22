<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar />
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="people" />账户
                <div class="pull-right">{{ user.account }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="user" />真实姓名
                <div class="pull-right">{{ user.name }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="log" />身份证号码
                <div class="pull-right">{{ user.idNumber }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="phone" />联系电话
                <div class="pull-right">{{ user.phone }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="email" />邮箱
                <div class="pull-right">{{ user.email }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="peoples" />所属角色
                <div class="pull-right">{{ user.role }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="date" />创建日期
                <div class="pull-right">{{ user.createDate }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="row" />现住地址
                <div class="pull-right">{{ user.currentAddress }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="guide" />家庭地址
                <div class="pull-right">{{ user.homeAddress }}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18" :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :user="user" />
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd />
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";
import { getUserProfile } from "@/api/system/user";

export default {
  name: "Profile",
  components: { userAvatar, userInfo, resetPwd },
  data() {
    return {
      user: {},
      roleGroup: {},
      postGroup: {},
      activeTab: "userinfo"
    };
  },
  created() {
    this.getUser();
  },
  methods: {
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
        this.roleGroup = response.roleGroup;
        this.postGroup = response.postGroup;
      });
    }
  }
};
</script>
