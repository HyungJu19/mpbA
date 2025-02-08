<template>
  <textarea v-model="text" @input="updateValue"></textarea>
</template>

<script setup>
import { ref, defineProps, defineEmits, watch } from "vue";

const props = defineProps({
  modelValue: String,
});

const emit = defineEmits(["update:modelValue"]);
const text = ref(props.modelValue);

watch(() => props.modelValue, (newVal) => {
  text.value = newVal;
});

const updateValue = () => {
  emit("update:modelValue", text.value);
};
</script>

<style scoped>
textarea {
  width: 100%;
  height: 100px;
  padding: 8px;

}
</style>
