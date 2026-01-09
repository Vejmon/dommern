<script setup lang="ts">
import Round from './components/Round.vue'
import Header from './components/Header.vue'
import {onMounted, onUnmounted, ref} from 'vue';
import type {Kusk} from './types/Kusk.ts';

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
  <header>
    <Header class="absolute top-0 left-0 position min-h-10 bg-stone-950 ring-2 ring-stone-700 rounded-b-lg w-full"/>
  </header>
  <main class="flex justify-center pt-24">
    <div class="xl:min-w-7xl min-w-sm flex flex-col gap-4">
      <div v-for="kusk in kusks" :key="kusk.id">
        <Round :kusk="kusk"/>
      </div>
    </div>
  </main>
</template>

<style scoped>
</style>
