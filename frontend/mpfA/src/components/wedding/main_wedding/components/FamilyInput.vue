<template>
  <div class="p-4 border rounded-lg">
    <h2 class="text-lg font-bold mb-2">신랑측 정보 입력</h2>
    <div class="mb-2">
      <label>신랑:</label>
      <input v-model="groom.name" type="text" class="input-style" />
    </div>
    <div class="mb-2">
      <label>아버지:</label>
      <input v-model="groom.father" type="text" class="input-style" />
    </div>
    <div class="mb-2">
      <label>어머니:</label>
      <input v-model="groom.mother" type="text" class="input-style" />
    </div>
    <div class="mb-2">
      <label>관계:</label>
      <select v-model="groom.relation" class="input-style">
        <option value="아들">아들</option>
        <option value="딸">딸</option>
      </select>
    </div>

    <h2 class="text-lg font-bold mt-6 mb-2">신부측 정보 입력</h2>
    <div class="mb-2">
      <label>신부:</label>
      <input v-model="bride.name" type="text" class="input-style" />
    </div>
    <div class="mb-2">
      <label>아버지:</label>
      <input v-model="bride.father" type="text" class="input-style" />
    </div>
    <div class="mb-2">
      <label>어머니:</label>
      <input v-model="bride.mother" type="text" class="input-style" />
    </div>
    <div class="mb-2">
      <label>관계:</label>
      <select v-model="bride.relation" class="input-style">
        <option value="아들">아들</option>
        <option value="딸">딸</option>
      </select>
    </div>
  </div>
</template>

<script setup>
import {useFamily} from "@/stores/useFamily";
import {storeToRefs} from "pinia";

const store = useFamily();
const {groom, bride} = storeToRefs(store);
</script>

<style scoped>
.input-style {
  width: 100%;
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
</style>
