<script setup lang="ts">
import type {Kusk} from '../types/Kusk.ts';

const props = defineProps<{ kusk: Kusk }>();


// watch( async)

const formatMs = (ms) => {
  if (ms == null || ms === '') return '';
  const n = Number(ms);
  if (!Number.isFinite(n)) return '';
  const totalMs = Math.max(0, Math.floor(n));
  const seconds = Math.floor(totalMs / 1000);
  const remainderMs = totalMs % 1000;
  return `${seconds}:${String(remainderMs).padStart(3, '0')}`;
}

</script>

<template>
  <div :class="`bane-${props.kusk.currentBane}`">
    <div> {{ kusk.name }} </div>
    <div>Tid: {{ formatMs(kusk.latest?.tid) }}</div>
    <div>PB: {{ formatMs(kusk.personalBest?.tid) }}</div>
  </div>
</template>

<style scoped>
</style>
