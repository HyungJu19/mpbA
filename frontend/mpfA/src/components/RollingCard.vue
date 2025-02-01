<template>
  <div class="container">
    <div class="content-wrapper">
      <!-- ✅ 왼쪽 회전 카드 -->
      <div class="flip-card">
        <div class="flip-card-inner">
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

      <!-- ✅ 오른쪽 컨텐츠 (2개씩 배치) -->
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
import { ref } from "vue";

// 오른쪽 컨텐츠 4개 데이터
const contents = ref([
  {
    icon: "⭐",
    title: "Feature One",
    description: "This is the first feature description.",
  },
  {
    icon: "🚀",
    title: "Feature Two",
    description: "This is the second feature description.",
  },
  {
    icon: "🔒",
    title: "Feature Three",
    description: "This is the third feature description.",
  },
  {
    icon: "⚡",
    title: "Feature Four",
    description: "This is the fourth feature description.",
  },
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
}

/* ✅ 컨텐츠 배치 (회전 카드 + 오른쪽 컨텐츠) */
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

/* ✅ 회전 카드 스타일 (앞뒤 크기 동일) */
.flip-card {
  width: 300px;
  height: 400px;
  position: relative;
  overflow: hidden; /* ✅ 내부 크기 초과 방지 */
}

/* ✅ 내부 카드 (회전) */
.flip-card-inner {
  width: 100%;
  height: 100%;
  transform-style: preserve-3d;
  transition: transform 0.6s ease;
  transform-origin: center center; /* ✅ 중앙 정렬 */
}

.flip-card:hover .flip-card-inner {
  transform: rotateY(180deg);
}

/* ✅ 앞면 & 뒷면 (크기 문제 해결) */
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
  box-sizing: border-box; /* ✅ 크기 초과 문제 해결 */
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
/*회전카드 디버깅용
.flip-card {
  border: 2px solid blue;
}

.flip-card-front, .flip-card-back {
  border: 2px solid red;
}*/


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
