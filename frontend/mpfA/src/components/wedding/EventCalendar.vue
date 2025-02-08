<template>
  <div class="event-calendar">
    <h3>📅 예식 일정</h3>
    <p>예식 날짜: {{ weddingDate }}</p>
  </div>
</template>

<script setup>
import { ref } from "vue";

const weddingDate = ref("2025년 5월 20일 (토요일)");
</script>

<style scoped>
.event-calendar {
  padding: 10px;

}
</style>
