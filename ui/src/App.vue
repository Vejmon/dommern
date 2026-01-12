<script setup lang="ts">
import Round from './components/Round.vue'
import Header from './components/Header.vue'
import {onMounted, onUnmounted, ref} from 'vue';
import type {Kusk} from './types/Kusk.ts';

const kusks = ref<Kusk[]>([]);
const evtSrc = new EventSource('linje/sse');

onMounted(() => {
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
  }
});

</script>

<template>
  <header>
    <Header class="app-header"/>
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
