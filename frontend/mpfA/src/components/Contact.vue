<template>
  <div class="contact-wrapper">
    <!-- ✅ 로그인 모달 -->
    <AuthModal v-if="showLoginModal" @close="handleLoginClose" />

    <!-- ✅ 1:1 문의 폼 -->
    <transition name="fade-slide">
      <div v-if="authStore.user" class="contact-container">
        <!-- 📌 제목 -->
        <h1 class="title">1:1 문의</h1>
        <p class="description">
          궁금하신 점을 남겨주시면 접수 순서대로 답변 드리겠습니다.
        </p>

        <!-- 🙋‍♂️ 유저 환영 메시지 -->
        <div class="user-info">
          <span class="user-icon">👋</span>
          <p class="user-greeting"><strong>{{ authStore.user.id }}님</strong></p>
        </div>

        <!-- ℹ️ 안내 텍스트 -->
        <div class="info-box">
          <p>
            - 메일 문의 전 먼저 확인해 보세요! -
          </p>
          <p>1. 자주 찾는 도움말로 빠르게 궁금한 점을 해결할 수 있습니다.</p>
          <p>2. 스마트봇을 통해 빠르게 안내받을 수 있습니다.</p>
        </div>

        <!-- 📝 문의 입력 필드 -->
        <div class="input-group">
          <textarea v-model="message" placeholder="문의 내용을 입력하세요..." class="input-box"></textarea>
        </div>

        <!-- 📩 문의하기 버튼 -->
        <button @click="submitInquiry" class="submit-btn">
          문의하기
        </button>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useAuthStore } from "@/stores/useAuthStore.js";

const authStore = useAuthStore();
const router = useRouter();
const route = useRoute();

// ✅ 로그인 체크 후 자동 리디렉트
onMounted(() => {
  if (!authStore.user) {
    router.replace({ path: "/login-required", query: { redirect: route.fullPath } });
  }
});
</script>


<style scoped>
/* ✅ 전체 컨테이너 */

.contact-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh; /* 🔥 높이를 최소 100vh로 설정 */
  padding: 20px;
  background: linear-gradient(to bottom, #eceff1, #ffffff);
}


/* ✅ 문의 폼 카드 */
.contact-container {
  width: 100%;
  max-width: 600px;
  background: white;
  border-radius: 12px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.15); /* 📌 입체감 */
  text-align: center;
  padding: 40px;
  position: relative;
  transition: all 0.3s ease-in-out;
}

/* ✅ 제목 */
.title {
  font-size: 26px;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 10px;
}

/* ✅ 설명 */
.description {
  font-size: 16px;
  margin-bottom: 20px;
  color: #555;
}

/* ✅ 유저 정보 */
.user-info {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  font-size: 18px;
  font-weight: bold;
  color: #007BFF;
  margin-bottom: 15px;
}

.user-icon {
  font-size: 24px;
}

/* ✅ 안내 박스 */
.info-box {
  background: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
  font-size: 14px;
  color: #666;
  line-height: 1.6;
  margin-bottom: 20px;
  text-align: left;
}

/* ✅ 입력 박스 */
.input-group {
  width: 100%;
  position: relative;
}

.input-box {
  width: 100%;
  height: 120px;
  border: none;
  border-radius: 8px;
  padding: 15px;
  font-size: 16px;
  background: #f5f5f5;
  transition: all 0.3s ease-in-out;
  outline: none;
  resize: none;
}

.input-box:focus {
  background: white;
  box-shadow: 0px 5px 10px rgba(0, 123, 255, 0.3);
}

/* ✅ 버튼 */
.submit-btn {
  background: linear-gradient(to right, #007BFF, #00c6ff);
  color: white;
  font-size: 18px;
  font-weight: bold;
  padding: 12px;
  width: 100%;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
  margin-top: 15px;
}

.submit-btn:hover {
  background: linear-gradient(to right, #0056b3, #00a2ff);
  transform: translateY(-2px);
  box-shadow: 0px 5px 15px rgba(0, 123, 255, 0.4);
}

/* ✅ 애니메이션 */
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

/* 📱 반응형 스타일 */
@media (max-width: 768px) {
  .contact-container {
    padding: 25px;
  }

  .input-box {
    font-size: 14px;
  }

  .submit-btn {
    font-size: 16px;
    padding: 10px;
  }
}
</style>
