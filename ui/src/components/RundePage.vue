<script setup>


import {formatMs} from "@/utils/api.js";
import {ref} from "vue";

const props = defineProps({
  rundes: {
    type: Object,
    required: true
  }
});

const rundePage = ref(props.rundes);


const deleteRunde = async (id) => {
  try {
    const response = await fetch(`/rundes/${id}`, {
      method: 'DELETE'
    });
    if (!response.ok) {
      throw new Error('Failed to delete runde');
    }
    rundePage.value._embedded.rundes = rundePage.value._embedded.rundes.filter(r => r.id !== id);
  } catch (error) {
    console.error('Error deleting runde:', error);
  }
}

</script>

<template>
<div class="flex flex-col gap-4">
  <div v-for="runde in rundePage?._embedded?.rundes" :key="runde.id">
    <div :class="`bane-${runde.baneType} text-3xl w-full justify-between flex flex-row gap-6`">
      <div>
        tid: {{formatMs(runde.tid)}}
      </div>
        <button class="ring-2 ring-round hover:cursor-pointer" @click.prevent="deleteRunde(runde.id)">X</button>
    </div>
  </div>
</div>
</template>

<style scoped>

</style>