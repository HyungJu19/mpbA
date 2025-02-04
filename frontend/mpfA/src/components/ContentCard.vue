<template>
  <div class="container">
    <hr class="separator" />
    <div class="card-mainTitle">
      <h2>스킨디자인</h2>
      <p>진심을 담아 정성껏 디자인하겠습니다.</p>
    </div>

    <div class="grid">
      <!-- 각 카드 -->
      <div
          class="grid-item"
          v-for="(card, index) in visibleCards"
          :key="index"
      >
        <div class="card">
          <!-- ✅ 이미지 영역 -->
          <div class="card-image">
            <img :src="getImagePath(card.image)" alt="카드 이미지" />
          </div>
          <!-- ✅ 카드 본문 (호버 시 표시) -->
          <div class="card-overlay">
            <h5 class="card-title">{{ card.title }}</h5>
            <p class="card-text">{{ card.description }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from "vue";

// 카드 데이터 생성
const cards = ref(
    Array.from({ length: 20 }, (_, i) => ({
      title: `Card Title ${i + 1}`,
      description: `This is the description for card ${i + 1}.`,
      image: `po${i + 1}.jpg`,
    }))
);

// 화면 크기에 따라 표시할 카드 개수 결정
const windowWidth = ref(window.innerWidth);
const visibleCards = computed(() => {
  return windowWidth.value > 899 ? cards.value : cards.value.slice(0, 4);
});

// 창 크기 변경 감지
const updateWindowWidth = () => {
  windowWidth.value = window.innerWidth;
};

onMounted(() => {
  window.addEventListener("resize", updateWindowWidth);
});
onUnmounted(() => {
  window.removeEventListener("resize", updateWindowWidth);
});

// 이미지 경로 계산
const getImagePath = (imageName) => {
  return new URL(`/src/assets/img/po/${imageName}`, import.meta.url).href;
};
</script>

<style scoped>
/* ✅ 전체 페이지 가로 스크롤 방지 */
html, body {
  overflow-x: hidden;
  width: 100vw;
  margin: 0;
  padding: 0;
}

/* ✅ 중앙 정렬을 위한 컨테이너 */
.container {
  width: 100%;
  max-width: 1200px; /* ✅ 최대 너비 제한 */
  margin: 0 auto; /* ✅ 중앙 정렬 */
  padding: 20px;
  box-sizing: border-box;
  overflow: hidden;
}

/* ✅ 카드 제목 */
.card-mainTitle {
  height: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 100%;
  margin-bottom: 50px;
}

@media (max-width: 991px) {
  .card-mainTitle {
    height: 200px;
  }
}

/* ✅ 그리드 레이아웃 */
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); /* ✅ 반응형 */
  gap: 15px;
  justify-content: center; /* ✅ 중앙 정렬 */
  max-width: 100%;
  width: 100%;
}

@media (max-width: 899px) {
  .grid {
    display: flex;
    flex-wrap: wrap;
    justify-content: center; /* ✅ 모바일에서도 중앙 정렬 유지 */
  }
}

/* ✅ 개별 카드 */
.grid-item {
  display: flex;
  justify-content: center; /* ✅ 카드 개별 중앙 정렬 */
  width: 100%;
  max-width: 300px;
}

/* ✅ 카드 스타일 */
.card {
  width: 100%;
  aspect-ratio: 550 / 330;
  background: white;
  border-radius: 8px;
  overflow: hidden;
  position: relative;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
}

/* ✅ 이미지 영역 */
.card-image {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* ✅ 카드 오버레이 */
.card-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transition: opacity 0.3s ease;
  color: white;
}

.card:hover .card-overlay {
  opacity: 1;
}

/* ✅ 카드 타이틀 */
.card-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 10px;
  text-align: center;
}

/* ✅ 카드 설명 */
.card-text {
  font-size: 1rem;
  text-align: center;
  line-height: 1.4;
  padding: 0 20px;
}

/* ✅ 구분선 */
.separator {
  width: 80%;
  margin: 80px auto;
  border: none;
  height: 2px;
  background: #ddd;
}
</style>
