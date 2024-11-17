<template>
  <div class="chart-container" ref="myRef" />
</template>

<script setup>
import * as echarts from "echarts"
import { onMounted, ref, watch, nextTick } from "vue"

// 定义属性
const myRef = ref()
const myChart = ref()
const props = defineProps(["data", "colorStops", "route"])

// 监听数据变化并更新图表
const updateChart = () => {
  if (myChart.value) {
    option.series[0].data = props.data
    myChart.value.setOption(option, true) // 使用 `true` 表示合并而不是完全重绘
  }
}

// 初始化 ECharts
onMounted(() => {
  nextTick(() => {
    if (myRef.value) {
      myChart.value = echarts.init(myRef.value)
      updateChart()
    }
  })
})

// 监听数据变化
watch(
  () => props.data,
  (newData) => {
    updateChart()
  },
  { immediate: true },
)

// 图表配置项
const option = {
  title: {
    text: "",
    left: "center",
  },
  tooltip: {
    trigger: "item",
  },
  toolbox: {
    show: true,
    feature: {
      mark: { show: true },
    },
  },
  legend: {
    orient: "vertical",
    left: "left",
  },
  series: [
    {
      name: "数据",
      type: "pie",
      radius: ["30%", "60%"],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: "#fff",
        borderWidth: 2,
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: "bold",
        },
      },
      labelLine: {
        show: true,
      },
      data: [],
      label: {
        show: true,
        formatter: "{b}: {c}({d}%)",
      },
    },
  ],
}
</script>
