<script setup>
defineProps({
  name: {
    type: String,
    required: true,
  },
  bane: {
    type: String,
    required: true,
  },
  tid: {
    type: Number,
    required: false,
  },
  pb: {
    type: Number,
    required: false,
  }
});

</script>

<template>
  <div>
    <div class="text-lg p-16 font-semibold text-blue-600">{{ name }} </div>
    <div class="text-yellow-600">Bane: {{ bane }}</div>
    <div>Tid: {{ tid }}</div>
    <div>PB: {{ pb }}</div>
  </div>
</template>

<style scoped>
</style>
