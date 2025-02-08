<template>
  <div class="upload-container">
    <label class="upload-box">
      <div class="image-preview">
        <img :src="modelValue || defaultImage" alt="미리보기 이미지">
      </div>

      <div class="hover-overlay">이미지 변경</div>

      <input type="file" @change="handleFileChange" accept="image/*" />
    </label>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref } from "vue";
import defaultImage from "@/assets/img/d1.png";

const props = defineProps({
  modelValue: String,
});
const emit = defineEmits(["update:modelValue"]);

const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (file && file.type.startsWith("image/")) {
    const reader = new FileReader();
    reader.onload = () => {
      emit("update:modelValue", reader.result);
    };
    reader.readAsDataURL(file);
  }
};
</script>

<style scoped>
.upload-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.upload-box {
  position: relative;
  width: 100%;
  max-width: 800px;
  cursor: pointer;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}

.upload-box input {
  display: none;
}

/* ✅ 이미지 미리보기 */
.image-preview {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.image-preview img {
  width: 100%;
  height: 100%;
  max-width: 800px; /* ✅ 작은 이미지도 확대 */
  object-fit: cover; /* ✅ 크롭하여 꽉 차게 */
  border-radius: 10px;
}

/* ✅ 마우스 호버 시 변경 안내 */
.hover-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 14px;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.upload-box:hover .hover-overlay {
  opacity: 1;
}

/* ✅ 삭제 버튼 */
.delete-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: rgba(255, 0, 0, 0.7);
  color: white;
  border: none;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  font-size: 14px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
