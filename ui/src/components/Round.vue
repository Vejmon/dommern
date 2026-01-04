<script setup>
defineProps({
  msg: {
    type: String,
    required: false,
  },
})

fetch('actuator/health')
    .then(res => res.text())
    .then(data => {
      console.log('body:', data);
    })
    .catch(err => console.log('err', err));

</script>

<template>
    <strong>yiha</strong>
</template>

<style scoped>
</style>
