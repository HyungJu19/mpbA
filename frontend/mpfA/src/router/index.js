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
import Preview from "@/components/wedding/Preview.vue";
import LoginRequiredView from "@/views/page/LoginRequiredView.vue";
import ProductDetail from "@/components/ProductDetail.vue";
import ProductList from "@/components/ProductList.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: "/", component: HomeView },
    { path: "/login", component: LoginView },
    { path: "/register", component: SignupView },
    { path: "/home", component: HomeView },
    { path: "/wedding-invitation", component: WeddingInvitationView }, // ✅ 모바일 청첩장
    { path: "/cafe24-skins", component: Cafe24SkinView }, // ✅ 카페24 스킨
    {
      path: "/contact",
      component: ContactView,
      meta: { requiresAuth: true }  // ✅ 로그인 필요
    },
    { path: "/community", component: CommunityView }, // ✅ 자유게시판
    { path: "/login-required", component: LoginRequiredView }, // ✅ 로그인 필요 페이지
    { path: "/preview", name: "Preview", component: Preview },
    { path: "/:pathMatch(.*)*", component: NotFoundView }, // ✅ 404 페이지
    { path: "/products", component: ProductList },
    { path: "/product/:id", component: ProductDetail, props: true }, // ✅ 상세 페이지 추가
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
