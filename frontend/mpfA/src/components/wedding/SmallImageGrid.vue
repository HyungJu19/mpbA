<template>
  <div class="component-box">
    <h3>이미지 9개</h3>
    <div class="grid">
      <div v-for="i in 9" :key="i" class="image-box">+</div>
    </div>
  </div>
</template>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 5px;
}
.image-box {
  width: 60px;
  height: 60px;
  background: #ddd;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 5px;
}
</style>
