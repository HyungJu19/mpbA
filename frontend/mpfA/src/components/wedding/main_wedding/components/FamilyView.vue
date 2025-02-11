<template>
  <div class="p-4 border rounded-lg mt-4">


    <div v-if="groom.name" class="mb-4 ">
      <p>

        {{ groom.father }} · {{ groom.mother }} 의 {{ groom.relation }} {{groom.name}}
      </p>
    </div>

    <div v-if="bride.name">
      <p>
        {{ bride.father }} · {{ bride.mother }} 의 {{ bride.relation }}  {{bride.name}}
      </p>
    </div>

    <p v-if="!groom.father && !bride.father" class="text-gray-500">
      입력된 정보가 없습니다.
    </p>
  </div>
</template>

<script setup>
import { useFamily } from "@/stores/useFamily";
import { storeToRefs } from "pinia";

const store = useFamily();
const { groom, bride } = storeToRefs(store);
</script>
