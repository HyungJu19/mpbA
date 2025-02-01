<template>
  <div class="register-container">
    <div class="register-card">
      <h2 class="register-title">회원가입</h2>
      <form @submit.prevent="handleRegister">
        <div class="form-group">
          <label for="id">아이디</label>
          <div class="input-wrapper">
            <input type="text" id="id" v-model="user.id" required />
          </div>
        </div>

        <div class="form-group">
          <label for="password">비밀번호</label>
          <div class="input-wrapper">
            <input type="password" id="password" v-model="user.password" required />
          </div>
        </div>

        <div class="form-group">
          <label for="persName">이름</label>
          <div class="input-wrapper">
            <input type="text" id="persName" v-model="user.persName" required />
          </div>
        </div>

        <div class="form-group">
          <label for="email">이메일</label>
          <div class="input-wrapper">
            <input type="email" id="email" v-model="user.email" @input="validateEmail" required />
          </div>
          <p v-if="emailErrorMessage" class="error">{{ emailErrorMessage }}</p>
        </div>

        <button type="submit" class="register-btn">회원가입</button>
      </form>

      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      <p v-if="successMessage" class="success">{{ successMessage }}</p>
      <RouterLink to="/login" class="login-link">로그인 페이지로 이동</RouterLink>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useAuthStore } from "@/stores/useAuthStore.js";
import { useRouter } from "vue-router";

const router = useRouter();
const authStore = useAuthStore();
const emailErrorMessage = ref("");
const user = ref({
  id: "",
  persName: "",
  email: "",
  password: "",
});
const errorMessage = ref("");
const successMessage = ref("");

const validateEmail = () => {
  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  emailErrorMessage.value = emailPattern.test(user.value.email) ? "" : "유효한 이메일 주소를 입력하세요.";
};

const handleRegister = async () => {
  const result = await authStore.register(user.value);
  if (result.success) {
    successMessage.value = "회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.";
    setTimeout(() => {
      router.push("/login");
    }, 1500);
  } else {
    errorMessage.value = result.message;
  }
};
</script>

<style scoped>
/* ✅ 전체 배경 설정 */
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background: none; /* ✅ 배경 제거 */
}

/* ✅ 카드 스타일 */
.register-card {
  background: rgba(255, 255, 255, 0.9); /* ✅ Glassmorphism 효과 */
  backdrop-filter: blur(10px);
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 400px;
  text-align: center;
  position: relative;
  animation: fadeInUp 0.6s ease-out;
  border: 1px solid rgba(255, 255, 255, 0.3);
}

/* ✅ 제목 */
.register-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #2c3e50;
}

/* ✅ 입력 필드 그룹 */
.form-group {
  display: flex;
  flex-direction: column;
  text-align: left;
  margin-bottom: 15px;
}

/* ✅ 입력 필드 스타일 */
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

/* ✅ 회원가입 버튼 */
.register-btn {
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

.register-btn:hover {
  background: linear-gradient(to right, #0056b3, #00a2ff);
  transform: translateY(-2px);
  box-shadow: 0px 5px 15px rgba(0, 123, 255, 0.4);
}

/* ✅ 로그인 링크 */
.login-link {
  display: block;
  margin-top: 15px;
  font-size: 14px;
  color: #007bff;
  font-weight: bold;
  text-decoration: none;
  transition: color 0.3s ease-in-out;
}

.login-link:hover {
  color: #0056b3;
}

/* ✅ 에러 & 성공 메시지 */
.error {
  color: red;
  font-size: 14px;
  margin-top: 10px;
}

.success {
  color: green;
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

.fade-register-enter-active {
  transition: opacity 0.5s ease, transform 0.5s ease;
}

.fade-register-enter-from {
  opacity: 0;
  transform: scale(0.9);
}

.fade-register-leave-to {
  opacity: 0;
  transform: scale(1.1);
}
</style>
