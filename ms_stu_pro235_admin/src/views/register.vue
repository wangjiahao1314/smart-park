<template>
  <div class="register">
    <el-form ref="registerFormRef" :model="registerForm" :rules="registerRules" class="register-form">
      <h2 class="title">智慧园区管理系统</h2>
      <el-form-item prop="roleKey">
        <el-select v-model="registerForm.roleKey" placeholder="请选择注册角色">
          <el-option v-for="item in roleOpt" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="username">
        <el-input v-model="registerForm.username" type="text" auto-complete="off" placeholder="账号">
        </el-input>
      </el-form-item>
      <el-form-item prop="nickName">
        <el-input v-model="registerForm.nickName" type="text" placeholder="真实姓名">
        </el-input>
      </el-form-item>
      <el-form-item prop="idCardNumber">
        <el-input v-model="registerForm.idCardNumber" type="text" placeholder="身份证号码">
        </el-input>
      </el-form-item>
      <el-form-item prop="phonenumber">
        <el-input v-model="registerForm.phonenumber" type="text" placeholder="联系电话">
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-popover placement="right" width="200" trigger="focus">
          <template #reference>
            <el-input v-model="registerForm.password" :type="type" auto-complete="off" placeholder="密码"
              @keyup.enter.native="handleRegister" @input="inputMethod">
              <i slot="suffix" class="icon-style" :class="elIcon" autocomplete="auto" @click="flag = !flag" />
            </el-input>
          </template>
          <template>
            <div :class="['user-register', passwordLevelClass]">{{ str2 }}</div>
            <el-progress :percentage="state.percent" :show-text="false" :status="passwordLevelColor"
              style="margin: 10px 0;" />
            <div>{{ note }}</div>
          </template>
        </el-popover>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input v-model="registerForm.code" auto-complete="off" placeholder="验证码" style="width: 63%"
          @keyup.enter.native="handleRegister">
          <!-- <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" /> -->
        </el-input>
        <div class="register-code">
          <img :src="codeUrl" @click="getCode" class="register-code-img" />
        </div>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="medium" type="primary" style="width:100%;"
          @click.native.prevent="handleRegister">
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>
        <div style="float: right;">
          <router-link class="link-type" :to="'/login'">使用已有账户登录</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-register-footer">
      <span>Copyright © 2018-2024 ruoyi.vip All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg, register } from "@/api/login";

const levelClass = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColor = {
  0: 'exception',
  1: 'exception',
  2: 'warning',
  3: 'success'
}

export default {
  name: "Register",
  computed: {
    type() {
      return this.flag ? "text" : "password";
    },
    elIcon() {
      return this.flag ? "el-icon-minus" : "el-icon-view";
    },
    passwordLevelClass() {
      return levelClass[this.state.passwordLevel]
    },
    passwordLevelColor() {
      return levelColor[this.state.passwordLevel]
    }
  },
  data() {

    return {
      codeUrl: "",
      registerForm: {
        roleKey: "",
        username: "",
        nickName: "",
        idCardNumber: "",
        phonenumber: "",
        password: "",
        code: "",
        uuid: "",
      },
      registerRules: {
        roleKey: [{ required: true, trigger: "change", message: "请选择注册角色" }],
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" },
          { min: 2, max: 20, message: '用户账号长度必须介于 2 和 20 之间', trigger: 'blur' }
        ],
        nickName: [{ required: true, trigger: "blur", message: "请输入您的真实姓名" }],
        idCardNumber: [{ required: true, trigger: "blur", message: "请输入您的身份证号码" }],
        phonenumber: [{ required: true, trigger: "blur", message: "请输入您的联系电话" }],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" },
          { min: 6, max: 20, message: "用户密码长度必须介于 6 和 20 之间", trigger: "blur" },
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      captchaEnabled: true,
      flag: false,
      str2: "",
      note: "请至少输入6个字符。请不要使用容易被猜到的密码。",
      state: {
        passwordLevel: 0,              // 默认的密码强度等级
        passwordLevelChecked: false,   // 是否开启强度等级校验
        percent: 10,
        progressColor: '#FF0000'
      },
      roleOpt: [{ label: '租户', value: 'tenant' }, { label: '维修工', value: 'repair' }]
    };
  },
  created() {
    this.getCode();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.registerForm.uuid = res.uuid;
        }
      });
    },
    handleRegister() {
      this.$refs['registerFormRef'].validate(valid => {        
        if (valid) {
          this.loading = true;
          console.log(1);
          register(this.registerForm).then(res => {
            console.log(res);
            
            const username = this.registerForm.username;
            this.$alert("<font color='red'>恭喜你，您的账号 " + username + " 注册成功！</font>", '系统提示', {
              dangerouslyUseHTMLString: true,
              type: 'success'
            }).then(() => {
              this.$router.push("/login");
            }).catch(() => { });
          }).catch(() => {
            console.log(2);
            
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          })
        }
      });
    },
    inputMethod(value) {
      //密码为6位及以上并且大小写字母数字特殊字符三项都包括
      var strongRegex = new RegExp("^(?=.{6,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-7])(?=.*\\W).*$", "g");
      //密码为6位及以上并且大小写字母、数字、特殊字符三项中有两项，强度是中等
      var mediumRegex = new RegExp("^(?=.{6,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-7]))|((?=.*[a-z])(?=.*[0-7]))|((?=.*[a-z])(?=.*\\W))|((?=.*[0-7])(?=.*\\W))|((?=.*[A-Z])(?=.*\\W))).*$", "g");
      var enoughRegex = new RegExp("(?=.{6,}).*", "g");
      if (strongRegex.test(value)) {
        // console.log('强密码-----',this.passwordLevelColor)
        this.str2 = "强度：强"
        this.state.percent = 100
        this.state.passwordLevel = 3
      } else if (mediumRegex.test(value)) {
        //console.log('中等密码-----',value)
        this.str2 = "强度：中"
        this.state.percent = 60
        this.state.passwordLevel = 2
      } else if (enoughRegex.test(value)) {
        //console.log('弱密码-----',value)
        this.str2 = "强度：低"
        this.state.percent = 10
        this.state.passwordLevel = 1
      } else {
        //console.log('密码-----',value)
        this.str2 = "强度：低"
        this.state.percent = 10
        this.state.passwordLevel = 0
      }
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.user-register {
  &.error {
    color: #ff0000;
  }

  &.warning {
    color: #ff7e05;
  }

  &.success {
    color: #52c41a;
  }
}

.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  // background-image: url("../assets/images/login-background.jpg");
  background-color: #F1F2F6;
  background-size: cover;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.register-form {
  border-radius: 6px;
  background: #F1F2F6;
  width: 400px;
  padding: 25px 25px 5px 25px;

  .el-input {
    height: 38px;

    input {
      height: 38px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.register-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.register-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-register-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.register-code-img {
  height: 38px;
}
</style>
