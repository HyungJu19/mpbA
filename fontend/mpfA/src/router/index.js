import { createRouter, createWebHistory } from 'vue-router'
import LoginView from "@/views/user/LoginView.vue";
import SignupView from "@/views/user/SignupView.vue";
import HomeView from "@/views/HomeView.vue";
import WeddingInvitationView from "@/views/WeddingInvitationView.vue";
import Cafe24SkinView from "@/views/Cafe24SkinView.vue";
import ContactView from "@/views/ContactView.vue";
import CommunityView from "@/views/CommunityView.vue";
import NotFoundView from "@/views/NotFoundView.vue";
import Preview from "@/views/Preview.vue";



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [

    { path: "/", component: LoginView },
    { path: "/login", component: LoginView },
    { path: "/register", component: SignupView},
    { path: "/home", component: HomeView},
    { path: "/wedding-invitation", component: WeddingInvitationView }, // ✅ 모바일 청첩장
    { path: "/cafe24-skins", component: Cafe24SkinView }, // ✅ 카페24 스킨
    { path: "/contact", component: ContactView}, // ✅ 1:1 문의
    { path: "/community", component: CommunityView }, // ✅ 자유게시판
    { path: "/:pathMatch(.*)*", component: NotFoundView}, // ✅ 404 페이지
    {
      path: "/preview",
      name: "Preview",
      component: Preview,
    },
  ],
})

export default router
