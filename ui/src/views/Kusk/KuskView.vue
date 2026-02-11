<script setup lang="ts">

import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const kusk = ref(null);

const fetchKuskData = async id => {
  try {
    const response = await fetch(`/kusks/${id}`);
    if (!response.ok) throw new Error('Failed to load kusk data');
    kusk.value = await response.json();
  } catch (error) {
    console.error('Error fetching kusk data:', error);
  }
};

onMounted(() => {
  const id = route.params.id;
  fetchKuskData(id);
});


</script>

<template>
    <div>
        Kusk View
    </div>
</template>

<style scoped>
</style>
