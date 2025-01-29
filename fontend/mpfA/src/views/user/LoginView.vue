<template>
  <div class="login-view">
    <p v-if="authStore.user">✅ 로그인 중입니다. "내 정보 관리" 페이지로 이동합니다...</p>
    <LoginForm v-else />
  </div>
</template>

<script setup>
import {useAuthStore} from "@/stores/useAuthStore.js";
import { useRouter } from "vue-router";
import { watchEffect } from "vue";
import LoginForm from "@/components/LoginForm.vue";

const authStore = useAuthStore();
const router = useRouter();

// ✅ 로그인 상태면 내 정보 페이지로 자동 이동 (로그아웃 방지)
watchEffect(() => {
  if (authStore.accessToken && authStore.user) {
    console.log("🔄 로그인 상태 확인 → 내 정보 관리 페이지로 이동");
    router.push("/mypage");
  }
});
</script>

<style scoped>
/* 스타일 추가 가능 */
</style>
