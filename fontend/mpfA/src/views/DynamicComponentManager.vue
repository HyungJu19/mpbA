<template>
  <div class="container">
    <h2>📌 청첩장 구성</h2>

    <!-- ✅ 드래그 가능 리스트 -->
    <draggable
        v-model="componentList"
        group="components"
        class="drag-container"
        handle=".drag-handle"
        item-key="id"
        animation="200"
    >
      <template #item="{ element }">
        <div class="component-box">
          <div class="drag-handle">☰</div>

          <!-- ✅ 동적 컴포넌트 렌더링 -->
          <component
              v-if="element.type"
              :is="getComponent(element.type)"
              v-bind="element.data"
              @update-data="updateComponentData(element.id, $event)"
          ></component>

          <button class="delete-btn" @click="removeComponent(element.id)">🗑</button>
        </div>
      </template>
    </draggable>

    <!-- ✅ 최종 확인 및 미리보기 버튼 -->
    <div class="bottom-buttons">
      <button class="confirm-btn" @click="finalizeComponents">✔ 최종 확인</button>
      <button class="preview-btn" @click="openPreview">📱 미리보기</button>
    </div>

    <!-- ✅ 추가 버튼 -->
    <button class="add-btn" @click="toggleMenu">➕</button>
    <div class="button-group" v-if="showMenu">
      <button
          v-for="(comp, index) in availableComponents"
          :key="index"
          @click="addComponent(comp.type)"
      >
        {{ comp.label }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import draggable from "vuedraggable";
import { useRouter } from "vue-router"; // ✅ 페이지 이동을 위한 Vue Router 사용

// ✅ 개별 컴포넌트 등록
import RepresentativeImage from "@/views/RepresentativeImage.vue";
import SmallImageGrid from "@/views/SmallImageGrid.vue";
import TextEditor from "@/views/TextEditor.vue";
import BankInfo from "@/views/BankInfo.vue";
import LocationMap from "@/views/LocationMap.vue";
import EventCalendar from "@/views/EventCalendar.vue";

// ✅ Vue Router 사용
const router = useRouter();

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

// ✅ 기본 컴포넌트 리스트
const componentList = ref([]);

// ✅ 추가 가능한 컴포넌트 목록
const availableComponents = ref([
  {type: "RepresentativeImage", label: "대표 이미지"},
  {type: "SmallImageGrid", label: "9개 작은 이미지"},
  {type: "TextEditor", label: "글쓰기"},
  {type: "BankInfo", label: "전하실 곳"},
  {type: "LocationMap", label: "오시는 곳"},
  {type: "EventCalendar", label: "달력"}
]);

// ✅ 추가 버튼 토글
const showMenu = ref(false);
const toggleMenu = () => {
  showMenu.value = !showMenu.value;
};

// ✅ 컴포넌트 추가
const addComponent = (type) => {
  componentList.value.push({
    id: Date.now(),
    type,
    data: {} // 초기 데이터 추가 가능
  });
  showMenu.value = false; // 메뉴 닫기
};

// ✅ 컴포넌트 삭제
const removeComponent = (id) => {
  componentList.value = componentList.value.filter((comp) => comp.id !== id);
};

// ✅ 컴포넌트 데이터 업데이트
const updateComponentData = (id, newData) => {
  const component = componentList.value.find((comp) => comp.id === id);
  if (component) {
    component.data = {...component.data, ...newData};
  }
};

// ✅ 최종 확인 버튼 (콘솔에 JSON 데이터 출력)
const finalizeComponents = () => {
  console.log("최종 데이터:", JSON.stringify(componentList.value, null, 2));
  alert("구성이 완료되었습니다!");
};

// ✅ 미리보기 버튼 (새로운 페이지로 이동)
const openPreview = () => {
  router.push({
    path: "/preview",
    query: { data: JSON.stringify(componentList.value) }
  });
};
</script>

<style scoped>
/* ✅ 전체 컨테이너 */
.container {
  max-width: 800px;
  margin: auto;
  padding: 20px;
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

/* ✅ 드래그 가능한 영역 */
.drag-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
  min-height: 100px;
  border: 2px dashed #d1d1d1;
  border-radius: 6px;
}

/* ✅ 개별 컴포넌트 */
.component-box {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px;
  background: white;
  border-radius: 6px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease-in-out;
}

.component-box:hover {
  transform: scale(1.02);
}

/* ✅ 하단 버튼 그룹 */
.bottom-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.confirm-btn, .preview-btn {
  flex: 1;
  margin: 5px;
  padding: 12px;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.2s;
}

.confirm-btn {
  background: #28a745;
  color: white;
}

.confirm-btn:hover {
  background: #218838;
}

.preview-btn {
  background: #007bff;
  color: white;
}

.preview-btn:hover {
  background: #0056b3;
}

/* ✅ 아이폰 스타일 추가 버튼 */
/* ✅ 추가 버튼 스타일 */
.add-btn {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: linear-gradient(135deg, #007bff, #0056b3);
  color: white;
  border: none;
  font-size: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  cursor: pointer;
  transition: transform 0.2s ease-in-out;
}

.add-btn:hover {
  transform: scale(1.1);
}

/* ✅ 추가 버튼 목록 위치 고정 */
.button-group {
  position: fixed;
  bottom: 90px;
  right: 20px;
  background: rgba(255, 255, 255, 0.95);
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.button-group button {
  padding: 10px 15px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.2s;
  font-size: 14px;
}

.button-group button:hover {
  background: #0056b3;
}

</style>
