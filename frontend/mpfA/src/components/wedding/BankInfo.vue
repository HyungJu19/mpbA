<template>
  <div class="bank-info">
    <h3>💰 은행 정보</h3>
    <p>신랑 계좌: {{ groomAccount }}</p>
    <p>신부 계좌: {{ brideAccount }}</p>
  </div>
</template>

<script setup>
import { ref } from "vue";

const groomAccount = ref("국민은행 123-456-7890");
const brideAccount = ref("우리은행 987-654-3210");
</script>

<style scoped>
.bank-info {
  padding: 10px;

}
</style>
