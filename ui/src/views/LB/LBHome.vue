<script setup>
import Header from "@/components/Header.vue";

</script>

<template>
  <header>
    <Header class="app-header" to="/lb" link-name="lbNewKusk"/>
  </header>
  <main>
    <router-view />
  </main>

</template>

<style scoped>

</style>