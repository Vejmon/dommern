<script setup>
import Round from './components/Round.vue'

let riders = [];
const evtSrc = new EventSource('linje/sse');
evtSrc.onmessage = (event) => {
  riders = JSON.parse(event.data);
  console.log(riders);
};

evtSrc.onerror = (err) => {
  console.error('EventSource error:', err);
};

</script>

<template>
  <main>
    <ul>
      <li v-for="rider in riders" :key="rider.id">
        <Round name="rider.name" bane="rider.currentBane" pb="personalBest.tid" />
      </li>
    </ul>
  </main>
</template>

<style scoped>
</style>1
