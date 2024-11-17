<template>
  <div>
    <el-row :gutter="10">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" class="row-spacing">
        <el-card>
          <pane-head :route="route" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="16" class="row-spacing">
        <el-card style="padding: 20px">
          <div class="title">{{ route.meta.title }}</div>
          <div style="height: 600px; width: 100%">
            <component
                :is="chartComponent('hbar')"
                :data="items"
                :colorStops="colorStops"
                :route="route"
            />
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="8" class="row-spacing">
        <el-card style="padding: 20px">
          <div class="title">{{ route.meta.title }}</div>
          <div style="height: 600px; width: 100%; overflow-y: auto">
            <TableData :data="items" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { defineAsyncComponent, onMounted, ref } from "vue"
import { useRoute } from "vue-router"
import { getAnalyze2 } from "@/api/index.ts"
import { colorList } from "@/utils/chartColor.ts"
import TableData from "@/components/TableData/index.vue"

const route = useRoute()

const chartComponent = (val) => {
  return defineAsyncComponent(
      () => import(`@/components/EchartsChat/${val}.vue`),
  )
}

const items = ref([])
onMounted(() => {
  getAnalyze2().then((res) => {
    items.value = res.data
  })
})

const colorStops = colorList[Math.floor(Math.random() * colorList.length)]
</script>

<style scoped lang="scss">
:deep(.el-card__body) {
  padding: 0 !important;
}

.row-spacing {
  margin-bottom: 10px;
}

.title {
  font-size: 16px;
  font-weight: bold;
  padding: 10px;
  border-bottom: 1px var(--el-border-color-dark) solid;
}
</style>