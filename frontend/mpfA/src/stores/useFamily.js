// src/stores/useFamily.js
import { defineStore } from "pinia";

export const useFamily = defineStore("family", {
    state: () => ({
        groom: { name: "", father: "", mother: "", relation: "아들" },
        bride: { name: "", father: "", mother: "", relation: "딸" },
    }),
    actions: {
        setGroom(field, value) {
            this.groom[field] = value;
        },
        setBride(field, value) {
            this.bride[field] = value;
        },
    },
});
