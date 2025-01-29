<template>
  <div class="preview-container">
    <h2>📱 모바일 청첩장 미리보기</h2>
    <div v-for="(component, index) in parsedData" :key="index">
      <component :is="getComponent(component.type)" v-bind="component.data"></component>
    </div>
    <button class="back-btn" @click="goBack">뒤로 가기</button>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import RepresentativeImage from "@/views/RepresentativeImage.vue";
import SmallImageGrid from "@/views/SmallImageGrid.vue";
import TextEditor from "@/views/TextEditor.vue";
import BankInfo from "@/views/BankInfo.vue";
import LocationMap from "@/views/LocationMap.vue";
import EventCalendar from "@/views/EventCalendar.vue";

// ✅ 동적 컴포넌트 등록
const componentRegistry = {
  RepresentativeImage,
  SmallImageGrid,
  TextEditor,
  BankInfo,
  LocationMap,
  EventCalendar
};

// ✅ 동적 컴포넌트 반환
const getComponent = (type) => componentRegistry[type] || null;

const route = useRoute();
const router = useRouter();

// ✅ JSON 데이터 파싱
const parsedData = route.query.data ? JSON.parse(route.query.data) : [];

// ✅ 뒤로 가기
const goBack = () => {
  router.push("/");
};
</script>

<style scoped>
.preview-container {
  max-width: 800px;
  margin: auto;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.back-btn {
  margin-top: 20px;
  padding: 10px 15px;
  background: #ff4d4d;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.2s;
}

.back-btn:hover {
  background: #e60000;
}
</style>
