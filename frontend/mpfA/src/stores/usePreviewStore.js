import { defineStore } from "pinia";
import { ref, markRaw } from "vue";
import RepresentativeImage from "@/components/wedding/RepresentativeImage.vue";
import SmallImageGrid from "@/components/wedding/SmallImageGrid.vue";
import TextEditor from "@/components/wedding/TextEditor.vue";
import BankInfo from "@/components/wedding/BankInfo.vue";
import LocationMap from "@/components/wedding/LocationMap.vue";
import EventCalendar from "@/components/wedding/EventCalendar.vue";

export const usePreviewStore = defineStore("preview", () => {
    const componentList = ref([]);

    const componentRegistry = {
        RepresentativeImage: markRaw(RepresentativeImage),
        SmallImageGrid: markRaw(SmallImageGrid),
        TextEditor: markRaw(TextEditor),
        BankInfo: markRaw(BankInfo),
        LocationMap: markRaw(LocationMap),
        EventCalendar: markRaw(EventCalendar),
    };

    // ✅ 컴포넌트 추가 (markRaw 적용)
    const addComponent = (type) => {
        if (!componentRegistry[type]) {
            console.error(`🚨 오류: ${type} 컴포넌트가 존재하지 않습니다.`);
            return;
        }

        console.log(`📌 usePreviewStore에 컴포넌트 추가: ${type}`);

        componentList.value.push({
            id: Date.now(),
            type,
            component: componentRegistry[type],
            data: { value: "" },
        });
    };

    return {
        componentList,
        addComponent,
    };
});
