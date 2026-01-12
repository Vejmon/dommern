<script setup lang="ts">
import type {Kusk} from '../types/Kusk.ts';

const props = defineProps<{ kusk: Kusk }>();


// watch( async) todo: pulse change to PB

const formatMs = (ms) => {
  if (ms == null || ms === '') return '---';
  const n = Number(ms);
  if (!Number.isFinite(n)) return 'NaN';
  const totalMs = Math.max(0, Math.floor(n));
  const seconds = Math.floor(totalMs / 1000);
  const remainderMs = totalMs % 1000;
  return `${seconds}:${String(remainderMs).padStart(3, '0')}`;
}

const baneInitials = (bane: string) => {
  return bane.split("_").map(word => word.charAt(0).toUpperCase()).join("");
}

</script>

<template>
  <div :class="`bane-${props.kusk.currentBane} text-3xl`">
    <div class="flex lg:flex-row flex-col lg:gap-7 lg:justify-between lg:items-center lg:mb-4">
      <div class="lg:text-5xl lg:w-3/5 truncate lg:min-h-13 self-center"> {{ kusk.name }} </div>
      <div class="flex flex-row gap-2 lg:w-2/5 justify-between lg:justify-start">
        <div>Bane: </div>
        <div>{{ baneInitials(kusk.currentBane) }}</div>
      </div>
    </div>
    <div class="flex lg:flex-row flex-col lg:gap-7 justify-between lg:items-end lg:min-h-25">
      <div class="flex flex-row gap-6 truncate justify-between lg:justify-start lg:text-8xl lg:w-3/5">
        <div>Tid: </div>
        <div>{{ formatMs(kusk.latest?.tid) }} </div>
      </div>
      <div v-if="kusk.personalBest?.tid" class="flex flex-row gap-6 justify-between lg:justify-start lg:text-7xl lg:w-2/5">
        <div>PB: </div>
        <div>{{ formatMs(kusk.personalBest?.tid) }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
