<script setup lang="ts">
import type {Kusk} from '../types/Kusk.ts';

const props = defineProps<{ kusk: Kusk }>();


// watch( async)

const formatMs = (ms) => {
  if (ms == null || ms === '') return '---';
  const n = Number(ms);
  if (!Number.isFinite(n)) return 'NaN';
  const totalMs = Math.max(0, Math.floor(n));
  const seconds = Math.floor(totalMs / 1000);
  const remainderMs = totalMs % 1000;
  return `${seconds}:${String(remainderMs).padStart(3, '0')}`;
}

</script>

<template>
  <div :class="`bane-${props.kusk.currentBane} text-3xl`">
    <div class="flex lg:flex-row flex-col lg:gap-7 lg:justify-between lg:items-center lg:mb-4">
      <div class="lg:text-5xl lg:w-3/5 truncate lg:min-h-13"> {{ kusk.name }} </div>
      <div class="lg:w-2/5">Bane: {{ kusk.currentBane }}</div>
    </div>
    <div class="flex lg:flex-row flex-col lg:gap-7 lg:justify-between lg:items-end lg:min-h-25">
      <div class="lg:text-8xl lg:w-3/5">Tid: {{ formatMs(kusk.latest?.tid) }}</div>
      <div v-if="kusk.personalBest?.tid" class="lg:text-7xl lg:w-2/5">PB: {{ formatMs(kusk.personalBest?.tid) }}</div>
    </div>
  </div>
</template>

<style scoped>
</style>
