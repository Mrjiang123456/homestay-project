<template>
  <div class="chart-container" ref="myRef" />
</template>

<script setup>
import * as echarts from "echarts"
import { ref, onMounted, watch, nextTick } from "vue"

// 定义属性
const myRef = ref()
const myChart = ref()
const props = defineProps(["data", "colorStops", "route"])

// 更新图表数据
const updateChart = () => {
  if (!myChart.value) return

  // 更新图表的配置项
  if (myChart.value && props.data && props.route) {
    option.xAxis.name = props.route.meta.xname || ""
    option.yAxis.name = props.route.meta.yname || ""
  }
  option.yAxis.data = props.data.map((item) => item.name)
  option.series[0].data = props.data.map((item) => Number(item.value))

  // 更新渐变色
  if (props.colorStops) {
    option.series[0].itemStyle.color.colorStops = props.colorStops
  }

  myChart.value.setOption(option)
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
    if (myRef.value) {
      myChart.value = echarts.init(myRef.value)
      updateChart()
    }
  })
})

// 图表配置项
const option = {
  title: { text: "" },
  tooltip: {},
  xAxis: {
    type: "value",
    name: "",
    splitLine: { show: false },
  },
  yAxis: {
    axisLabel: { interval: 0, fontSize: 12 },
    data: [],
    splitLine: { show: false },
  },
  series: [
    {
      type: "bar",
      data: [],
      label: {
        show: true,
        position: "right",
        color: "#424656",
        fontSize: 14,
      },
      showBackground: false,
      itemStyle: {
        color: {
          type: "linear",
          x: 0,
          y: 0,
          x2: 1,
          y2: 0,
          colorStops: [],
        },
      },
    },
  ],
}
</script>