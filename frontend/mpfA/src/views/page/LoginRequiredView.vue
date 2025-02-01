<template>
  <div class="login-required-wrapper">
    <transition name="fade-slide">
      <div class="login-required">
        <!-- 🔒 아이콘 추가 -->
        <div class="icon-container">
          <i class="material-icons lock-icon">lock</i>
        </div>

        <h2 class="title">로그인이 필요합니다</h2>
        <p class="description">
          이 페이지는 로그인한 사용자만 접근할 수 있습니다.<br />
          계정이 없으신가요? 지금 바로 가입하세요!
        </p>

        <!-- ✅ 로그인 버튼 -->
        <button class="btn primary-btn" @click="showLoginModal = true">
          로그인하기
        </button>

        <!-- ✅ 회원가입 버튼 -->
        <RouterLink to="/register" class="btn secondary-btn">
          회원가입
        </RouterLink>

        <!-- ✅ 홈으로 돌아가기 -->
        <RouterLink to="/" class="home-link">
          ⬅ 홈으로 돌아가기
        </RouterLink>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { inject, onMounted } from "vue";

const showLoginModal = inject("showLoginModal"); // ✅ `App.vue`에서 제공하는 상태 사용

// ✅ 스크롤 방지
onMounted(() => {
  document.body.style.overflow = "hidden"; // 🌟 스크롤 막기
});
</script>

<style scoped>
/* ✅ 전체 레이아웃 설정 */
.login-required-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100dvh; /* ✅ 모바일 주소창 제외한 100% 높이 */
  width: 100vw;
  overflow: hidden;
  background: none; /* ✅ 배경색 제거 */
}

/* ✅ 카드 스타일 (Glassmorphism 효과 적용) */
.login-required {
  background: rgba(255, 255, 255, 0.8); /* 반투명 유리 효과 */
  backdrop-filter: blur(10px);
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.15);
  text-align: center;
  max-width: 400px;
  width: 100%;
  border: 1px solid rgba(255, 255, 255, 0.3);
  animation: fadeInUp 0.6s ease-out;
}

/* ✅ 아이콘 스타일 */
.icon-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 15px;
}

.lock-icon {
  font-size: 40px;
  color: #007bff;
}

/* ✅ 제목 스타일 */
.title {
  font-size: 24px;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 10px;
}

/* ✅ 설명 스타일 */
.description {
  font-size: 16px;
  color: #555;
  margin-bottom: 20px;
  line-height: 1.6;
}

/* ✅ 버튼 기본 스타일 */
.btn {
  display: block;
  width: 100%;
  padding: 12px;
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  border-radius: 8px;
  transition: all 0.3s ease-in-out;
  text-decoration: none;
  margin-bottom: 12px;
  cursor: pointer;
  box-sizing: border-box;
  border: none;
}

/* ✅ 로그인 버튼 */
.primary-btn {
  background: linear-gradient(to right, #007bff, #00c6ff);
  color: white;
}

.primary-btn:hover {
  background: linear-gradient(to right, #0056b3, #00a2ff);
  transform: translateY(-2px);
  box-shadow: 0px 5px 15px rgba(0, 123, 255, 0.4);
}

/* ✅ 회원가입 버튼 */
.secondary-btn {
  background: white;
  color: #007bff;
  border: 2px solid #007bff;
  padding: 10px;
}

.secondary-btn:hover {
  background: #007bff;
  color: white;
}

/* ✅ 홈으로 돌아가기 */
.home-link {
  display: block;
  margin-top: 15px;
  font-size: 14px;
  color: #555;
  font-weight: bold;
  text-decoration: none;
  transition: color 0.3s ease-in-out;
}

.home-link:hover {
  color: #007bff;
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

.fade-slide-enter-active {
  transition: opacity 0.5s ease, transform 0.5s ease;
}

.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(10px);
}

.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>
