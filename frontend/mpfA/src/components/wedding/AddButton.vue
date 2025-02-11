<template>
  <div>
    <!-- ✅ 추가 버튼 -->
    <button class="add-btn" @click="toggleMenu">➕</button>

    <!-- ✅ 추가 가능한 리스트 -->
    <transition name="fade-slide">
      <div class="button-group" v-if="showMenu">
        <button
            v-for="(comp, index) in availableComponents"
            :key="index"
            @click="handleAdd(comp.type)"
        >
          {{ comp.label }}
        </button>
      </div>
    </transition>
  </div>
</template>
<!-- ✅ AddButton.vue -->
<script setup>
import { ref, defineEmits } from "vue";

const emit = defineEmits(["add-component"]);

const showMenu = ref(false);
const availableComponents = [
  { type: "RepresentativeImage", label: "대표 이미지" },
  { type: "SmallImageGrid", label: "9개 작은 이미지" },
  { type: "TextEditor", label: "글쓰기" },
  { type: "BankInfo", label: "전하실 곳" },
  { type: "LocationMap", label: "오시는 곳" },
  { type: "EventCalendar", label: "달력" },
];

// ✅ 추가 버튼 토글
const toggleMenu = () => {
  showMenu.value = !showMenu.value;
};

// ✅ 컴포넌트 추가 이벤트 발생 (콘솔로 확인)
const handleAdd = (type) => {
  console.log(`📌 추가 이벤트 발생: ${type}`);
  emit("add-component", type);
  showMenu.value = false;
};
</script>

<style scoped>
/* ✅ 추가 버튼 스타일 */
.add-btn {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: #007bff;
  color: white;
  font-size: 24px;
  cursor: pointer;
}

/* ✅ 버튼 그룹 스타일 */
.button-group {
  position: fixed;
  bottom: 90px;
  right: 20px;
  background: white;
  padding: 12px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 180px;
}

.button-group button {
  padding: 10px;
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
