import { defineStore } from "pinia";
import { ref } from "vue";

export const usePreviewStore = defineStore("preview", () => {
    const componentList = ref([]); // ✅ 청첩장 데이터 유지 (올바르게 정의)
    return { componentList };
});
