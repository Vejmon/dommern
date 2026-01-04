<script setup>
defineProps({
  msg: {
    type: String,
    required: false,
  },
})

fetch('/actuator/health')
    .then(res => console.log('then', res))
    .catch(err => console.log('err', err));

</script>

<template>
    <strong>yiha</strong>
</template>

<style scoped>
</style>
