<template>
  <div class="login-container">
    <div class="login-card">
      <!-- 🎨 타이틀 -->
      <div class="header-logo">
        <RouterLink to="/home" class="logo">Angorani</RouterLink>
      </div>


      <!-- ✅ 로그인 폼 -->
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">아이디</label>
          <div class="input-wrapper">
            <input type="username" id="username" v-model="username" required />
          </div>
        </div>

        <div class="form-group">
          <label for="password">비밀번호</label>
          <div class="input-wrapper">
            <input type="password" id="password" v-model="password" required />
          </div>
        </div>

        <!-- ✅ 로그인 버튼 -->
        <button type="submit" class="btn primary-btn">로그인</button>

        <!-- ✅ 네이버 로그인 버튼 -->
        <button class="btn naver-btn" @click="handleNaverLogin">
          <img src="@/assets/naver-icon.png" alt="네이버 로고" class="btn-icon" />
          네이버 로그인
        </button>

        <!-- ✅ 회원가입 & 홈으로 돌아가기 버튼 (버튼 크기 통일됨) -->
        <RouterLink to="/register" class="btn secondary-btn">회원가입</RouterLink>
        <RouterLink to="/" class="btn secondary-btn">⬅ 홈으로 돌아가기</RouterLink>
      </form>

      <!-- 🚨 에러 메시지 -->
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useAuthStore } from "@/stores/useAuthStore.js";
import { useRouter, useRoute } from "vue-router";

const username = ref("");
const password = ref("");
const errorMessage = ref("");
const authStore = useAuthStore();
const router = useRouter();
const route = useRoute();

const handleLogin = async () => {
  const success = await authStore.login(username.value, password.value);
  if (success) {
    const redirectPath = route.query.redirect || "/mypage";
    router.push(redirectPath);
  } else {
    errorMessage.value = "이메일 또는 비밀번호가 올바르지 않습니다.";
  }
};

// ✅ 네이버 로그인 처리 (더미 기능)
const handleNaverLogin = () => {
  alert("네이버 로그인 기능이 구현될 예정입니다!");
};
</script>

<style scoped>
/* ✅ HTML & BODY 설정 */
html, body {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  overflow-x: hidden;
}

/* ✅ 전체 레이아웃 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  background: linear-gradient(to right, #eceff1, #ffffff);
  padding: 20px;
  box-sizing: border-box;
}

/* ✅ 로그인 카드 */
.login-card {
  background: white;
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.15);
  width: 100%;
  max-width: 400px;
  max-height: 85vh;
  overflow-y: auto;
  text-align: center;
  box-sizing: border-box;
  animation: fadeIn 0.8s ease-out;
}

/* 🎨 타이틀 */
.login-title {
  font-size: 26px;
  font-weight: bold;
  color: #ff5e57;
  margin-bottom: 20px;
}

/* ✅ 폼 그룹 */
.form-group {
  text-align: left;
  margin-bottom: 15px;
}

/* ✅ 입력 필드 */
.input-wrapper {
  background: rgba(0, 0, 0, 0.05);
  border-radius: 8px;
  padding: 12px;
  display: flex;
  align-items: center;
}

.input-wrapper input {
  width: 100%;
  background: none;
  border: none;
  font-size: 16px;
  color: #333;
  outline: none;
  padding: 5px;
}

/* ✅ 공통 버튼 스타일 */
.btn {
  font-size: 16px;
  font-weight: bold;
  padding: 12px;
  width: 100%;  /* ✅ 모든 버튼 동일한 너비 */
  border: none;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-top: 10px; /* ✅ 버튼 간격 동일하게 */
  transition: all 0.3s ease-in-out;
  text-decoration: none; /* ✅ 링크 스타일 제거 */
  box-sizing: border-box; /* ✅ 내부 패딩 차이 제거 */
}

/* ✅ RouterLink 버튼도 동일한 크기로 만들기 */
.btn.secondary-btn {
  display: flex;  /* ✅ 버튼과 동일하게 설정 */
  justify-content: center;
  align-items: center;
}

/* ✅ 로그인 버튼 (빨간색) */
.primary-btn {
  background: #ff5e57;
  color: white;
}

.primary-btn:hover {
  background: #e04e47;
  transform: translateY(-2px);
  box-shadow: 0px 5px 15px rgba(255, 94, 87, 0.3);
}

/* ✅ 네이버 로그인 버튼 (초록색) */
.naver-btn {
  background: #03c75a;
  color: white;
}

.header-logo{
  margin-bottom: 50px;
}
/* ✅ 로고 스타일 */
.header-logo .logo {
  font-size: 1.8rem;
  font-weight: bold;
  text-decoration: none;
  color: black;
}



.naver-btn:hover {
  background: #029d4d;
  transform: translateY(-2px);
  box-shadow: 0px 5px 15px rgba(3, 199, 90, 0.3);
}

/* ✅ 공통 아이콘 스타일 */
.btn-icon {
  width: 24px;
  height: 24px;
}

/* ✅ 회원가입 & 홈으로 돌아가기 버튼 (회색) */
.secondary-btn {
  background: #f5f5f5;
  color: #333;
  text-align: center;
}

.secondary-btn:hover {
  background: #ddd;
}

/* 🚨 에러 메시지 */
.error {
  color: red;
  font-size: 14px;
  margin-top: 10px;
}

/* 🔥 애니메이션 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(15px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* ✅ 반응형 (모바일 최적화) */
@media (max-width: 768px) {
  .login-card {
    padding: 30px;
    max-width: 90vw;
    max-height: 75vh;
  }

  .btn {
    font-size: 14px;
    padding: 10px;
  }

  .input-wrapper {
    padding: 10px;
  }
}
</style>
