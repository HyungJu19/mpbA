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
/* ✅ 홈 화면 스타일 */
.home-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
}
</style>
