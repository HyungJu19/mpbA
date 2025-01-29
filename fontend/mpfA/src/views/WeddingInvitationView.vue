<template>
  <div class="toggle-container">
    <h2 class="section-title">설정</h2>
    <div v-for="(option, index) in toggleOptions" :key="index" class="toggle-item">
      <div class="toggle-text">
        <h3>{{ option.label }}</h3>
        <p>{{ option.description }}</p>
      </div>
      <label class="switch">
        <input type="checkbox" v-model="option.value">
        <span class="slider"></span>
      </label>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";

// ✅ 샘플 사이트와 동일한 토글 항목 구성
const toggleOptions = reactive([
  { label: "초대장 공개", description: "초대장을 모든 사용자에게 공개합니다.", value: true },
  { label: "방명록 활성화", description: "방명록을 사용자가 작성할 수 있도록 허용합니다.", value: true },
  { label: "연락처 공유", description: "방명록 작성 시 연락처를 함께 표시합니다.", value: false },
  { label: "푸시 알림", description: "새로운 댓글이나 소식을 알림으로 받습니다.", value: false }
]);
</script>

<style scoped>
/* ✅ 전체 컨테이너 스타일 */
.toggle-container {
  width: 90%;
  max-width: 450px;
  margin: 20px auto;
  padding: 15px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  border: 1px solid #ddd;
}

/* ✅ 섹션 타이틀 (샘플 사이트와 유사한 스타일) */
.section-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 12px;
  padding-left: 5px;
}

/* ✅ 개별 토글 항목 스타일 */
.toggle-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 12px;
  background: #fff;
  border-radius: 10px;
  border-bottom: 1px solid #eee;
  transition: background 0.3s ease;
}

.toggle-item:last-child {
  border-bottom: none;
}

.toggle-item:hover {
  background: #fafafa;
}

/* ✅ 토글 항목 텍스트 스타일 */
.toggle-text {
  flex-grow: 1;
}

.toggle-text h3 {
  margin: 0;
  font-size: 16px;
  font-weight: bold;
  color: #222;
}

.toggle-text p {
  margin: 4px 0 0;
  font-size: 14px;
  color: #666;
}

/* ✅ 아이폰 스타일 토글 버튼 */
.switch {
  position: relative;
  display: inline-block;
  width: 48px;
  height: 28px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

/* ✅ 토글 슬라이더 (아이폰 스타일) */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ddd;
  transition: 0.4s;
  border-radius: 34px;
}

/* ✅ 토글 버튼 원 */
.slider:before {
  position: absolute;
  content: "";
  height: 22px;
  width: 22px;
  left: 3px;
  bottom: 3px;
  background-color: white;
  transition: 0.4s;
  border-radius: 50%;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

/* ✅ 체크 상태 (켜진 상태) */
input:checked + .slider {
  background-color: #34c759; /* 아이폰 스타일 */
}

input:checked + .slider:before {
  transform: translateX(20px);
}
</style>
