<script setup>

const props = defineProps({
  to: {
    type: String,
    required: false,
    default: '/'
  },
  linkName: {
    type: String,
    required: false,
    default: 'newKusk'
  }
});

</script>

<template>
  <div class="flex flex-row justify-between p-2">
    <div class="flex flex-col items-center">
      <a :href="to">
        <img src="/img/android-chrome-512x512.png" class="max-w-15" />
      </a>
    </div>
    <router-link :to="{name: linkName}">
      <div class="ring-2 rounded-lg p-2 text-4xl">Kusk +</div>
    </router-link>
  </div>
</template>

<style scoped>

</style>