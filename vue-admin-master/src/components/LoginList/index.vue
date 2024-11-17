<template>
  <div class="container">
    <el-timeline style="max-width: 600px">
      <el-timeline-item
        v-for="(item, index) in items"
        :key="index"
        color="#0bbd87"
        :timestamp="item.createTime"
      >
        {{ item.nickname + " - " + item.content }}
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script setup>
import { defineProps, ref, watch } from "vue"

const props = defineProps(["data"])
const items = ref([])
watch(
  () => props.data,
  (newData) => {
    items.value = newData
  },
)
</script>
<style>
.container {
  margin-left: 20px;
  margin-top: 20px;
}
</style>
