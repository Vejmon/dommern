<script setup lang="ts">
import type {Kusk} from '@/types/Kusk.ts';
import {onMounted, onUnmounted, ref} from "vue";
import {formatMs} from "@/utils/api";

const evtSrc = new EventSource('/linje/sse');
const kusks = ref<Kusk[]>([]);

onMounted(() => {
  evtSrc.onmessage = (event) => {
    kusks.value = JSON.parse(event.data) as Kusk[];
  };

  evtSrc.onerror = (err) => {
    console.error('EventSource error:', err);
  };
});

onUnmounted(() => {
  if (evtSrc) {
    evtSrc.close();
  }
});

const baneInitials = (bane: string) => {
  return bane.split("_").map(word => word.charAt(0).toUpperCase()).join("");
}

</script>

<template>
  <div>
    <div class="flex flex-col xl:flex-row justify-center">
      <div class="xl:min-w-7xl min-w-sm flex flex-col gap-4">
        <div v-for="kusk in kusks" :key="kusk.id">
          <router-link :to="{name: 'viewKusk', params:{id: kusk.id}}" class="no-underline">
            <div :class="`bane-${kusk.currentBane} text-3xl`">
              <div class="flex lg:flex-row flex-col lg:gap-7 lg:justify-between lg:items-center lg:mb-4">
                <div class="lg:text-5xl lg:w-3/5 truncate lg:min-h-13 self-center"> {{ kusk.name }} </div>
                <div class="flex flex-row gap-2 lg:w-2/5 justify-between lg:justify-start">
                  <div>Bane: </div>
                  <div>{{ baneInitials(kusk.currentBane) }}</div>
                </div>
              </div>
              <div class="flex lg:flex-row flex-col lg:gap-7 justify-between lg:items-end lg:min-h-25">
                <div class="flex flex-row gap-6 truncate justify-between lg:justify-start lg:text-8xl lg:w-3/5">
                  <div>Tid: </div>
                  <div>{{ formatMs(kusk.latest?.tid) }} </div>
                </div>
                <div v-if="kusk.personalBest?.tid" class="flex flex-row gap-6 justify-between lg:justify-start lg:text-7xl lg:w-2/5">
                  <div>PB: </div>
                  <div>{{ formatMs(kusk.personalBest?.tid) }}</div>
                </div>
              </div>
            </div>
          </router-link>
        </div>
      </div>
        <router-view />
    </div>
  </div>
</template>

<style scoped>
</style>
