<template>
  <div class="container">
    <h2>📌 청첩장 구성</h2>

    <!-- ✅ 데이터가 없을 경우 안내 메시지 -->
    <p v-if="componentList.length === 0" class="empty-message">추가할 내용을 입력하세요.</p>

    <!-- ✅ 리스트 -->
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

        <!-- ✅ 삭제 버튼 -->
        <button class="delete-btn" @click="removeComponent(element.id)">🗑</button>
      </div>
    </div>

    <!-- ✅ 추가 버튼 -->
    <AddButton @add-component="addComponent"  class="vmf"/>

    <!-- ✅ 최종 확인 및 미리보기 버튼 -->
    <div class="bottom-buttons" v-if="componentList.length > 0">
      <button class="confirm-btn" @click="finalizeComponents">✔ 완료</button>
      <button class="preview-btn" @click="openPreview">📱 미리보기</button>
    </div>

    <!-- ✅ 공유할 수 있는 최종 URL 표시 -->
    <div v-if="finalUrl" class="share-url">
      🔗 공유 링크: <a :href="finalUrl" target="_blank">{{ finalUrl }}</a>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, nextTick, markRaw } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { usePreviewStore } from "@/stores/usePreviewStore.js";

// ✅ 동적 컴포넌트 import
import AddButton from "@/components/wedding/AddButton.vue";
import RepresentativeImage from "@/components/wedding/RepresentativeImage.vue";
import SmallImageGrid from "@/components/wedding/SmallImageGrid.vue";
import TextEditor from "@/components/wedding/TextEditor.vue";
import BankInfo from "@/components/wedding/BankInfo.vue";
import LocationMap from "@/components/wedding/LocationMap.vue";
import EventCalendar from "@/components/wedding/EventCalendar.vue";

const router = useRouter();
const componentStore = usePreviewStore();
const { componentList } = storeToRefs(componentStore);
const finalUrl = ref("");

// ✅ 동적 컴포넌트 등록 (이걸 사용해서 로컬스토리지의 데이터를 다시 매핑함)

// ✅ 동적 컴포넌트 등록 (markRaw 적용)
const componentRegistry = {
  RepresentativeImage: markRaw(RepresentativeImage),
  SmallImageGrid: markRaw(SmallImageGrid),
  TextEditor: markRaw(TextEditor),
  BankInfo: markRaw(BankInfo),
  LocationMap: markRaw(LocationMap),
  EventCalendar: markRaw(EventCalendar),
};

// ✅ 로컬스토리지에서 데이터 불러오기 (markRaw 적용)
const loadFromLocalStorage = async () => {
  const savedData = localStorage.getItem("componentList");

  if (savedData) {
    try {
      const parsedData = JSON.parse(savedData);

      // ✅ 동적 컴포넌트 연결 다시 하기 (markRaw 적용)
      componentList.value = parsedData.map((item) => ({
        ...item,
        component: markRaw(componentRegistry[item.type]) || null,
      }));

      await nextTick(); // Vue가 변경 사항을 감지하도록 보장
    } catch (error) {
      console.error("🚨 JSON 파싱 오류:", error);
      componentList.value = [];
    }
  }
};
// ✅ 위/아래 이동 및 정렬 기능 유지
const moveUp = (index) => { if (index > 0) [componentList.value[index], componentList.value[index - 1]] = [componentList.value[index - 1], componentList.value[index]]; };
const moveDown = (index) => { if (index < componentList.value.length - 1) [componentList.value[index], componentList.value[index + 1]] = [componentList.value[index + 1], componentList.value[index]]; };
const moveToTop = (index) => { if (index > 0) componentList.value.unshift(componentList.value.splice(index, 1)[0]); };
const moveToBottom = (index) => { if (index < componentList.value.length - 1) componentList.value.push(componentList.value.splice(index, 1)[0]); };


// ✅ `onMounted()`에서 데이터 체크
onMounted(loadFromLocalStorage);

// ✅ 데이터 변경 감지 후 자동 저장
watch(componentList, (newVal) => {
  localStorage.setItem("componentList", JSON.stringify(newVal));
}, { deep: true });

// ✅ 컴포넌트 추가 기능 (markRaw 적용)
const addComponent = (type) => {
  if (!componentRegistry[type]) {
    console.error(`🚨 오류: ${type} 컴포넌트가 존재하지 않습니다.`);
    return;
  }

  componentList.value.push({
    id: Date.now(),
    type,
    component: markRaw(componentRegistry[type]), // ✅ markRaw 적용
    data: { value: "" },
  });
};
// ✅ 미리보기 버튼 (모바일에서 `nextTick()`을 이용하여 반응 개선)
const openPreview = async () => {
  localStorage.setItem("componentList", JSON.stringify(componentList.value));
  await nextTick();
  router.push("/angorani");
};

// ✅ 완료 버튼 (백엔드 저장)
const finalizeComponents = () => {
  finalUrl.value = `${window.location.origin}/invitation/${Date.now()}`;
};

// ✅ 컴포넌트 삭제 기능
const removeComponent = (id) => {
  componentList.value = componentList.value.filter((comp) => comp.id !== id);
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: auto;
  padding: 20px;
  background: #ffffff;
  text-align: center;
}

.component-box {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px;
  background: white;
  border-radius: 6px;
  transition: transform 0.2s ease-in-out;
}

.left-controls {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-right: 10px;
}

.order-number {
  font-weight: bold;
  font-size: 16px;
  color: #555;
  margin-bottom: 5px;
}

.move-buttons {
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.move-buttons button {
  background: #ddd;
  border: none;
  padding: 5px;
  font-size: 14px;
  cursor: pointer;
  border-radius: 4px;
}

.move-buttons button:hover {
  background: #bbb;
}

.delete-btn {
  background: red;
  color: white;
  border: none;
  padding: 8px;
  border-radius: 6px;
  cursor: pointer;
}

.empty-message {
  color: #777;
  font-size: 16px;
  margin-top: 20px;
}

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

/* ✅ 버튼 스타일 */
.confirm-btn, .preview-btn {
  padding: 12px 20px;
  font-size: 16px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.preview-btn {
  background: #007bff;
  color: white;
}

.preview-btn:hover {
  background: #0056b3;
}

.vmf{
  position: fixed;
  z-index: 1100;
  bottom: 80px;
}
</style>
