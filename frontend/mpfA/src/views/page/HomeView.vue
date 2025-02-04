<template>
  <div class="home-page">
    <RollingCard />
    <ContentCard />
  </div>
</template>

<script setup>
import ContentCard from "@/components/ContentCard.vue";
import RollingCard from "@/components/RollingCard.vue";
</script>

<style scoped>
/* ✅ 전체 페이지 가로 스크롤 방지 */
html, body {
  overflow-x: hidden;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}

/* ✅ 홈 화면 스타일 */
.home-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  max-width: 1200px; /* ✅ 중앙 정렬 */
  margin: 0 auto;
  overflow: hidden;
  padding: 0 20px;
  box-sizing: border-box;
}
</style>
