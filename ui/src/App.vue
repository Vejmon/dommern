<script setup lang="ts">
import Round from './components/Round.vue'
import { ref } from 'vue';
import type { Kusk } from './types/Kusk.ts';

const riders = ref<Kusk[]>([]);
const evtSrc = new EventSource('linje/sse');
evtSrc.onmessage = (event) => {
  riders.value = JSON.parse(event.data) as Kusk[];
};

evtSrc.onerror = (err) => {
  console.error('EventSource error:', err);
};

</script>

<template>
  <main>
    <div>
      <div v-for="rider in riders" :key="rider.id">
        <Round :name="rider.name" :bane="rider.currentBane" :pb="rider.personalBest?.tid" :tid="rider.latest?.tid" />
      </div>
    </div>
  </main>
</template>

<style scoped>
</style>
