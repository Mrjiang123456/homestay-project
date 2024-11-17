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
const updateChart = (data) => {
  if (!myChart.value) return
  // 设置 X 和 Y 轴的名称
  option.xAxis.name = props.route.meta.xname
  option.yAxis.name = props.route.meta.yname
  // 过滤并映射数据
  option.series[0].data = data
    .filter((item) => parseFloat(item.name) <= 1000)
    .map((item) => [item.name, item.value])
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
  { immediate: true }, // 确保初始数据也能被捕获
)

// 初始化 ECharts
onMounted(() => {
  nextTick(() => {
    if (myRef.value) {
      myChart.value = echarts.init(myRef.value)
      if (props.data && props.data.length) {
        updateChart(props.data)
      }
    }
  })
})

// 随机生成符号
const randomSymbol = () => {
  const symbols = ["circle", "diamond", "rect"]
  return symbols[Math.floor(Math.random() * symbols.length)]
}

// 生成渐变颜色数组
const getModifiedColors = (numColors) => {
  const colors = []
  const hue = 0 // 红色
  const saturation = 100 // 饱和度
  const lightnessStart = 90 // 起始亮度
  const lightnessEnd = 30 // 结束亮度
  const lightnessStep = (lightnessEnd - lightnessStart) / (numColors - 1)

  for (let i = 0; i < numColors; i++) {
    const lightness = lightnessStart + lightnessStep * i
    colors.push(`hsl(${hue}, ${saturation}%, ${lightness}%)`)
  }
  return colors
}

// 图表配置项
const option = {
  title: {
    text: "",
  },
  tooltip: {
    trigger: "item",
    enterable: true,
    height: 100,
    triggerOn: "mousemove",
    formatter: "{c}",
  },
  graphic: [
    {
      type: "text",
      left: "center",
      top: "2%",
      style: {
        fill: "#333",
        fontSize: 14,
        fontWeight: "bold",
      },
    },
  ],
  visualMap: {
    type: "continuous",
    min: 1,
    max: 50,
    calculable: false,
    inRange: {
      color: getModifiedColors(30),
    },
    textStyle: {
      color: "#333",
    },
    orient: "horizontal",
    right: "12%",
    top: "0%",
  },
  grid: {
    left: "6%",
    top: "10%",
    right: "10%",
  },
  xAxis: {
    splitLine: { show: false },
    axisTick: { show: false },
    axisLine: {
      onZero: false,
      lineStyle: {
        color: "#100202",
      },
      symbol: ["none", "arrow"],
      symbolSize: [8, 12],
      symbolOffset: [10, 0],
      symbolRotate: 0,
    },
  },
  yAxis: {
    splitLine: { show: false },
    axisTick: { show: false },
    axisLine: {
      onZero: false,
      lineStyle: {
        color: "#100202",
      },
      symbol: ["none", "arrow"],
      symbolSize: [8, 12],
      symbolOffset: [10, 0],
      symbolRotate: 0,
    },
  },
  series: [
    {
      symbol: randomSymbol,
      symbolSize: 14,
      data: [],
      type: "scatter",
    },
  ],
}
</script>
