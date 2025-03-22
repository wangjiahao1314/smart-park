<template>
  <div class="card-container" @click="tojump">
    <span>{{cardObj.title}} (元)</span>
    <div class="show-total">
      {{(cardObj.totalValue).toFixed(2)}}
    </div>
    <div class="footer-total">
      <span>{{cardObj.title==='本月应收账单'?`总已收款
        ￥${(cardObj.alreadyPayment).toFixed(2)}`:`已收款￥${(cardObj.alreadyPayment).toFixed(2)}`}}</span>
      <span>{{cardObj.title==='本月应收账单'?`总未收款
        ￥${(cardObj.remain).toFixed(2)}`:`未收款￥${(cardObj.remain).toFixed(2)}`}}</span>
    </div>
  </div>
</template>
<script>
  export default {
    props: {
      cardObj: {
        type: Object,
        default: () => ({})
      }
    },
    data() {
      return {

      }
    },
    computed: {

    },
    created() {

    },
    methods: {
      tojump() {
        this.$router.push({
          path: '/index'
        })
      }
    },
  }
</script>
<style lang="scss" scoped>
  .card-container {
    display: flex;
    flex-direction: column;

    width: 23%;
    height: 150px;
    background-color: #fff;
    padding: 10px 20px;

    &:hover {
      cursor: pointer;
      opacity: 0.7;
    }

    span {
      padding: 5px 0px;
      font-size: 12px;
      color: #cecece;
    }

    .show-total {
      flex: 1;
      font-size: 23px;
      font-weight: 700;
      border-bottom: 1px solid #f8f8f8;
    }

    .footer-total {
      display: flex;
      justify-content: space-between;
    }
  }
</style>