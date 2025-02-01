<template>
  <div v-show="isOpen" class="auth-modal">
    <button @click="closeModal">닫기</button>
    <h2>로그인</h2>
    <p>로그인 정보를 입력하세요</p>
  </div>
</template>

<script setup>
import {ref, defineEmits} from "vue";

const isOpen = ref(true); // ✅ 초기 상태를 열림으로 설정
const emit = defineEmits(["close"]);

const closeModal = () => {
  isOpen.value = false;
  emit("close");
};
</script>

<style scoped>
.auth-modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0px 5px 20px rgba(0, 0, 0, 0.2);
}
</style>
