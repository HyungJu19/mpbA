import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "@/stores/useAuthStore.js";
import LoginView from "@/views/user/LoginView.vue";
import SignupView from "@/views/user/SignupView.vue";
import HomeView from "@/views/page/HomeView.vue";
import WeddingInvitationView from "@/views/page/WeddingInvitationView.vue";
import Cafe24SkinView from "@/views/page/Cafe24SkinView.vue";
import ContactView from "@/views/page/ContactView.vue";
import CommunityView from "@/views/page/CommunityView.vue";
import NotFoundView from "@/views/NotFoundView.vue";
import LoginRequiredView from "@/views/page/LoginRequiredView.vue";
import ProductDetail from "@/components/ProductDetail.vue";
import ProductList from "@/components/ProductList.vue";
import Angorani from "@/components/wedding/Angorani.vue";
import WeddingView from "@/views/mainPage/WeddingView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: "/", component: HomeView }, // ✅ 홈
    { path: "/login", component: LoginView }, // ✅ 로그인
    { path: "/register", component: SignupView }, // ✅ 회원가입
    { path: "/home", component: HomeView }, // ✅ 홈 (중복 허용)
    { path: "/wedding-invitation", component: WeddingInvitationView }, // ✅ 모바일 청첩장

    // ✅ 청첩장 (미리보기 & 저장된 초대장)
    { path: "/angorani", component: Angorani, props: true }, // ✅ 미리보기
    { path: "/angorani/:id", component: Angorani, props: true }, // ✅ 저장된 청첩장 보기

    { path: "/cafe24-skins", component: Cafe24SkinView }, // ✅ 카페24 스킨
    { path: "/community", component: CommunityView }, // ✅ 자유게시판
    { path: "/products", component: ProductList }, // ✅ 제품 목록
    { path: "/product/:id", component: ProductDetail, props: true }, // ✅ 제품 상세
    { path: "/weddingView", component: WeddingView},
    {
      path: "/contact",
      component: ContactView,
      meta: { requiresAuth: true }  // ✅ 로그인 필요
    },
    { path: "/login-required", component: LoginRequiredView }, // ✅ 로그인 필요 페이지

    { path: "/:pathMatch(.*)*", component: NotFoundView }, // ✅ 404 페이지
  ],
});

// ✅ 네비게이션 가드 추가 (로그인 체크)
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  if (to.meta.requiresAuth && !authStore.user) {
    next({ path: "/login-required", query: { redirect: to.fullPath } });
  } else {
    next();
  }
});

export default router;
