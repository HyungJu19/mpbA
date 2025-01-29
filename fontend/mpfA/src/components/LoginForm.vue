<template>
  <div class="login-container">
    <h2>로그인</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="id">이메일</label>
        <input type="id" id="id" v-model="id" required />
      </div>
      <div class="form-group">
        <label for="password">비밀번호</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit">로그인</button>
      <button type="button"><RouterLink to="/register">회원가입</RouterLink></button>
    </form>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script setup>
import { ref } from "vue";
import {useAuthStore} from "@/stores/useAuthStore.js";
import {RouterLink, useRouter} from "vue-router";

const id = ref("");
const password = ref("");
const errorMessage = ref("");
const authStore = useAuthStore();
const router = useRouter();

const handleLogin = async () => {
  const success = await authStore.login(id.value, password.value);
  if (success) {
    router.push("/home"); // 로그인 성공 시 이동할 페이지
  } else {
    errorMessage.value = "이메일 또는 비밀번호가 올바르지 않습니다.";
  }
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}
.form-group {
  margin-bottom: 15px;
}
.error {
  color: red;
}
</style>
