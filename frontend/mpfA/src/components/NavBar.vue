<template>
  <header class="header">
    <div class="header-container">
      <!-- 로고 -->
      <div class="header-logo">
        <RouterLink to="/home" class="logo">Angorani</RouterLink>
      </div>

      <!-- 네비게이션 (992px 이상) -->
      <nav class="nav-categories" v-if="!isMobile">
        <RouterLink to="/home" class="nav-link">Home</RouterLink>
        <RouterLink to="/cafe24-skins" class="nav-link">DesignSkin</RouterLink>
        <RouterLink to="/products" class="nav-link">DetailPage</RouterLink>
        <RouterLink to="/wedding-invitation" class="nav-link">WeddingLetter</RouterLink>
        <RouterLink to="/contact" class="nav-link">Support</RouterLink>
      </nav>

      <!-- 로그인/로그아웃 -->
      <div class="auth-buttons" v-if="!isMobile">
        <transition name="fade-scale" mode="out-in">
          <div :key="authStore.user ? 'loggedIn' : 'loggedOut'" class="auth-item">
            <template v-if="authStore.user">
              <span class="userHi" style="margin-right: 10px"> {{ authStore.user.persName }}님 환영합니다. </span>
              <RouterLink to="/profile" class="nav-link user-id" style="margin-right: 5px">
                내정보변경
              </RouterLink>
              <button class="nav-link logout-btn" @click="handleLogout">로그아웃</button>
            </template>
            <template v-else>
              <RouterLink to="/login" class="nav-link login" @click="closeMenu" style="margin-right: 10px">login</RouterLink>
              <RouterLink to="/register" class="nav-link register" @click="closeMenu">SignUp</RouterLink>
            </template>
          </div>
        </transition>
      </div>

      <!-- ✅ 모바일 햄버거 메뉴 (991px 이하) -->
      <div class="mobile-menu" v-if="isMobile">
        <button class="hamburger" @click="toggleMenu">
          <span :class="{ open: menuOpen }"></span>
          <span :class="{ open: menuOpen }"></span>
          <span :class="{ open: menuOpen }"></span>
        </button>
        <div class="mobile-dropdown" :class="{ open: menuOpen }">
          <template v-if="!authStore.user">
            <RouterLink to="login" class="nav-link login" @click="closeMenu">login</RouterLink>
            <RouterLink to="/register" class="nav-link register" @click="closeMenu">Sign Up</RouterLink>
          </template>
          <template v-if="authStore.user">
            <div>{{ authStore.user.persName }}님 환영합니다.</div>
            <RouterLink to="/profile" class="nav-link user-id" @click="closeMenu">내정보변경</RouterLink>
          </template>
          <RouterLink to="/wedding-invitation" class="nav-link" @click="closeMenu">모바일청첩장</RouterLink>
          <RouterLink to="/cafe24-skins" class="nav-link" @click="closeMenu">카페24 스킨</RouterLink>
          <RouterLink to="/contact" class="nav-link" @click="closeMenu">1:1 문의</RouterLink>
          <RouterLink to="/products" class="nav-link" @click="closeMenu">상세페이지</RouterLink>
          <template v-if="authStore.user">
            <button class="nav-link logout-btn" @click="handleLogout">로그아웃</button>
          </template>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { useAuthStore } from "@/stores/useAuthStore.js";
import { ref, onMounted, onUnmounted } from "vue";

const authStore = useAuthStore();
const isMobile = ref(window.innerWidth <= 991);
const menuOpen = ref(false);

// ✅ 반응형 체크
const updateScreenSize = () => {
  isMobile.value = window.innerWidth <= 991;
};

// ✅ 햄버거 메뉴 토글
const toggleMenu = () => {
  menuOpen.value = !menuOpen.value;
};

// ✅ 메뉴 닫기
const closeMenu = () => {
  menuOpen.value = false;
};

// ✅ 바깥 클릭 시 닫기 기능 추가
const handleOutsideClick = (event) => {
  const menuDropdown = document.querySelector(".mobile-dropdown");
  const hamburgerBtn = document.querySelector(".hamburger");

  if (
      menuOpen.value &&
      menuDropdown &&
      !menuDropdown.contains(event.target) && // 메뉴 내부 클릭 제외
      !hamburgerBtn.contains(event.target) // 햄버거 버튼 클릭 제외
  ) {
    closeMenu();
  }
};

// ✅ 로그아웃 처리
const handleLogout = () => {
  authStore.logout();
  closeMenu();
};

// ✅ 이벤트 리스너 추가 및 해제
onMounted(() => {
  window.addEventListener("resize", updateScreenSize);
  window.addEventListener("click", handleOutsideClick); // ✅ 바깥 클릭 감지
});

onUnmounted(() => {
  window.removeEventListener("resize", updateScreenSize);
  window.removeEventListener("click", handleOutsideClick); // ✅ 이벤트 제거
});
</script>



<style scoped>
/* ✅ 전체 헤더 스타일 */
.header {
  width: 100%;
  background: white;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  padding: 12px 0;
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-link.login {
  all: unset; /* 버튼의 기본 스타일 초기화 */
  display: inline-block; /* 링크처럼 동작하도록 설정 */
  cursor: pointer; /* 클릭 가능한 손 모양 커서 */
  text-decoration: none; /* 텍스트 밑줄 제거 */
  color: inherit; /* 부모 요소의 텍스트 색상 상속 */
}

/* ✅ 헤더 컨테이너 */
.header-container {
  width: 100%;
  max-width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  box-sizing: border-box;
}

/* ✅ 로고 스타일 */
.header-logo .logo {
  font-size: 1.8rem;
  font-weight: bold;
  text-decoration: none;
  color: black;
}

/* ✅ 네비게이션 스타일 */
.nav-categories {
  display: flex;
  justify-content: center;
  gap: 40px;
}

.nav-link {
  text-decoration: none;
  color: inherit;
  font-size: 1rem;
}

.nav-link:hover {
  color: #FFD700; /* ✅ 호버 시 노란색 */
}

/* ✅ 로그아웃 버튼 (빨간색 유지) */
.logout-btn {
  background: red;
  color: white;
  padding: 6px 12px;
  border-radius: 6px;
  border: none;
  font-weight: bold;
}

.logout-btn:hover {
  background: darkred;
}

/* ✅ 모바일 메뉴 */
.mobile-menu {
  display: flex;
  align-items: center;
}

/* ✅ 햄버거 버튼 */
.hamburger {
  width: 35px;
  height: 30px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background: none;
  border: none;
  cursor: pointer;
}

.hamburger span {
  width: 100%;
  height: 4px;
  background: #333;
  transition: all 0.3s ease;
}

.hamburger span.open:nth-child(1) {
  transform: translateY(12px) rotate(45deg);
}

.hamburger span.open:nth-child(2) {
  opacity: 0;
}

.hamburger span.open:nth-child(3) {
  transform: translateY(-12px) rotate(-45deg);
}

/* ✅ 모바일 드롭다운 */
.mobile-dropdown {
  position: absolute;
  top: 60px;
  right: 0;
  background: white;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 15px;
  width: 220px;
  opacity: 0;
  visibility: hidden;
  transition: opacity 0.3s ease;
}

.mobile-dropdown.open {
  opacity: 1;
  visibility: visible;
}

/* ✅ 로그인/로그아웃 애니메이션 */
.fade-scale-enter-active, .fade-scale-leave-active {
  transition: opacity 0.3s ease, transform 0.3s ease;
}

.fade-scale-enter-from, .fade-scale-leave-to {
  opacity: 0;
  transform: scale(0.95);
}


@media (max-width: 1200px) {
  .userHi {
    display: none;
  }
}



</style>
