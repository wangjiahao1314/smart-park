<template>
  <div class="inquire-container">
    <el-form ref="inquireFormRef" :inline="true" :model="inquireForm" :rules="formRules" @submit.native.prevent>
      <el-form-item :label="label" style="margin-right: 30px;" prop="name">
        <el-input v-model="inquireForm.name" size="mini" :placeholder='placeholder'
          @keyup.enter.native="onSubmit"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button class="primary-btn" type="primary" size="mini" @click="onSubmit">查询</el-button>
        <el-button class="default-btn" type="default" size="mini" @click="onReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>

  export default {
    props: {
      inquireForm: {
        type: Object,
        default: () => ({})
      },

      formRules: {
        type: Object,
        default: () => ({})
      },

      label: {
        type: String,
        default: ''
      },

      placeholder: {
        type: String,
        default: ''
      }
    },
    components: {

    },
    data() {
      return {


      }
    },
    computed: {

    },
    created() {

    },
    mounted() {


    },
    methods: {
      onSubmit() {
        this.$refs['inquireFormRef'].validate(valid => {
          if (!valid) return
          this.$emit('to-inquire')

        })
      },

      onReset() {
        this.$refs['inquireFormRef'].resetFields()
        this.$emit('to-reset')
      }
    },

  }
</script>
<style lang="scss" scoped>
  .inquire-container {
    background-color: #fff;

    ::v-deep .el-form-item {
      width: 22%;
    }

    ::v-deep .el-form-item__content {
      width: 65%
    }
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
</style>