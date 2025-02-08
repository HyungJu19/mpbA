<template>
  <div class="location-map">
    <h3>📍 위치 정보</h3>

    <div v-if="!isPreview" class="address-container">
      <input
          v-if="isEditing"
          type="text"
          v-model="address"
          placeholder="예식장 주소를 입력하세요"
          @keyup.enter="saveAddress"
          class="address-input"
      />
      <p v-else>입력한 주소: {{ address }}</p>

      <!-- ✅ 수정 및 저장 버튼 -->
      <button v-if="isEditing" @click="saveAddress" class="save-btn">✔ 저장</button>
      <button v-else @click="enableEditing" class="edit-btn">✏ 수정</button>
    </div>

    <!-- ✅ 지도 컨테이너 (가로 100%, 높이 반응형 적용) -->
    <div ref="mapContainer" class="map-container"></div>
  </div>
</template>

<script setup>
import { ref, watch, defineProps, defineEmits, computed, onMounted, nextTick } from "vue";
import { useRoute } from "vue-router";

const props = defineProps({ modelValue: String });
const emit = defineEmits(["update:modelValue"]);
const address = ref(props.modelValue || "서울특별시 강남구 테헤란로 123");
const isEditing = ref(true);
const route = useRoute();
const isPreview = computed(() => route.path.startsWith("/angorani"));
const mapContainer = ref(null);
let map, geocoder, marker;

// ✅ Google 지도 초기화 함수
const initMap = () => {
  if (!window.google || !mapContainer.value) return;

  geocoder = new google.maps.Geocoder();
  map = new google.maps.Map(mapContainer.value, {
    zoom: 16,
    center: { lat: 37.5665, lng: 126.9780 }, // 기본값: 서울
    disableDefaultUI: false,
    draggable: true, // 지도 드래그 가능
  });

  marker = new google.maps.Marker({
    map,
    position: map.getCenter(),
    animation: google.maps.Animation.DROP, // 부드러운 애니메이션 추가
  });

  updateMap(address.value);
};

// ✅ 주소 변경 시 지도 이동 (부드럽게 이동)
const updateMap = (newAddress) => {
  if (!geocoder || !map) return;

  geocoder.geocode({ address: newAddress }, (results, status) => {
    if (status === "OK") {
      const location = results[0].geometry.location;

      // ✅ 지도 애니메이션 이동
      map.panTo(location);
      map.setZoom(16);

      // ✅ 마커 애니메이션 추가
      marker.setPosition(location);
      marker.setAnimation(google.maps.Animation.BOUNCE);
      setTimeout(() => marker.setAnimation(null), 1500);
    } else {
      console.error("주소 변환 실패:", status);
    }
  });
};

// ✅ 주소 저장 후 지도 업데이트
const saveAddress = () => {
  isEditing.value = false;
  emit("update:modelValue", address.value);
  updateMap(address.value);
};

// ✅ 수정 버튼 클릭 시 다시 입력 가능하게
const enableEditing = () => {
  isEditing.value = true;
};
const GOOGLE_MAPS_API_KEY = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;
// ✅ Google Maps API 로드 후 지도 초기화
onMounted(async () => {
  if (window.google) {
    await nextTick();
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `https://maps.googleapis.com/maps/api/js?key=${GOOGLE_MAPS_API_KEY}&libraries=places`;
    script.defer = true;
    script.onload = async () => {
      await nextTick();
      initMap();
    };
    document.head.appendChild(script);
  }
});

// ✅ 주소 변경 감지 후 지도 업데이트
watch(address, updateMap);
</script>

<style scoped>
/* ✅ 부모 컨테이너를 가로 100% 설정 */
.location-map {
  padding: 10px;
  text-align: center;
  width: 100%;
  max-width: 100vw;
  position: relative;
}

/* ✅ 주소 입력 필드 */
.address-container {
  margin-bottom: 10px;
}

.address-input {
  width: 80%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
}

/* ✅ 버튼 스타일 */
.edit-btn, .save-btn {
  padding: 6px 12px;
  margin-left: 5px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.edit-btn {
  background-color: #ffc107;
  color: black;
}

.save-btn {
  background-color: #28a745;
  color: white;
}

.edit-btn:hover {
  background-color: #e0a800;
}

.save-btn:hover {
  background-color: #218838;
}

/* ✅ 지도 컨테이너: 부모 크기를 따라 100% 적용 */
.map-container {
  width: 100%;
  height: 75vh; /* ✅ 높이를 75%로 설정하여 화면에 꽉 차게 */
  min-height: 400px; /* ✅ 최소 높이 지정 */
  border-radius: 8px;
  margin-top: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  position: relative;
}

/* ✅ 반응형 스타일 */
@media (max-width: 768px) {
  .map-container {
    height: 60vh;
    min-height: 300px;
  }
}

@media (max-width: 480px) {
  .map-container {
    height: 50vh;
    min-height: 250px;
  }
}
</style>
