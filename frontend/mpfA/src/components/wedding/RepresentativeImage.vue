<template>
  <div class="upload-container">
    <label class="upload-box">
      <div class="image-preview">
        <img :src="imageSrc" alt="미리보기 이미지">
      </div>

      <div class="hover-overlay">이미지 변경</div>

      <input type="file" @change="handleFileChange" accept="image/*" />
    </label>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref, watch } from "vue";
import defaultImage from "@/assets/img/d1.png";

const props = defineProps({
  modelValue: String, // 부모에서 전달한 값
});
const emit = defineEmits(["update:modelValue"]);
const imageSrc = ref(props.modelValue || defaultImage);

// ✅ 파일 선택 시 Blob URL 생성하여 사용
const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (!file) return;

  if (file.type.startsWith("image/")) {
    const blobUrl = URL.createObjectURL(file);
    imageSrc.value = blobUrl;
    emit("update:modelValue", blobUrl);
  }
};

// ✅ `modelValue`가 변경될 때마다 `imageSrc` 업데이트
watch(() => props.modelValue, (newVal) => {
  imageSrc.value = newVal || defaultImage;
});
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
  height: auto;
  max-width: 800px;
  object-fit: contain;
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
</style>
