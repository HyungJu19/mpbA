<!-- ✅ DynamicComponentManager.vue -->
<template>
  <div class="container">
    <h2>📌 청첩장 구성</h2>

    <p v-if="componentList.length === 0" class="empty-message">추가할 내용을 입력하세요.</p>

    <div class="component-list" v-if="componentList.length > 0">
      <div v-for="(element, index) in componentList" :key="element.id" class="component-box">
        <div class="left-controls">
          <div class="order-number">No. {{ index + 1 }}</div>
          <div class="move-buttons">
            <button @click="moveToTop(index)" :disabled="index === 0">🔝</button>
            <button @click="moveUp(index)" :disabled="index === 0">▲</button>
            <button @click="moveDown(index)" :disabled="index === componentList.length - 1">▼</button>
            <button @click="moveToBottom(index)" :disabled="index === componentList.length - 1">🔽</button>
          </div>
        </div>

        <!-- ✅ 동적 컴포넌트 -->
        <div class="component-content">
          <component v-if="element.component" :is="element.component" v-model="element.data.value" />
        </div>

        <button class="delete-btn" @click="removeComponent(element.id)">🗑</button>
      </div>
    </div>

    <!-- ✅ 추가 버튼을 `@add-component="addComponent"`로 연결 -->
    <AddButton @add-component="addComponent" class="vmf" />

    <div class="bottom-buttons" v-if="componentList.length > 0">
      <button class="confirm-btn" @click="finalizeComponents">✔ 완료</button>
      <button class="preview-btn" @click="openPreview">📱 미리보기</button>
    </div>
  </div>
</template>

<script setup>
import { nextTick } from "vue"; // ✅ nextTick 추가
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { usePreviewStore } from "@/stores/usePreviewStore.js";
import AddButton from "@/components/wedding/AddButton.vue";

const router = useRouter();
const componentStore = usePreviewStore();
const { componentList } = storeToRefs(componentStore);

// ✅ 컴포넌트 추가 (Pinia 사용)
const addComponent = (type) => {
  console.log(`📌 DynamicComponentManager에서 추가 요청: ${type}`);
  componentStore.addComponent(type);
};

// ✅ 미리보기 버튼 (모바일에서 `nextTick()`을 이용하여 반응 개선)
const openPreview = async () => {
  await nextTick(); // ✅ Vue의 비동기 업데이트 기다리기
  router.push("/angorani");
};
// ✅ 완료 버튼 (URL 생성)
const finalizeComponents = () => {
  const finalUrl = `${window.location.origin}/invitation/${Date.now()}`;
  console.log("📌 최종 URL:", finalUrl);
};
</script>

<style scoped>
/* ✅ 미리보기 & 완료 버튼을 하단 고정 */
.bottom-buttons {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background: white;
  padding: 10px 0;
  box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-around;
  z-index: 1000;
}

/* ✅ 추가 버튼 스타일 */
.vmf {
  position: fixed;
  bottom: 80px;
  right: 20px;
  z-index: 1100;
}

/* ✅ 반응형 */
@media (max-width: 768px) {
  .vmf {
    bottom: 60px;
    right: 10px;
  }
}
</style>
