<template>
  <header>
    <NavbarView />
  </header>

  <main class="content">
    <router-view v-slot="{ Component }">
      <transition name="fade-slide" mode="out-in">
        <component :is="Component" v-if="Component" />
      </transition>
    </router-view>
  </main>

  <!-- ✅ 로그인 모달 추가 -->
  <LoginForm v-if="showLoginModal" @close="showLoginModal = false" />
</template>

<script setup>
import { ref, onMounted, provide } from "vue";
import { useAuthStore } from "@/stores/useAuthStore.js";
import NavbarView from "@/views/NavbarView.vue";
import LoginForm from "@/components/LoginForm.vue";

const authStore = useAuthStore();
const showLoginModal = ref(false); // ✅ 로그인 모달 상태 추가

onMounted(() => {
  console.log("🚀 앱 실행됨: 사용자 정보 불러오기");
  authStore.fetchUser();
});

// ✅ 로그인 모달 상태를 Provide하여 하위 컴포넌트에서 접근 가능
provide("showLoginModal", showLoginModal);
</script>

<style scoped>
/* ✅ 페이지 전환 애니메이션 최적화 */
.fade-slide-enter-active, .fade-slide-leave-active {
  transition: opacity 0.3s ease;
}

.fade-slide-enter-from, .fade-slide-leave-to {
  opacity: 0;
}
</style>
