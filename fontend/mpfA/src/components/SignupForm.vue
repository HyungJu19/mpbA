<template>
  <div class="register-container">
    <h2>회원가입</h2>
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label for="id">아이디</label>
        <input type="text" id="id" v-model="user.id" required />
      </div>
      <div class="form-group">
        <label for="password">비밀번호</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-group">
        <label for="persName">이름</label>
        <input type="text" id="persName" v-model="user.persName" required />
      </div>
      <div class="form-group">
        <label for="email">이메일</label>
        <input type="email" id="email" v-model="user.email" @input="validateEmail" required />
        <p v-if="emailErrorMessage" class="error">{{ emailErrorMessage }}</p>
      </div>

      <div class="form-group">
        <label for="birth">생년월일 7자리 (xxxxxx-x 또는 xxxxxxx)</label>
        <input type="text" id="birth" v-model="inputBirth" @input="updateBirthFormat" maxlength="8" />
      </div>

      <div class="form-group">
        <label for="age">나이</label>
        <span>{{ user.age || "입력 없음" }}</span>
      </div>

      <div class="form-group">
        <label for="gender">성별</label>
        <span>{{ user.gender === "M" ? "남성" : user.gender === "F" ? "여성" : "입력 없음" }}</span>
      </div>

      <button type="submit">회원가입</button>
    </form>

    <!-- ❗ 백엔드에서 보내준 메시지 표시 -->
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    <p v-if="successMessage" class="success">{{ successMessage }}</p>

    <button @click="goToLogin">로그인 페이지로 이동</button>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {useAuthStore} from "@/stores/useAuthStore.js";
import {useRouter} from "vue-router";

const router = useRouter();
const authStore = useAuthStore();
const inputBirth = ref(""); // 사용자가 입력하는 값 (7자리)
const emailErrorMessage = ref(""); // 이메일 에러 메시지
const user = ref({
  id: "",
  persName: "",
  email: "",
  password: "",
  birth: "",
  authType: "LOCAL",
});
const errorMessage = ref("");
const successMessage = ref("");



// ✅ 이메일 정규표현식 검증
const validateEmail = () => {
  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  if (!emailPattern.test(user.value.email)) {
    emailErrorMessage.value = "유효한 이메일 주소를 입력하세요.";
  } else {
    emailErrorMessage.value = "";
  }
};

// ✅ 생년월일 입력 시 자동 변환 (YYYYMMDD-G)
const updateBirthFormat = () => {
  let birth = inputBirth.value.replace(/-/g, ""); // '-' 제거
  if (birth.length === 7) {
    const birthYear = parseInt(birth.substring(0, 2)); // 앞 2자리 (년도)
    const birthDate = birth.substring(2, 6); // MMDD (월일)
    const genderCode = birth.charAt(6); // 7번째 숫자 (성별)

    // 현재 연도 가져오기
    const currentYear = new Date().getFullYear();
    let fullBirthYear;
    if (genderCode === "1" || genderCode === "2") {
      fullBirthYear = 1900 + birthYear;
    } else if (genderCode === "3" || genderCode === "4") {
      fullBirthYear = 2000 + birthYear;
    } else {
      fullBirthYear = birthYear < 30 ? 2000 + birthYear : 1900 + birthYear; // 기본 설정
    }

    // ✅ 최종 변환된 birth 값 (YYYYMMDD-G) → 백엔드 전송용
    user.value.birth = `${fullBirthYear}${birthDate}-${genderCode}`;

    // ✅ 나이 계산
    user.value.age = currentYear - fullBirthYear;

    // ✅ 성별 설정
    if (genderCode === "1" || genderCode === "3") {
      user.value.gender = "M"; // 남성
    } else if (genderCode === "2" || genderCode === "4") {
      user.value.gender = "F"; // 여성
    } else {
      user.value.gender = ""; // 알 수 없음
    }
  } else {
    user.value.birth = "";
    user.value.age = "";
    user.value.gender = "";
  }
};










const handleRegister = async () => {
  const result = await authStore.register(user.value);
  if (result.success) {
    successMessage.value = "회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.";
    setTimeout(() => {
      router.push("/login");
    }, 1500);
  } else {
    errorMessage.value = result.message; // ❗ 백엔드에서 온 메시지 출력
  }
};

const goToLogin = () => {
  router.push("/login");
};
</script>

<style scoped>
.register-container {
  max-width: 400px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px;
}

.error {
  color: red;
}

.success {
  color: green;
}
</style>
