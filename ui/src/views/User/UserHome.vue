<script setup lang="ts">
import Header from "@/components/Header.vue";
import { getData } from "@/utils/api";
import { BaneType} from "@/types/BaneType.ts";
import { ref, onMounted, onBeforeUnmount, watch } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const defaultKusks = [
    BaneType.FRODE_SPESIAL.toString(),
    BaneType.KORTESTE_VEIEN.toString(),
    BaneType.UTEN_NAVN.toString(),
    BaneType.BESTEFAR_BANEN.toString()
];
const kusks = ref([]);
const search = ref('');
const selectedKusk = ref(null);
const selectBane = ref(null);
const loading = ref(false);
const error = ref(null);

const fetchBils = async () => {
  loading.value = true;
  error.value = null;
  try {
    const data = await getData('/bils', 300);
    kusks.value = data._embedded.bils;
  } catch (e) {
    error.value = 'Failed to load bils';
    console.error(e);
  } finally {
    loading.value = false;
  }
}

const fetchKusks = async () => {
  loading.value = true;
  error.value = null;
  try {
    const data = await getData('/kusks', 300);
    kusks.value = data._embedded.kusks.filter(k => !defaultKusks.includes(k.name));
  } catch (e) {
    error.value = 'Failed to load kusks';
    console.error(e);
  } finally {
    loading.value = false;
  }
};

onMounted(async () => {
  await fetchKusks();
});

watch(selectedKusk, () => {
  router.push({name: 'userView', params: {id: selectedKusk.value.id}});
})

</script>

<template>
  <header>
    <Header class="app-header" to="/"/>
  </header>
  <main>
    <div id="kuskForm" class="input-form">
      <div class="input-group">
        <label for="kusk-select">Velg Kusk:</label>
        <div v-if="loading">Loading kusks...</div>
        <div v-else-if="error" class="text-red-600">{{ error }}</div>
        <select
            id="kusk-select"
            v-else
            v-model="selectedKusk"
            class="input-text border p-2 rounded"
        >
          <option :value="null" disabled>Select one...</option>
          <option
              v-for="k in kusks"
              :key="k.id ?? k._id ?? k.name"
              :value="k"
          >
            {{ k.name ?? k.id ?? JSON.stringify(k) }}
          </option>
        </select>
      </div>

      <div>
        <div class="input-group">
          <label for="bane-select">Velg Bane:</label>
          <select
              id="bane-select"
              v-model="selectBane"
              class="input-text border p-2 rounded"
          >
            <option :value="null" disabled>Select one...</option>
            <option
                v-for="b in defaultKusks"
                :value="b"
            >
              {{ b }}
            </option>
          </select>
        </div>
      </div>
    </div>

    <router-view v-slot="{ Component }">
      <component :is="Component" :kusk="selectedKusk" />
    </router-view>
  </main>


</template>

<style scoped>

</style>