<script setup lang="ts">
import {Kusk} from "@/types/Kusk";
import {PropType} from "vue";

const props = defineProps({
  kusk: {
    type: Object as PropType<Kusk>,
    required: true
  }
});

</script>

<template>
  <div>

  </div>
</template>

<style scoped>
</style>
