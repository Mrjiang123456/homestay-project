<template>
  <svg class="icon" aria-hidden="true" :style="{ width, height }" style="margin-right: 10px">
    <use :xlink:href="`#icon-${icon}`" :fill="color"></use>
  </svg>
</template>

<script setup lang="ts">
defineProps({
  icon: {
    type: String,
    default: "",
  },
  color: {
    type: String,
    default: "",
  },
  width: {
    type: String,
    default: "15px",
  },
  height: {
    type: String,
    default: "15px",
  },
})
</script>
