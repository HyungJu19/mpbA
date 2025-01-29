<template>
  <div class="auth-buttons">
    <transition name="fade-scale" mode="out-in">
      <div :key="authStore.user ? 'loggedIn' : 'loggedOut'" class="auth-item">
        <!-- ✅ 로그인 상태 -->
        <template v-if="authStore.user">
          <span class="user-id">{{ authStore.user.id }}님</span>
          <button @click="handleLogout">로그아웃</button>
        </template>

        <!-- ✅ 로그아웃 상태 -->
        <template v-else>
          <RouterLink to="/login">로그인</RouterLink>
          <RouterLink to="/register">회원가입</RouterLink>
        </template>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { useAuthStore } from "@/stores/useAuthStore.js";
import { useRouter } from "vue-router";
import { ref } from "vue";

const authStore = useAuthStore();
const router = useRouter();
const isLoggingOut = ref(false);

const handleLogout = async () => {
  isLoggingOut.value = true;
  await new Promise((resolve) => setTimeout(resolve, 500)); // ⏳ 애니메이션 대기 후 로그아웃 실행
  authStore.logout();
  isLoggingOut.value = false;
  router.push("/");
};
</script>

<style scoped>
.auth-buttons {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 10px;
  min-height: 40px;
}

/* ✅ 로그인/로그아웃 버튼 크기 유지 */
.auth-item {
  display: flex;
  align-items: center;
  gap: 10px;
  min-width: 180px; /* ✅ 크기 유지 */
}

/* ✅ 자연스럽게 사라지고 등장하는 애니메이션 */
.fade-scale-enter-active, .fade-scale-leave-active {
  transition: opacity 0.5s ease, transform 0.5s ease;
}

.fade-scale-enter-from {
  opacity: 0;
  transform: scale(0.9); /* ✅ 작아지면서 등장 */
}

.fade-scale-leave-to {
  opacity: 0;
  transform: scale(0.9); /* ✅ 작아지면서 사라짐 */
}

.auth-buttons a {
  color: white;
  text-decoration: none;
  font-weight: bold;
  padding: 6px 12px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 4px;
  transition: background-color 0.3s ease-in-out;
}

.auth-buttons a:hover {
  background-color: rgba(255, 255, 255, 0.4);
}

button {
  background-color: #ff9800;
  border: none;
  padding: 6px 12px;
  cursor: pointer;
  color: white;
  font-weight: bold;
  border-radius: 4px;
  transition: background-color 0.3s ease-in-out;
}

button:hover {
  background-color: #e68900;
}

.user-id {
  font-weight: bold;
}
</style>
