<script setup lang="ts">
import {ref, watch} from "vue";
import {postData} from "@/utils/api.js";
import {BaneType} from "@/types/BaneType";

const formRef = ref<HTMLFormElement | null>(null);
const currentBane = ref<BaneType | null>(BaneType.I_DEPO);
const submittedName = ref(null);
const selectableBanes: BaneType[] = [
    BaneType.KORTESTE_VEIEN,
    BaneType.FRODE_SPESIAL,
    BaneType.UTEN_NAVN,
    BaneType.BESTEFAR_BANEN
];

const submitForm = async () => {
  if (!formRef.value) return;
  const formData = new FormData(formRef.value);
  const data = Object.fromEntries(formData.entries());
  await postData('/kusks', data)
    .then(response => {
      submittedName.value = response.name;
    })
    .catch(error => {
      console.error('Error submitting form:', error);
    });
}
watch(submittedName, async (newValue) => {
  if (!newValue) return;

  formRef.value.reset();

  await new Promise(resolve => setTimeout(resolve, 1000));
  submittedName.value = null;
  currentBane.value = BaneType.I_DEPO;
})

</script>

<template>
  <div class="xl:hidden text-4xl pl-24 pt-5">
    --- Kusk form ---
  </div>
  <div>
    <form id="kuskForm" ref="formRef" class="input-form pb-32 max-w-2xl" @submit.prevent="">
      <div class="input-group">
        <label for="name">Navn:</label>
        <input class="input-text" type="text" id="name" name="name" />
      </div>
      <div class="input-group">
        <label for="email">Epost:</label>
        <input class="input-text" type="email" id="email" name="email" />
      </div>
      <div class="input-group">
        <label for="currentBane">Bane:</label>
        <select id="currentBane" name="currentBane" v-model="currentBane" class="input-text">
          <option :value="BaneType.I_DEPO">{{BaneType.I_DEPO}}</option>
          <option
              v-for="bane in selectableBanes"
              :key="bane"
              :value="bane"
          >
            {{ bane }}
          </option>
        </select>
      </div>
      <div class="justify-between input-group flex-row">
        <div v-if="!submittedName" class="opacity-0"> hidden </div>
        <div v-else class="pl-5"> {{submittedName}} Lagret! </div>
        <button class="w-2/5 input-text hover:cursor-pointer active:bg-green-600" type="button" @click="submitForm">
          Lagre
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>
</style>
