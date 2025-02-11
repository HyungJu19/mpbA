<template>
  <div class="preview-container">
    <h2>청첩장</h2>

    <!-- ✅ 데이터가 없을 경우 안내 메시지 -->
    <p v-if="componentList.length === 0" class="empty-message">미리볼 청첩장 정보가 없습니다.</p>

    <!-- ✅ 동적 컴포넌트 렌더링 -->
    <div v-for="(component, index) in componentList" :key="index">
      <component
          v-if="getComponent(component.type)"
          :is="getComponent(component.type)"
          v-model="component.data.value"
      />
    </div>

    <!-- ✅ 뒤로 가기 버튼 -->
    <button class="back-btn" @click="goBack">뒤로 가기</button>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { usePreviewStore } from "@/stores/usePreviewStore.js";
import { storeToRefs } from "pinia";
import { onMounted, markRaw } from "vue";
import RepresentativeImage from "@/components/wedding/RepresentativeImage.vue";
import SmallImageGrid from "@/components/wedding/SmallImageGrid.vue";
import TextEditor from "@/components/wedding/TextEditor.vue";
import BankInfo from "@/components/wedding/BankInfo.vue";
import LocationMap from "@/components/wedding/LocationMap.vue";
import EventCalendar from "@/components/wedding/EventCalendar.vue";

const router = useRouter();
const previewStore = usePreviewStore();
const { componentList } = storeToRefs(previewStore);

// ✅ 동적 컴포넌트 매핑 (markRaw 적용)
const componentRegistry = {
  RepresentativeImage: markRaw(RepresentativeImage),
  SmallImageGrid: markRaw(SmallImageGrid),
  TextEditor: markRaw(TextEditor),
  BankInfo: markRaw(BankInfo),
  LocationMap: markRaw(LocationMap),
  EventCalendar: markRaw(EventCalendar),
};

// ✅ 올바른 컴포넌트를 반환하는 함수
const getComponent = (type) => componentRegistry[type] || null;

// ✅ 뒤로 가기 버튼
const goBack = () => {
  router.back();
};

// ✅ `onMounted()`에서 Pinia 데이터 확인
onMounted(() => {
  console.log("📌 Pinia에서 불러온 미리보기 데이터:", componentList.value);
});
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
