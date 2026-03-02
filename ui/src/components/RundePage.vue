vue
<!-- src/components/RundePage.vue -->
<script setup>
import { formatMs } from "@/utils/api.js";
import { ref, computed, watch } from "vue";

const props = defineProps({
  rundes: { type: Object, required: true },
});

const rundePage = ref(props.rundes);
const loading = ref(false);
const error = ref(null);

watch(
    () => props.rundes,
    (next) => {
      rundePage.value = next;
    },
    { immediate: true }
);

const pageNumber = computed(() => (rundePage.value?.page?.number ?? 0) + 1);
const totalPages = computed(() => rundePage.value?.page?.totalPages ?? 1);
const totalElements = computed(() => rundePage.value?.page?.totalElements ?? 0);

const linkHref = (rel) => rundePage.value?._links?.[rel]?.href ?? null;

const canPrev = computed(() => !!linkHref("prev") && !loading.value);
const canNext = computed(() => !!linkHref("next") && !loading.value);

const fetchByLink = async (rel) => {
  const href = linkHref(rel);
  if (!href) return;

  loading.value = true;
  error.value = null;
  try {
    const res = await fetch(href);
    if (!res.ok) throw new Error(`Failed to load ${rel} page`);
    rundePage.value = await res.json();
  } catch (e) {
    error.value = e;
  } finally {
    loading.value = false;
  }
};

const prevPage = () => {
  if (canPrev.value) fetchByLink("prev");
};

const nextPage = () => {
  if (canNext.value) fetchByLink("next");
};

const deleteRunde = async (runde) => {
  try {
    const response = await fetch(runde._links.self.href, { method: "DELETE" });
    if (!response.ok) throw new Error("Failed to delete runde");

    const list = rundePage.value?._embedded?.rundes;
    if (Array.isArray(list)) {
      rundePage.value._embedded.rundes = list.filter((r) => r.id !== runde.id);
    }
  } catch (e) {
    console.error("Error deleting runde:", e);
  }
};
</script>

<template>
  <div class="flex flex-col gap-4 pt-10 lg:pl-2 lg:pt-0">
    <h2 class="flex w-full justify-center text-4xl">-- Runder-- </h2>
    <div v-for="runde in rundePage?._embedded?.rundes" :key="runde.id">
      <div :class="`bane-${runde.baneType} text-3xl w-full justify-between flex flex-row gap-6`">
        <div>tid: {{ formatMs(runde.tid) }}</div>
        <button type="button" class="ring-2 px-2 hover:cursor-pointer" @click.stop="deleteRunde(runde)">
          X
        </button>
      </div>
    </div>

    <div class="flex items-center justify-between gap-4 p-6">
      <div class="text-sm flex justify-start gap-4">
        <div>
        {{ pageNumber }} av {{ totalPages }}
        </div>
        <div>
          {{ totalElements }} runder
        </div>
      </div>

      <div class="flex gap-4 justify-end">
        <button type="button" class="w-20 py-1 ring-2 rounded-lg bg-black hover:bg-gray-800 hover:cursor-pointer"
                :disabled="!canPrev" @click="prevPage">
          Forrige
        </button>
        <button type="button" class="w-20 ring-2 rounded-lg bg-black hover:bg-gray-800 hover:cursor-pointer"
                :disabled="!canNext" @click="nextPage">
          Neste
        </button>
      </div>
    </div>

    <div v-if="error" class="text-red-600 text-sm">
      {{ String(error) }}
    </div>
  </div>
</template>
