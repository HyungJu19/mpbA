<script setup>
import {  RouterView } from 'vue-router'
import NavbarView from "@/views/NavbarView.vue";
import {useAuthStore} from "@/stores/useAuthStore.js";
import {onMounted} from "vue";
const authStore = useAuthStore();

onMounted(() => {
  console.log("🚀 앱 실행됨: 사용자 정보 불러오기");
  authStore.fetchUser(); // ✅ 앱 실행 시 사용자 정보 자동 가져오기
})
</script>

<template>
  <header>
<NavbarView />
  </header>

  <RouterView />
</template>

<style scoped>

</style>
