
<template>
  <div class="terms-container">
    <!-- ✅ 전체 동의 -->
    <div class="terms-all">
      <label>
        <input type="checkbox" :checked="agreeAll" @change="toggleAll" />
        <span class="checkbox-custom"></span> 전체 동의하기
      </label>
    </div>

    <!-- ✅ 개별 동의 리스트 -->
    <div class="terms-list">
      <div v-for="(term, index) in terms" :key="index" class="terms-item">
        <label>
          <input
              type="checkbox"
              :checked="term.checked"
              @change="() => emit('update:terms', terms.map((t, i) => i === index ? { ...t, checked: !t.checked } : t))"
          />
          <span class="checkbox-custom"></span> {{ term.label }}
        </label>
      </div>
    </div>
  </div>
</template>
<script setup>
import { defineProps, defineEmits, computed, watch } from "vue";

// ✅ 부모로부터 props 받기
const props = defineProps({
  agreeAll: Boolean,
  terms: Array,
});

// ✅ 부모에게 변경된 값 emit
const emit = defineEmits(["update:agreeAll", "update:terms"]);

// ✅ 전체 동의 기능
const toggleAll = () => {
  const newState = !props.agreeAll;
  emit("update:agreeAll", newState);
  emit(
      "update:terms",
      props.terms.map((term) => ({ ...term, checked: newState }))
  );
};

// ✅ 개별 체크 변경 시 전체 동의 상태 업데이트
const updateAgreement = () => {
  const allChecked = props.terms.every((term) => term.checked);
  emit("update:agreeAll", allChecked);
};

// ✅ 개별 체크박스 변경 감지
watch(
    () => props.terms,
    updateAgreement,
    { deep: true }
);
</script>


<style scoped>
/* ✅ 약관 전체 컨테이너 */
.terms-container {
  margin-top: 20px;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 12px;
  background: #f9f9f9;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

/* ✅ 전체 동의 */
.terms-all {
  display: flex;
  flex-direction: column;
  font-weight: bold;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ddd;
}

.terms-all label {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.terms-desc {
  font-size: 13px;
  color: #666;
  margin-top: 5px;
}

/* ✅ 개별 약관 */
.terms-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.terms-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
}

.terms-item input,
.terms-all input {
  display: none;
}

.terms-item label,
.terms-all label {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  font-size: 14px;
}

.required {
  color: red;
  font-size: 12px;
  margin-left: 5px;
}

/* ✅ 체크박스 스타일 */
.checkbox-custom {
  width: 18px;
  height: 18px;
  border-radius: 5px;
  border: 2px solid #ff5e57;
  display: inline-block;
  position: relative;
}

.terms-item input:checked + .checkbox-custom,
.terms-all input:checked + .checkbox-custom {
  background: #ff5e57;
  border: none;
  position: relative;
}

.terms-item input:checked + .checkbox-custom::after,
.terms-all input:checked + .checkbox-custom::after {
  content: "✔";
  color: white;
  font-size: 14px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -55%);
}

/* ✅ 내용 보기 버튼 */
.view-btn {
  background: transparent;
  border: none;
  color: #007bff;
  font-size: 13px;
  cursor: pointer;
  transition: color 0.3s ease-in-out;
}

.view-btn:hover {
  color: #0056b3;
  text-decoration: underline;
}

/* ✅ 백엔드로 보낼 값 미리 확인 */
.agreement-output {
  margin-top: 10px;
  font-size: 14px;
  color: #333;
  text-align: center;
}

/* ✅ 모달 스타일 */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 12px;
  width: 350px;
  text-align: center;
  animation: fadeIn 0.3s ease-in-out;
}

.modal-body {
  max-height: 200px;
  overflow-y: auto;
  padding: 10px;
  text-align: left;
  font-size: 14px;
  line-height: 1.6;
}

.close-btn {
  margin-top: 10px;
  padding: 8px;
  background: #ff5e57;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}

.close-btn:hover {
  background: #e04e47;
}

/* ✅ 애니메이션 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
