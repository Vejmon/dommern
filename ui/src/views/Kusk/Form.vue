<script setup lang="ts">
import {ref} from "vue";
import {postData} from "@/utils/api.js";
import {BaneType} from "@/types/BaneType";

const formRef = ref<HTMLFormElement | null>(null);
const currentBane = ref<BaneType | null>(null);
const selectableBanes: BaneType[] = [
    BaneType.KORTESTE_VEIEN,
    BaneType.FRODE_SPESIAL,
    BaneType.UTEN_NAVN,
    BaneType.BESTEFAR_BANEN
];

const submitForm = async () => {
  console.log('ref.value', formRef.value);
  if (!formRef.value) return;
  const formData = new FormData(formRef.value);
  const data = Object.fromEntries(formData.entries());
  await postData('/kusks', data)
    .then(response => {
      console.log('Form submitted successfully:', response);
    })
    .catch(error => {
      console.error('Error submitting form:', error);
    });
}

</script>

<template>
  <div>
    <form id="kuskForm" ref="formRef" class="input-form pb-32 max-w-2xl">
      <div class="input-group">
        <label for="name">Navn:</label>
        <input class="input-text" type="text" id="name" name="name" />
      </div>
      <div class="input-group">
        <label for="email">Epost:</label>
        <input class="input-text" type="text" id="email" name="email" />
      </div>
      <div class="input-group">
        <label for="currentBane">Bane:</label>
        <select id="currentBane" name="currentBane" v-model="currentBane" class="input-text">
          <option :value="null"></option>
          <option
              v-for="bane in selectableBanes"
              :key="bane"
              :value="bane"
          >
            {{ bane }}
          </option>
        </select>
      </div>
      <button class="w-full input-text hover:cursor-pointer" type="button" @click="submitForm">Submit</button>
    </form>
  </div>
</template>

<style scoped>
</style>
