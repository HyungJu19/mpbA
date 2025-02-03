<template>
  <div class="product-detail">
    <!-- ✅ 상단: 상품 이미지 + 정보 -->
    <div class="product-content">
      <div class="product-image">
        <img :src="getImagePath(product.image)" alt="상품 이미지" />
      </div>
      <div class="product-info">
        <h1 class="product-title">{{ product.title }}</h1>
        <p class="product-price">{{ formatPrice(product.price) }}</p>
        <div class="product-actions">
          <button class="btn-buy">구매하기</button>
          <button class="btn-cart">장바구니</button>
        </div>
      </div>
    </div>

    <!-- ✅ 구분선 -->
    <hr class="separator" />

    <!-- ✅ 하단: 상세 이미지 (세로 정렬) -->
    <div class="product-detail-images">
      <img v-for="index in 6" :key="index" :src="getImagePath(`detail${index}.png`)" class="detail-img" />
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

const products = ref([
  { id: 1, title: "모던 블랙", price: 59000, image: "detailmain1.png" },
  { id: 2, title: "화이트 미니멀", price: 68000, image: "detailmain2.png" },
  { id: 3, title: "클래식 우드", price: 72000, image: "detailmain3.png" },
]);

const product = computed(() => {
  return products.value.find((p) => p.id == route.params.id) || {};
});

const getImagePath = (imageName) => {
  return new URL(`/src/assets/img/${imageName}`, import.meta.url).href;
};

const formatPrice = (price) => {
  return new Intl.NumberFormat("ko-KR", {style: "currency", currency: "KRW"}).format(price);
};
</script>
<style scoped>
/* ✅ 전체 화면에서 가로 스크롤 방지 */
html, body {
  overflow-x: hidden;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* ✅ 전체 레이아웃 */
.product-detail {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 1200px;
  width: 100vw; /* ✅ 가로 초과 방지 */
  margin: 60px auto;
  padding: 20px;
  box-sizing: border-box; /* ✅ 패딩 포함 */
}

/* ✅ 상품 상단 */
.product-content {
  display: flex;
  gap: 40px;
  width: 100%;
  max-width: 100%; /* ✅ 가로 초과 방지 */
  align-items: center;
  justify-content: center;
  padding: 40px;
  background: #f8f8f8;
  border-radius: 16px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
  flex-wrap: wrap; /* ✅ 가로 공간 부족 시 줄바꿈 */
}

/* ✅ 상품 이미지 */
.product-image {
  flex: 1;
  max-width: 500px;
  display: flex;
  justify-content: center;
  width: 100%;
}

.product-image img {
  width: 100%;
  max-width: 100%; /* ✅ 가로 초과 방지 */
  border-radius: 12px;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.product-image img:hover {
  transform: scale(1.03);
}

/* ✅ 상품 정보 - 중앙 정렬 */
.product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  justify-content: center;
  width: 100%;
}

.product-title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 10px;
}

.product-price {
  font-size: 22px;
  font-weight: bold;
  color: #e74c3c;
  margin-bottom: 20px;
}

/* ✅ 버튼 */
.product-actions {
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;
}

.btn-buy,
.btn-cart {
  padding: 12px 24px;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  transition: all 0.3s ease;
  width: 45%; /* ✅ 반응형 조정 */
  max-width: 200px;
}

.btn-buy {
  background: #e74c3c;
  color: white;
}

.btn-buy:hover {
  background: #c0392b;
}

.btn-cart {
  background: #333;
  color: white;
}

.btn-cart:hover {
  background: #222;
}

/* ✅ 구분선 */
.separator {
  width: 80%;
  margin: 50px auto;
  border: none;
  height: 2px;
  background: #ddd;
}

/* ✅ 하단 상세 이미지 */
.product-detail-images {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 25px;
  width: 100%;
  max-width: 100%;
}

.detail-img {
  width: 100%;
  max-width: 800px; /* ✅ 가로 초과 방지 */
  border-radius: 12px;
  object-fit: cover;
  box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.15);
}

/* ✅ 반응형 */
@media (max-width: 991px) {
  .product-content {
    flex-direction: column;
    text-align: center;
    gap: 20px;
  }

  .product-info {
    align-items: center;
  }

  .product-actions {
    flex-direction: column;
    width: 100%;
    align-items: center;
  }

  .btn-buy,
  .btn-cart {
    width: 100%;
    max-width: 300px;
  }
}
</style>
