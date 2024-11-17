<template>
  <div>
    <el-table
      :data="props.data"
      style="width: 100%"
      :header-cell-style="{
        backgroundColor: dark ? '#1D1E1F' : '#fff',
        transition: 'all 0.2s',
        fontFamily: 'myFont',
        fontSize: '15px',
        color: dark ? '#fff' : '#000',
      }"
      :row-style="{
        background: dark ? '#1D1E1F' : '#fff',
        transition: 'all 0.2s',
        color: dark ? '#fff' : '#000',
        fontFamily: 'myFont',
        fontSize: '14px',
      }"
    >
      <el-table-column prop="name" label="维度" />
      <el-table-column prop="value" label="数值" />
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from "vue"
import { useSettingStore } from "@/stores/modules/setting"
import { storeToRefs } from "pinia"
const settingStore = useSettingStore()
const { dark } = storeToRefs(settingStore)
const props = defineProps(["data"])
const items = ref([])
watch(
  () => props.data,
  (newData) => {
    // 更新图表数据
    items.value = newData
  },
)
</script>

<style scoped lang="scss"></style>
