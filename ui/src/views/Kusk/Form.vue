<script setup lang="ts">
import {ref} from "vue";
import {postData} from "@/utils/api.js";

const formRef = ref<HTMLFormElement | null>(null);

const submitForm = async () => {
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
    <form id="kuskForm">
      <div>
        <label for="name">Navn:</label>
        <input type="text" id="name" name="name" />
      </div>
      <div>
        <label for="email">Epost:</label>
        <input type="text" id="email" name="email" />
      </div>
      <div>
        <label for="currentBane">Bane:</label>
        <input type="text" id="currentBane" name="currentBane" />
      </div>
      <button class="hover:cursor-pointer-" type="button" @click="submitForm">Submit</button>
    </form>
  </div>
</template>

<style scoped>
</style>
