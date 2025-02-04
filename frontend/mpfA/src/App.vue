<template>
  <header>
    <NavbarView v-if="!hideNavbar" />
  </header>

  <main class="content">
    <router-view v-slot="{ Component }">
      <transition name="fade-slide" mode="out-in">
        <component :is="Component" v-if="Component" />
      </transition>
    </router-view>
  </main>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import { useAuthStore } from "@/stores/useAuthStore.js";
import NavbarView from "@/views/NavbarView.vue";

const authStore = useAuthStore();
const route = useRoute(); // ✅ 현재 경로 가져오기

onMounted(() => {
  console.log("🚀 앱 실행됨: 사용자 정보 불러오기");
  authStore.fetchUser();

  // ✅ 스크롤 이벤트 리스너 추가 (부드러운 스크롤)
  window.addEventListener("scroll", () => {
    document.documentElement.style.scrollBehavior = "smooth";
  });
});

/* ✅ 현재 페이지가 로그인, 회원가입 페이지이면 Navbar 숨기기 */
const hideNavbar = computed(() => ["/login", "/register"].includes(route.path));
</script>

<style scoped>

/* ✅ 전체 페이지에서 부드러운 스크롤 적용 */
html, body {
  scroll-behavior: smooth;
}

/* ✅ 페이지 전환 애니메이션 최적화 */
.fade-slide-enter-active, .fade-slide-leave-active {
  transition: opacity 0.3s ease;
}

.fade-slide-enter-from, .fade-slide-leave-to {
  opacity: 0;
}
</style>
