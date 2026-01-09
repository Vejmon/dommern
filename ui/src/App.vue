<script setup lang="ts">
import Round from './components/Round.vue'
import { onMounted, onUnmounted, ref } from 'vue';
import type { Kusk } from './types/Kusk.ts';

const kusks = ref<Kusk[]>([]);

onMounted(() => {
  const evtSrc = new EventSource('linje/sse');
  evtSrc.onmessage = (event) => {
    kusks.value = JSON.parse(event.data) as Kusk[];
  };

  evtSrc.onerror = (err) => {
    console.error('EventSource error:', err);
  };
});

onUnmounted(() => {
  if (evtSrc) {
    evtSrc.close();
    evtSrc = null;
  }
});

</script>

<template>
  <main class="">
    <div class="xl:min-w-7xl min-w-sm flex flex-col">
      <div v-for="kusk in kusks" :key="kusk.id">
        <Round :kusk="kusk"/>
      </div>
    </div>
  </main>
</template>

<style scoped>
</style>
