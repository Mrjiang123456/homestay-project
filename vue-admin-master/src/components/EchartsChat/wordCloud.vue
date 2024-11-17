<template>
  <div class="chart-container" ref="myRef" />
</template>

<script setup>
import * as echarts from "echarts"
import { onMounted, ref, watch, nextTick } from "vue"
import "echarts-wordcloud"

// 定义属性
const myRef = ref()
const myChart = ref()
const props = defineProps(["data", "colorStops", "route"])

// 更新图表数据
const updateChart = () => {
  if (!myChart.value) return
  option.series[0].data = props.data
  myChart.value.setOption(option)
}

// 监听数据变化并更新图表
watch(
  () => props.data,
  (newData) => {
    if (newData) {
      updateChart(newData)
    }
  },
  { immediate: true }, // 初始数据也能被捕获
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

// 图表配置项
const option = {
  title: {
    text: "",
  },
  series: [
    {
      type: "wordCloud",
      shape: "diamond",
      keepAspect: false,
      left: "center",
      top: "center",
      width: "80%",
      height: "80%",
      sizeRange: [30, 80],
      rotationRange: [-90, 90],
      rotationStep: 45,
      gridSize: 8,
      drawOutOfBound: false,
      layoutAnimation: false,
      textStyle: {
        fontFamily: "sans-serif",
        fontWeight: "bold",
        color: () => {
          // 生成随机 RGB 颜色
          return `rgb(${[0, 1, 2]
            .map(() => Math.round(Math.random() * 255))
            .join(",")})`
        },
      },
      emphasis: {
        focus: "self",
        textStyle: {
          textShadowBlur: 10,
          textShadowColor: "#333",
        },
      },
      data: [],
    },
  ],
}
</script>
