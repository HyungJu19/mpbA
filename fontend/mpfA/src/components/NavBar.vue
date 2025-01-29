<template>
  <nav class="navbar">
    <div class="nav-left">
      <RouterLink to="/home" class="logo">Angorani</RouterLink>
      <RouterLink to="/wedding-invitation">모바일청첩장</RouterLink>
      <RouterLink to="/cafe24-skins">카페24스킨</RouterLink>
      <RouterLink to="/contact">1:1문의</RouterLink>
      <RouterLink to="/community">자유게시판</RouterLink>
      <RouterLink to="/category4">404</RouterLink>
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
  color: #333;
}

.nav-right {
  display: flex;
  align-items: center;
}
</style>
