<template>
  <div class="chart-container" ref="myRef" />
</template>

<script setup>
import * as echarts from "echarts"
import { nextTick, onMounted, ref, watch } from "vue"

// 定义属性
const myRef = ref()
const myChart = ref()
const props = defineProps(["data", "colorStops", "route"])

// 更新图表数据
const updateChart = () => {
  if (myChart.value) {
    // 更新 series 数据
    option.series[0].data[0].value = props.data.map((item) => Number(item.value))
    myChart.value.setOption(option, true) // true 表示优化更新
  }
}

// 监听数据变化并更新图表
watch(
  () => props.data,
  (newData) => {
    if (newData) {
      updateChart()
    }
  },
  { immediate: true },
)

// 初始化 ECharts
onMounted(() => {
  nextTick(() => {
    if (!myRef.value) return
    myChart.value = echarts.init(myRef.value)
    // 设置图表初始数据
    updateChart()
  })
})

// 图表配置项
const option = {
  title: {
    text: "",
  },
  legend: {
    data: ["评分"],
  },
  radar: {
    indicator: [],
  },
  splitArea: {
    areaStyle: {
      color: ["#77EADF", "#26C3BE", "#64AFE9", "#428BD4"],
      shadowColor: "rgba(0, 0, 0, 0.2)",
      shadowBlur: 10,
    },
  },
  axisLine: {
    lineStyle: {
      color: "rgba(211, 253, 250, 0.8)",
    },
  },
  splitLine: {
    lineStyle: {
      color: "rgba(211, 253, 250, 0.8)",
    },
  },
  series: [
    {
      name: "Budget vs spending",
      type: "radar",
      data: [
        {
          value: [],
          name: "最小值",
          areaStyle: {
            color: "rgba(255, 228, 52, 0.6)",
          },
          label: {
            show: true,
            formatter: (params) => params.value,
          },
        },
      ],
    },
  ],
}
</script>
