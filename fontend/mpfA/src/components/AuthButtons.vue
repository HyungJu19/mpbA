<template>
  <div class="auth-buttons">
    <span v-if="authStore.user" class="user-id">{{ authStore.user.id }}님</span>
    <button v-if="authStore.user" @click="logout">로그아웃</button>
    <div v-else>
      <RouterLink to="/login">로그인</RouterLink>
      <RouterLink to="/register">회원가입</RouterLink>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from "@/stores/useAuthStore.js";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const router = useRouter();

const logout = () => {
  authStore.logout();
  router.push("/"); // ✅ 로그아웃 후 홈으로 이동
};
</script>

<style scoped>
.auth-buttons {
  display: flex;
  align-items: center;
  gap: 10px;
}

.auth-buttons a {
  color: white;
  text-decoration: none;
  margin-right: 10px;
}

.auth-buttons a:hover {
  color: #ff9800;
}

button {
  background-color: #ff9800;
  border: none;
  padding: 6px 12px;
  cursor: pointer;
  color: white;
  font-weight: bold;
}

button:hover {
  background-color: #e68a00;
}

.user-id {
  margin-right: 10px;
  font-weight: bold;
}
</style>
