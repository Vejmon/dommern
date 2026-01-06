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
  <ul>
    <li>Navn: {{ name }}</li>
    <li>Bane: {{ bane }}</li>
    <li>Tid: {{ tid }}</li>
    <li>PB: {{ pb }}</li>
  </ul>
</template>

<style scoped>
</style>
