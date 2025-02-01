<template>
  <div class="product-list">
    <h1 class="section-title">상세페이지</h1>
    <h3 style="margin-bottom: 120px">앙고라니에서만 가능한 실용적인 상세페이지 디자인을 경험하세요</h3>
    <!-- ✅ 구분선 -->
    <hr class="separator" />

    <transition-group name="fade-in" tag="div" class="grid-container" leave-active-class="none">
      <div v-for="(item, index) in products" :key="index" class="product-card">
        <RouterLink :to="'/product/' + item.id">
          <img :src="getImagePath(item.image)" class="product-img" />
        </RouterLink>
        <div class="product-info">
          <h3 class="product-title">{{ item.title }}</h3>
          <p class="product-price">{{ formatPrice(item.price) }}</p>
        </div>
      </div>
    </transition-group>
  </div>
</template>

<script setup>
import { ref } from "vue";

const products = ref([
  { id: 1, title: "모던 블랙", price: 10000, image: "detailmain1.png" },
  { id: 2, title: "화이트 미니멀", price: 20000, image: "detailmain2.png" },
  { id: 3, title: "클래식 우드", price: 30000, image: "detailmain3.png" },
]);

const getImagePath = (imageName) => {
  return new URL(`/src/assets/img/${imageName}`, import.meta.url).href;
};

const formatPrice = (price) => {
  return new Intl.NumberFormat("ko-KR", { style: "currency", currency: "KRW" }).format(price);
};
</script>

<style scoped>
/* ✅ 전체 목록 스타일 */
.product-list {
  max-width: 1200px;
  margin: 40px auto;
  text-align: center;
}

/* ✅ 섹션 제목 */
.section-title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 2px;
  margin-top: 100px;
}

/* ✅ 구분선 */
.separator {
  width: 80%;
  margin: 50px auto;
  border: none;
  height: 2px;
  background: #ddd;
}

/* ✅ 그리드 */
.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

/* ✅ 개별 카드 */
.product-card {
  background: white;
  border-radius: 10px;
  overflow: hidden;
  transition: transform 0.3s ease-in-out;
}

.product-card:hover {
  transform: translateY(-5px);
}

/* ✅ 상품 이미지 */
.product-img {
  width: 100%;
  height: 250px;
  object-fit: cover;
}

/* ✅ 상품 정보 */
.product-info {
  padding: 15px;
}

.product-title {
  font-size: 18px;
  font-weight: bold;
}

.product-price {
  font-size: 16px;
  color: #e74c3c;
  font-weight: bold;
  margin-top: 5px;
}

/* ✅ 리스트 애니메이션 */
.fade-in-enter-active {
  transition: opacity 0.3s ease-in, transform 0.3s ease-in;
}

.fade-in-enter-from {
  opacity: 0;
  transform: translateY(10px);
}

.none {
  transition: none; /* ✅ 사라질 때 즉시 제거 */
}

/* ✅ 반응형 (태블릿 & 모바일) */
@media (max-width: 991px) {
  .grid-container {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 600px) {
  .grid-container {
    grid-template-columns: repeat(1, 1fr);
  }
}
</style>
