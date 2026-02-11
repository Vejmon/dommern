<script setup lang="ts">

import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const kusk = ref(null);
const rundePage = ref(null);

const fetchKuskData = async id => {
    const response = await fetch(`/kusks/${id}`);
    if (!response.ok) throw new Error('Failed to load kusk data');
    kusk.value = await response.json();
    const rundeResponse = await fetch(kusk.value.links.namedItem("paged-runder")?.href);
    if (!rundeResponse.ok) throw new Error('Failed to load runde data');
    rundePage.value = await rundeResponse.json();
}

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
