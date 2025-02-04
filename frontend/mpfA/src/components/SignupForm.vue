<template>
  <div class="register-container">
    <div class="register-card">
      <div class="header-logo" style="padding-top: 20px">
        <RouterLink to="/home" class="logo" >Angorani</RouterLink>
      </div>



      <form @submit.prevent="handleRegister">


        <div class="form-group">
          <label for="username">아이디</label>
          <div class="input-wrapper">
            <input type="text" id="username" v-model="user.username" placeholder="아이디 입력" required />
          </div>
        </div>

        <div class="form-group">
          <label for="password">비밀번호</label>
          <div class="input-wrapper">
            <input type="password" id="password" v-model="user.password" placeholder="비밀번호 입력" required />
          </div>
        </div>


        <div class="form-group">
          <label for="email">이메일</label>
          <div class="input-wrapper">
            <input type="email" id="email" v-model="user.email" placeholder="이메일 입력" @input="validateEmail" required />
          </div>
          <p v-if="emailErrorMessage" class="error">{{ emailErrorMessage }}</p>
        </div>

        <div class="form-group">
          <label for="persName">이름</label>
          <div class="input-wrapper">
            <input type="text" id="persName" v-model="user.persName" placeholder="이름 입력" required />
          </div>
        </div>

        <!-- ✅ 생년월일 입력 -->
        <div class="form-group">
          <label for="birth">생년월일</label>
          <div class="input-wrapper">
            <input
                type="text"
                v-model="user.birth"
                placeholder="예: 19910309"
                maxlength="8"
                @input="formatBirthDate"
                @focus="editBirthDate"
                required
            />
          </div>
        </div>
        <!-- ✅ 성별 선택 -->
        <div class="form-group">
          <label>성별</label>
          <div class="gender-buttons">
            <button type="button" :class="{ active: user.gender === 'M' }" @click="user.gender = 'M'">남자</button>
            <button type="button" :class="{ active: user.gender === 'F' }" @click="user.gender = 'F'">여자</button>
          </div>
        </div>



        <!-- ✅ 통신사 선택 -->
        <div class="form-group">
          <label for="carrier">통신사</label>
          <div class="input-wrapper select-wrapper">
            <select id="carrier" v-model="user.carrier" required>
              <option value="" disabled>통신사를 선택하세요</option>
              <option value="SKT">SKT</option>
              <option value="KT">KT</option>
              <option value="LGU+">LGU+</option>
              <option value="SKT알뜰폰">SKT알뜰폰</option>
              <option value="KT알뜰폰">KT알뜰폰</option>
              <option value="LGU+알뜰폰">LGU+알뜰폰</option>
            </select>
          </div>
        </div>
        <!-- ✅ 전화번호 입력 -->
        <div class="form-group">
          <label for="phone">전화번호</label>
          <div class="input-wrapper">
            <input
                type="text"
                id="phone"
                v-model="user.phone"
                placeholder="예: 010-1234-5678"
                @input="formatPhoneNumber"
                maxlength="13"
                required
            />
          </div>
        </div>



        <TermsAgreement v-model:agreeAll="agreeAll" v-model:terms="terms" />

        <button type="submit" class="btn primary-btn">회원가입</button>
        <RouterLink to="/login" class="btn secondary-btn">로그인 페이지로 이동</RouterLink>
        <RouterLink to="/" class="btn secondary-btn">⬅ 홈으로 돌아가기</RouterLink>
      </form>

      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      <p v-if="successMessage" class="success">{{ successMessage }}</p>
    </div>
  </div>
</template>
<script setup>
import { ref, computed } from "vue";
import { useAuthStore } from "@/stores/useAuthStore.js";
import { useRouter } from "vue-router";
import TermsAgreement from "@/components/TermsAgreement.vue";

const router = useRouter();
const authStore = useAuthStore();
const emailErrorMessage = ref("");
const errorMessage = ref("");
const successMessage = ref("");

const agreeAll = ref(false);
const terms = ref([
  { label: "네이버 이용약관", text: "네이버 서비스 이용약관...", checked: false, required: true },
  { label: "개인정보 수집 및 이용", text: "회원가입 시 개인정보 수집 관련 내용...", checked: false, required: true },
  { label: "위치기반서비스 이용", text: "위치기반 서비스 이용약관...", checked: false, required: false },
  { label: "이벤트 및 혜택 정보 수신", text: "이벤트 및 혜택 정보 수신 동의...", checked: false, required: false },
]);

const user = ref({
  username: "", // ✅ 기존 id → username 변경
  persName: "",
  email: "",
  phone: "",
  carrier: "",
  password: "",
  birth: "",
  gender: "",
  authType: "LOCAL"
});

// ✅ 약관 동의 상태를 "1111" 형식으로 변환
const agreementStatus = computed(() => terms.value.map(term => (term.checked ? "1" : "0")).join(""));

const formatPhoneNumber = () => {
  user.value.phone = user.value.phone.replace(/\D/g, "").slice(0, 11);
};

const formatBirthDate = () => {
  user.value.birth = user.value.birth.replace(/\D/g, "").slice(0, 8);
};

// ✅ 회원가입 요청
const handleRegister = async () => {
  if (!user.value.username) {
    errorMessage.value = "아이디(사용자명)를 입력해주세요.";
    return;
  }
  if (!user.value.birth || user.value.birth.length !== 8) {
    errorMessage.value = "생년월일을 8자리 (YYYYMMDD) 형식으로 입력해주세요.";
    return;
  }
  if (!user.value.phone) {
    errorMessage.value = "전화번호를 입력해주세요.";
    return;
  }
  if (!user.value.carrier) {
    errorMessage.value = "통신사를 선택해주세요.";
    return;
  }
  if (!user.value.gender) {
    errorMessage.value = "성별을 입력해주세요.";
    return;
  }

  const requestData = {
    ...user.value,
    agreement: agreementStatus.value
  };

  const result = await authStore.register(requestData);
  if (result.success) {
    successMessage.value = "회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.";
    setTimeout(() => router.push("/login"), 1500);
  } else {
    errorMessage.value = result.message;
  }
};
</script>




<style scoped>
/* ✅ 전체 화면에서 가로 & 세로 스크롤 방지 */
html, body {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  overflow-x: hidden;
}

/* ✅ 전체 레이아웃 */
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  background: linear-gradient(to right, #eceff1, #ffffff);
  padding: 10px;
  box-sizing: border-box;
}

/* ✅ 회원가입 카드 */
.register-card {
  background: white;
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.15);
  width: 100%;
  max-width: 400px;
  max-height: 85vh;
  overflow-y: auto;
  text-align: center;
  box-sizing: border-box;
  animation: fadeIn 0.8s ease-out;
}

/* 🎨 타이틀 */
.register-title {
  font-size: 26px;
  font-weight: bold;
  color: #ff5e57;
  margin-bottom: 20px;
}

/* ✅ 폼 그룹 */
.form-group {
  text-align: left;
  margin-bottom: 15px;
}

/* ✅ 입력 필드 */
.input-wrapper {
  background: rgba(0, 0, 0, 0.05);
  border-radius: 8px;
  padding: 12px;
  display: flex;
  align-items: center;
}

.input-wrapper input {
  width: 100%;
  background: none;
  border: none;
  font-size: 16px;
  color: #333;
  outline: none;
  padding: 5px;
}

/* ✅ 공통 버튼 스타일 */
.btn {
  font-size: 16px;
  font-weight: bold;
  padding: 12px;
  width: 100%;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-top: 10px;
  transition: all 0.3s ease-in-out;
  text-decoration: none;
  box-sizing: border-box;
}

/* ✅ 로그인 & 회원가입 버튼 */
.primary-btn {
  background: #ff5e57;
  color: white;
}


.header-logo{
  margin-bottom: 50px;
}
/* ✅ 로고 스타일 */
.header-logo .logo {
  font-size: 1.8rem;
  font-weight: bold;
  text-decoration: none;
  color: black;
}

.primary-btn:hover {
  background: #e04e47;
  transform: translateY(-2px);
  box-shadow: 0px 5px 15px rgba(255, 94, 87, 0.3);
}

/* ✅ 회원가입 & 홈으로 돌아가기 버튼 (회색) */
.secondary-btn {
  background: #f5f5f5;
  color: #333;
  text-align: center;
}

.secondary-btn:hover {
  background: #ddd;
}

/* ✅ 입력 필드 스타일 */
.input-wrapper {
  display: flex;
  align-items: center;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  background: #fff;
  position: relative;
}

.input-wrapper input {
  width: 100%;
  border: none;
  outline: none;
  font-size: 16px;
  text-align: center;
}

/* ✅ 통신사 선택 드롭다운 스타일 */
.select-wrapper {
  position: relative;
}

.select-wrapper select {
  width: 100%;
  border: none;
  font-size: 16px;
  padding: 8px;
  appearance: none;
  background: transparent;
  cursor: pointer;
  outline: none;
}

/* ✅ 드롭다운 선택 시 스타일 */
.select-wrapper select:focus {
  border: 2px solid #ff5e57;
  background: #fff5f5;
  border-radius: 5px;
}

/* ✅ 옵션 스타일 */
.select-wrapper select option {
  font-size: 16px;
  padding: 10px;
  background: white;
  color: #333;
}

/* ✅ 드롭다운 화살표 추가 */
.select-wrapper::after {
  content: "▼";
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 14px;
  color: #888;
  pointer-events: none;
}

/* ✅ 성별 선택 버튼 */
.gender-buttons {
  display: flex;
  gap: 10px;
}

.gender-buttons button {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background: #f5f5f5;
  cursor: pointer;
  font-size: 16px;
}

.gender-buttons button.active {
  background: #ff5e57;
  color: white;
  border-color: #ff5e57;
}

.gender-buttons button:hover {
  background: #ff7875;
  color: white;
  border-color: #ff7875;
}

/* 🚨 에러 메시지 */
.error {
  color: red;
  font-size: 14px;
  margin-top: 10px;
}

/* 🔥 애니메이션 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(15px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* ✅ 반응형 */
@media (max-width: 768px) {
  .register-card {
    padding: 20px;
    max-width: 99vw;
    max-height: 800px;
  }
}
</style>
