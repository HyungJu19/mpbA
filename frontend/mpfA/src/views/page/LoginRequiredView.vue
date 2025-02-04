<template>
  <div class="login-required-wrapper">
    <transition name="fade-slide">
      <div class="login-required">
        <!-- 🔒 아이콘 -->
        <div class="icon-container">
          <i class="material-icons lock-icon">lock</i>
        </div>

        <h2 class="title">로그인이 필요합니다</h2>
        <p class="description">
          이 페이지는 로그인한 사용자만 접근할 수 있습니다.<br />
          계정이 없으신가요? 지금 바로 가입하세요!
        </p>

        <div class="button-group">
          <!-- ✅ 로그인 버튼 -->
          <RouterLink to="/login" class="btn primary-btn">로그인하기</RouterLink>

          <!-- ✅ 회원가입 버튼 -->
          <RouterLink to="/register" class="btn secondary-btn">회원가입</RouterLink>
        </div>

        <!-- ✅ 홈으로 돌아가기 -->
        <RouterLink to="/" class="home-link">⬅ 홈으로 돌아가기</RouterLink>
      </div>
    </transition>
  </div>
</template>

<script setup>
</script>

<style scoped>
/* ✅ 전체 배경 */
.login-required-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 100%;
  background: linear-gradient(to right, #eceff1, #ffffff); /* ✅ 로그인 페이지와 동일한 배경 */
  padding: 20px;
  box-sizing: border-box;
}

/* ✅ 로그인 카드 스타일 */
.login-required {
  background: white;
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.15);
  width: 100%;
  max-width: 400px;
  text-align: center;
  box-sizing: border-box;
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
  color: #ff5e57; /* ✅ 로그인 페이지와 컬러 통일 */
}

/* ✅ 제목 스타일 */
.title {
  font-size: 22px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

/* ✅ 설명 */
.description {
  font-size: 15px;
  color: #666;
  margin-bottom: 20px;
  line-height: 1.5;
}

/* ✅ 버튼 그룹 */
.button-group {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

/* ✅ 버튼 스타일 */
.btn {
  font-size: 16px;
  font-weight: bold;
  padding: 12px;
  width: 100%;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 10px;
  transition: all 0.3s ease-in-out;
  text-decoration: none;
  box-sizing: border-box;
}

/* ✅ 로그인 버튼 (빨간색) */
.primary-btn {
  background: #ff5e57;
  color: white;
}

.primary-btn:hover {
  background: #e04e47;
  transform: translateY(-2px);
  box-shadow: 0px 4px 10px rgba(255, 94, 87, 0.3);
}

/* ✅ 회원가입 버튼 (회색) */
.secondary-btn {
  background: #f5f5f5;
  color: #333;
  text-align: center;
}

.secondary-btn:hover {
  background: #ddd;
}

/* ✅ 홈으로 돌아가기 */
.home-link {
  display: block;
  margin-top: 15px;
  font-size: 14px;
  color: #007bff;
  font-weight: bold;
  text-decoration: none;
  transition: color 0.3s ease-in-out;
}

.home-link:hover {
  color: #0056b3;
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
