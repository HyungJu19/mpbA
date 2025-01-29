import { defineStore } from "pinia";
import axios from "axios";

export const useAuthStore = defineStore("auth", {
    state: () => ({
        user: JSON.parse(localStorage.getItem("user")) || null, // ✅ 유저 정보 유지
        accessToken: localStorage.getItem("accessToken") || null, // ✅ Access Token 유지

    }),

    actions: {
        // ✅ 로그인 (JWT 저장 + 사용자 정보 저장)
        async login(id, password) {
            try {
                const response = await axios.post("/api/user/login", { id, password });



                // ✅ Access Token & Refresh Token 저장
                this.accessToken = response.data.accessToken;


                // ✅ LocalStorage에 저장
                localStorage.setItem("accessToken", this.accessToken);




                // ✅ 로그인한 사용자 정보 가져오기
                await this.fetchUser();
                return true;
            } catch (error) {
                console.error("❌ 로그인 오류:", error);
                return false;
            }
        },

        // ✅ 로그아웃 (토큰 제거 + 사용자 정보 삭제)
        logout() {
            console.log("🚨 로그아웃 실행됨!"); // ❗ 로그아웃이 언제 실행되는지 확인
            this.user = null;
            this.accessToken = null;
            this.refreshToken = null;
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            localStorage.removeItem("user");
        }
        ,

        // ✅ 현재 로그인한 사용자 정보 가져오기 (토큰 기반)

        async fetchUser() {
            if (!this.accessToken) {
                console.log("❌ Access Token이 없습니다.");
                return;
            }

            try {

                const response = await axios.get("/api/user/me", {
                    headers: { Authorization: `Bearer ${this.accessToken}` },
                });



                this.user = response.data; // ✅ 사용자 정보 업데이트

            } catch (error) {
                console.error("❌ 사용자 정보 가져오기 오류:", error);

                // ✅ Access Token이 만료되었을 경우 Refresh Token 사용하여 갱신 시도
                if (error.response && error.response.status === 401) {
                    console.log("🔄 Access Token 만료됨. Refresh Token을 사용하여 재발급 시도...");
                    const success = await this.refreshAccessToken();

                    if (success) {
                        console.log("✅ 새 Access Token으로 fetchUser() 다시 실행");
                        return this.fetchUser();
                    } else {
                        console.log("❌ Refresh Token도 만료됨. 로그아웃 실행 방지!");
                        return; // ❗ `logout()` 실행하지 않음
                    }
                }
            }
        }

        ,

        // ✅ 회원가입 (백엔드 메시지 반환)
        async register(user) {
            try {
                const response = await axios.post("/api/user/register", user);
                return { success: true, message: response.data };
            } catch (error) {
                if (error.response && error.response.data) {
                    return { success: false, message: error.response.data };
                }
                return { success: false, message: "회원가입 중 알 수 없는 오류 발생" };
            }
        },
    },
});

// ✅ Axios 기본 설정: 모든 요청에 JWT 자동 포함 (Access Token)
axios.interceptors.request.use((config) => {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
        config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
});
