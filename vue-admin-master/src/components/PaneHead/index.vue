<template>
  <div class="panel-heading" :style="headingStyle">
    <div class="panel-lead">
      <div class="title" :style="{ color: titleColor }">
        {{ props.route.meta.title }}
      </div>
      <p class="description" :style="{ color: titleColor }">
        {{ props.route.meta.descr }}
      </p>
    </div>
  </div>
</template>

<script setup>
const props = defineProps(["route"])
import { computed } from "vue"
import { useSettingStore } from "@/stores/modules/setting"
import { storeToRefs } from "pinia"
const settingStore = useSettingStore()
const { dark } = storeToRefs(settingStore)

const headingStyle = computed(() => ({
  backgroundColor: dark.value ? "#262626" : "#e6e6e6",
  transition: "all 0.2s",
}))

const titleColor = computed(() => (dark.value ? "#fff" : "#000"))
</script>

<style lang="less" scoped>
.panel-heading {
  padding: 20px;
  border-color: #e8edf0;
  position: relative;

  .panel-lead {
    font-size: 15px;

    .title {
      font-weight: bold;
      font-style: normal;
    }

    .description {
      margin-top: 10px;
      white-space: pre-wrap;
    }
  }
}
</style>
