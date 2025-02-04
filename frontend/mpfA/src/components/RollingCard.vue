<template>
  <div class="container">
    <div class="content-wrapper">
      <!-- ✅ 1000px 이하에서만 터치 이벤트 작동 -->
      <div class="flip-card" @click="toggleFlip" @touchstart="toggleFlip">
        <div class="flip-card-inner" :class="{ flipped: isFlipped }">
          <!-- 앞면 -->
          <div class="flip-card-front">
            <h3>Card Title</h3>
            <p>This is a flip card without an image.</p>
          </div>
          <!-- 뒷면 -->
          <div class="flip-card-back">
            <h3>More Info</h3>
            <p>Additional details about this card.</p>
            <button class="btn">Learn More</button>
          </div>
        </div>
      </div>

      <!-- ✅ 오른쪽 컨텐츠 -->
      <div class="right-content">
        <div class="content-grid">
          <div v-for="(item, index) in contents" :key="index" class="content-item">
            <div class="icon">{{ item.icon }}</div>
            <div>
              <h4>{{ item.title }}</h4>
              <p>{{ item.description }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";

const isFlipped = ref(false);
let flipTimeout = null;

// ✅ 터치 이벤트 핸들러 (1000px 이하에서만 클릭/터치 동작)
const toggleFlip = () => {
  if (window.innerWidth > 1000) return; // ✅ PC에서는 클릭 이벤트 차단

  if (flipTimeout) {
    clearTimeout(flipTimeout); // ✅ 기존 타이머 제거
    flipTimeout = null; // ✅ 초기화
  }

  isFlipped.value = true; // ✅ 카드 회전

  flipTimeout = setTimeout(() => {
    isFlipped.value = false; // ✅ 4초 후 원래 상태로 복귀
  }, 2000);
};

// ✅ 창 크기 변경 감지 (모바일 환경에서만 이벤트 활성화)
const handleResize = () => {
  if (window.innerWidth > 1000) {
    isFlipped.value = false;
    if (flipTimeout) {
      clearTimeout(flipTimeout);
      flipTimeout = null;
    }
  }
};

// ✅ 이벤트 리스너 추가 및 제거
onMounted(() => {
  window.addEventListener("resize", handleResize);
});
onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
});

const contents = ref([
  { icon: "⭐", title: "Feature One", description: "This is the first feature description." },
  { icon: "🚀", title: "Feature Two", description: "This is the second feature description." },
  { icon: "🔒", title: "Feature Three", description: "This is the third feature description." },
  { icon: "⚡", title: "Feature Four", description: "This is the fourth feature description." },
]);
</script>

<style scoped>
/* ✅ 전체 컨테이너 */
.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  display: flex;
  justify-content: center; /* ✅ 중앙 정렬 */
  perspective: 1200px; /* ✅ 3D 회전 효과 */
  box-sizing: border-box;
}

/* ✅ 컨텐츠 배치 */
.content-wrapper {
  display: flex;
  align-items: center;
  gap: 30px;
}

@media (max-width: 1000px) {
  .content-wrapper {
    flex-direction: column;
    align-items: center;
  }
}

/* ✅ 회전 카드 */
.flip-card {
  width: 300px;
  height: 400px;
  position: relative;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

/* ✅ 내부 카드 (회전) */
.flip-card-inner {
  width: 100%;
  height: 100%;
  transform-style: preserve-3d;
  transition: transform 0.6s ease;
  transform-origin: center center;
}

/* ✅ PC(1000px 이상)에서는 hover 시 회전 */
@media (min-width: 1000px) {
  .flip-card:hover .flip-card-inner {
    transform: rotateY(180deg);
  }
}

/* ✅ 모바일(1000px 이하)에서는 터치/클릭 시 회전 */
@media (max-width: 1000px) {
  .flipped {
    transform: rotateY(180deg);
  }
}

/* ✅ 카드 앞면 & 뒷면 */
.flip-card-front,
.flip-card-back {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  backface-visibility: hidden;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  box-sizing: border-box;
}

.flip-card-front {
  background: #ff5e57;
  color: white;
}

.flip-card-back {
  background: #ff5e57;
  color: white;
  transform: rotateY(180deg);
}

/* ✅ 버튼 스타일 */
.btn {
  margin-top: 10px;
  padding: 8px 16px;
  background: white;
  color: #ff5e57;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
}

.btn:hover {
  background: #ddd;
}

/* ✅ 오른쪽 컨텐츠 스타일 */
.right-content {
  flex: 1;
  display: flex;
  justify-content: center;
}

/* ✅ 2x2 배치 */
.content-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* ✅ 2개씩 배치 */
  gap: 20px;
  width: 100%;
}

@media (max-width: 1000px) {
  .content-grid {
    grid-template-columns: 1fr; /* ✅ 1000px 이하: 세로 1열 */
  }
  .flip-card {
    width: 100%; /* ✅ 화면 너비에 맞게 */
    display: flex;
    justify-content: center;
  }

  .right-content {
    width: 100%;
    display: flex;
    justify-content: center;
  }
}

/* ✅ 컨텐츠 아이템 스타일 */
.content-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px;
  border-radius: 8px;
  background: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.icon {
  font-size: 24px;
  background: #ff5e57;
  color: white;
  padding: 10px;
  border-radius: 50%;
}
</style>
