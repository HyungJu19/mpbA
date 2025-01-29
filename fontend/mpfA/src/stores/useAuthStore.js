import { defineStore } from "pinia";
import axios from "axios";

export const useAuthStore = defineStore("auth", {
    state: () => ({
        user: JSON.parse(localStorage.getItem("user")) || null, // ✅ 유저 정보 로컬스토리지 유지
        token: localStorage.getItem("token") || null, // ✅ 기존 토큰 유지
    }),

    actions: {
        // ✅ 로그인 (JWT 저장 + 사용자 정보 저장)
        async login(id, password) {
            try {
                const response = await axios.post("/api/user/login", { id, password });
                console.log("✅ 백엔드 응답 데이터:", response.data); // 🔍 응답 데이터 구조 확인
                this.token = response.data; // ✅ JWT 토큰 저장
                localStorage.setItem("token", this.token);
                console.log("✅ 백엔드 응답 데이터:", this.token); // 🔍 응답 데이터 구조 확인

                // ✅ 로그인한 사용자 정보 가져오기
                await this.fetchUser();
                return true;
            } catch (error) {
                return false;
            }
        },

        // ✅ 로그아웃 (토큰 제거 + 사용자 정보 삭제)
        logout() {
            this.user = null;
            this.token = null;
            localStorage.removeItem("token");
            localStorage.removeItem("user");
        },

        // ✅ 현재 로그인한 사용자 정보 가져오기 (토큰 기반)
        async fetchUser() {
            if (!this.token) return;

            try {
                const response = await axios.get("/api/user/me", {
                    headers: { Authorization: `Bearer ${this.token}` },
                });
                this.user = response.data;
                localStorage.setItem("user", JSON.stringify(this.user)); // ✅ 유저 정보 저장
            } catch (error) {
                this.logout(); // 인증 실패 시 자동 로그아웃
            }
        },

        // ✅ 회원가입 (백엔드 메시지 반환)
        async register(user) {
            try {
                const response = await axios.post("/api/user/register", user);
                return { success: true, message: response.data };
            } catch (error) {
                if (error.response && error.response.data) {
                    return { success: false, message: error.response.data }; // ❗ 백엔드에서 보낸 메시지 받기
                }
                return { success: false, message: "회원가입 중 알 수 없는 오류 발생" };
            }
        },
    },
});

// ✅ Axios 기본 설정: 모든 요청에 JWT 자동 포함
axios.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
