<script setup>
defineProps({
  msg: {
    type: String,
    required: false,
  },
})

fetch('linje')
    .then(res => res.text())
    .then(data => console.log('res:', data))
    .catch(err => console.log('err', err));

</script>

<template>
    <strong>yiha</strong>
</template>

<style scoped>
</style>
