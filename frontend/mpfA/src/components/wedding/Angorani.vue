<template>
  <div class="preview-container">
<!--    <h2 v-if="authStore.user?.persName">{{ authStore.user.persName }}님 청첩장</h2>-->
<!--    <h2 v-else>청첩장</h2>-->
    <h2>청첩장</h2>

    <!-- ✅ 데이터가 없을 경우 안내 메시지 -->
    <p v-if="componentList.length === 0" class="empty-message">미리볼 청첩장 정보가 없습니다.</p>

    <!-- ✅ 동적 컴포넌트 렌더링 -->
    <div v-for="(component, index) in componentList" :key="index">
      <component
          v-if="getComponent(component.type)"
          :is="getComponent(component.type)"
          v-model="component.data.value"  /> <!-- ✅ `v-model` 적용 -->

    </div>

    <!-- ✅ 뒤로 가기 버튼 -->
    <button class="back-btn" @click="goBack">뒤로 가기</button>
  </div>
</template>
<script setup>
import { useRouter } from "vue-router";
import { usePreviewStore } from "@/stores/usePreviewStore.js";
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue";
import RepresentativeImage from "@/components/wedding/RepresentativeImage.vue";
import SmallImageGrid from "@/components/wedding/SmallImageGrid.vue";
import TextEditor from "@/components/wedding/TextEditor.vue";
import BankInfo from "@/components/wedding/BankInfo.vue";
import LocationMap from "@/components/wedding/LocationMap.vue";
import EventCalendar from "@/components/wedding/EventCalendar.vue";

const router = useRouter();
const previewStore = usePreviewStore();
const { componentList } = storeToRefs(previewStore);

// ✅ 동적 컴포넌트 매핑
const componentRegistry = {
  RepresentativeImage,
  SmallImageGrid,
  TextEditor,
  BankInfo,
  LocationMap,
  EventCalendar,
};

// ✅ `localStorage`에서 데이터 불러오기
const loadFromLocalStorage = () => {
  const savedData = localStorage.getItem("componentList");
  if (savedData) {
    componentList.value = JSON.parse(savedData);
    console.log("📌 로컬스토리지에서 데이터 복구 완료:", componentList.value);
  }
};

// ✅ 올바른 컴포넌트를 반환하는 함수
const getComponent = (type) => {
  return componentRegistry[type] || null;
};

// ✅ 미리보기 페이지가 로드될 때 `localStorage`에서 데이터 불러오기
onMounted(() => {
  loadFromLocalStorage();
});

// ✅ 뒤로 가기 버튼
const goBack = () => {
  router.back();
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

/* ✅ 데이터 없을 때 메시지 */
.empty-message {
  color: #777;
  font-size: 16px;
  margin-top: 20px;
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
