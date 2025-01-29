<template>
  <nav class="navbar">
    <div class="nav-left">
      <RouterLink to="/" class="logo">MyApp</RouterLink>
      <RouterLink to="/category1">카테고리1</RouterLink>
      <RouterLink to="/category2">카테고리2</RouterLink>
      <RouterLink to="/category3">카테고리3</RouterLink>
      <RouterLink to="/category4">카테고리4</RouterLink>
    </div>

    <div class="nav-right">
      <AuthButtons />
    </div>
  </nav>
</template>

<script setup>
import { RouterLink } from "vue-router";
import AuthButtons from "@/components/AuthButtons.vue"; // ✅ 로그인/로그아웃 버튼 컴포넌트
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 20px;
  background-color: #333;
  color: white;
}

.nav-left a {
  color: white;
  text-decoration: none;
  margin-right: 15px;
  font-weight: bold;
}

.nav-left a:hover {
  color: #ff9800;
}

.nav-right {
  display: flex;
  align-items: center;
}
</style>
