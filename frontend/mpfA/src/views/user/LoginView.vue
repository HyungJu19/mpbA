<template>
  <div class="login-page">

    <p v-if="authStore.user">✅ 로그인 중입니다. "내 정보 관리" 페이지로 이동합니다...</p>
    <LoginForm v-else />
  </div>
</template>

<script setup>
import { useAuthStore } from "@/stores/useAuthStore.js";
import { useRouter } from "vue-router";
import { watchEffect } from "vue";
import LoginForm from "@/components/LoginForm.vue";

const authStore = useAuthStore();
const router = useRouter();

// ✅ 로그인 상태면 자동 이동
watchEffect(() => {
  if (authStore.accessToken && authStore.user) {
    router.push("/home");
  }
});
</script>

<style scoped>

</style>
