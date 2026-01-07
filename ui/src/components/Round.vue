<script setup lang="ts">
import type { Kusk }  from '../types/Kusk.ts';
import { BANE_COLOR_MAP } from '../types/BaneType.ts'

const props = defineProps<{ kusk: Kusk }>();


// watch( async)

</script>

<template>
  <div :class="`bane-${props.kusk.currentBane}`">
    <div> {{ kusk.name }} </div>
    <div>Tid: {{ kusk.latest?.tid }}</div>
    <div>PB: {{ kusk.personalBest?.tid }}</div>
  </div>
</template>

<style scoped>
</style>
