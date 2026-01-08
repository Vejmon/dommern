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
  <div :class="`bane-${props.kusk.currentBane}`">
    <div class="flex flex-row gap-7 justify-between items-center mb-4">
      <div class="text-5xl w-3/5 overflow-hidden"> {{ kusk.name }} </div>
      <div class="text-3xl w-2/5">Bane: {{ kusk.currentBane }}</div>
    </div>
    <div class="flex flex-row gap-7 justify-between items-end min-h-25">
      <div class="text-8xl w-3/5 overflow-hidden">Tid: {{ formatMs(kusk.latest?.tid) }}</div>
      <div v-if="kusk.personalBest?.tid" class="text-7xl w-2/5 overflow-hidden">PB: {{ formatMs(kusk.personalBest?.tid) }}</div>
    </div>
  </div>
</template>

<style scoped>
</style>
