<template>
  <transition name="fade-login">
    <div v-if="isOpen" class="login-container" @click.self="closeModal">
      <div class="login-card">
        <button class="close-btn" @click="closeModal">✖</button>
        <h2 class="login-title">로그인</h2>

        <form @submit.prevent="handleLogin">
          <div class="form-group">
            <label for="id">이메일</label>
            <div class="input-wrapper">
              <input type="email" id="id" v-model="id" required />
            </div>
          </div>

          <div class="form-group">
            <label for="password">비밀번호</label>
            <div class="input-wrapper">
              <input type="password" id="password" v-model="password" required />
            </div>
          </div>

          <button type="submit" class="login-btn">로그인</button>
          <RouterLink to="/register" class="register-link">회원가입</RouterLink>
        </form>

        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { ref, defineEmits, watchEffect } from "vue";
import { useAuthStore } from "@/stores/useAuthStore.js";
import { useRouter, useRoute } from "vue-router";

const id = ref("");
const password = ref("");
const errorMessage = ref("");
const authStore = useAuthStore();
const router = useRouter();
const route = useRoute();
const emit = defineEmits(["close"]);
const isOpen = ref(true);

const handleLogin = async () => {
  const success = await authStore.login(id.value, password.value);
  if (success) {
    const redirectPath = route.query.redirect || "/home";
    router.push(redirectPath);
    closeModal();
  } else {
    errorMessage.value = "이메일 또는 비밀번호가 올바르지 않습니다.";
  }
};

const closeModal = () => {
  isOpen.value = false;
  emit("close"); // ✅ 부모(Header.vue)에서 `showLoginModal = false` 처리
};
</script>


<style scoped>
/* ✅ 전체 레이아웃 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.5); /* ✅ Material 스타일 반투명 배경 */
  backdrop-filter: blur(10px); /* ✅ Glassmorphism 효과 */
  z-index: 1000;
}

/* ✅ 로그인 카드 */
.login-card {
  background: rgba(255, 255, 255, 0.9); /* ✅ Glassmorphism 효과 */
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 380px;
  text-align: center;
  position: relative;
  animation: fadeInUp 0.6s ease-out;
  border: 1px solid rgba(255, 255, 255, 0.3);
}

/* ✅ 닫기 버튼 */
.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  color: #666;
  transition: color 0.3s ease;
}

.close-btn:hover {
  color: #333;
}

/* ✅ 제목 */
.login-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

/* ✅ 입력 필드 그룹 */
.form-group {
  display: flex;
  flex-direction: column;
  text-align: left;
  margin-bottom: 15px;
}

/* ✅ 입력 필드 */
.input-wrapper {
  display: flex;
  align-items: center;
  background: #f9f9f9;
  border-radius: 8px;
  padding: 10px;
  border: 1px solid #ddd;
  transition: all 0.3s ease-in-out;
}

input {
  flex: 1;
  border: none;
  background: transparent;
  font-size: 16px;
  outline: none;
}

input:focus {
  border: none;
  outline: none;
}

/* ✅ 로그인 버튼 */
.login-btn {
  background: linear-gradient(to right, #007bff, #00c6ff);
  color: white;
  font-size: 16px;
  font-weight: bold;
  padding: 12px;
  width: 100%;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
  margin-top: 10px;
}

.login-btn:hover {
  background: linear-gradient(to right, #0056b3, #00a2ff);
  transform: translateY(-2px);
  box-shadow: 0px 5px 15px rgba(0, 123, 255, 0.4);
}

/* ✅ 회원가입 링크 */
.register-link {
  display: block;
  margin-top: 15px;
  font-size: 14px;
  color: #007bff;
  font-weight: bold;
  text-decoration: none;
  transition: color 0.3s ease-in-out;
}

.register-link:hover {
  color: #0056b3;
}

/* ✅ 에러 메시지 */
.error {
  color: red;
  font-size: 14px;
  margin-top: 10px;
}

/* ✅ 애니메이션 */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(15px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.fade-login-enter-active {
  transition: opacity 0.5s ease, transform 0.5s ease;
}

.fade-login-enter-from {
  opacity: 0;
  transform: scale(0.9);
}

.fade-login-leave-to {
  opacity: 0;
  transform: scale(1.1);
}
</style>
