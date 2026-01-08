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
  <main>
    <div class="flex flex-col gap-4 p-4 m-auto min-w-6xl">
      <div v-for="kusk in kusks" :key="kusk.id">
        <div>
          <Round :kusk="kusk"/>
        </div>
      </div>
    </div>
  </main>
</template>

<style scoped>
</style>
