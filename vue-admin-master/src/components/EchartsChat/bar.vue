<template>
  <div class="chart-container" ref="myRef" />
</template>

<script setup>
import * as echarts from "echarts"
import { nextTick, onMounted, ref, watch } from "vue"

// 定义属性
const myRef = ref(null)
const myChart = ref(null)
const props = defineProps(["data", "colorStops", "route"])

// 更新图表数据
const updateChart = () => {
  if (myChart.value && props.data) {
    // 更新 X 轴和 Y 轴的名称
    if (props.route) {
      option.xAxis.name = props.route.meta.xname || ""
      option.yAxis.name = props.route.meta.yname || ""
    }

    // 更新 X 轴和系列数据
    option.xAxis.data = props.data.map((item) => item.name)
    option.series[0].data = props.data.map((item) => Number(item.value))

    // 更新渐变色配置
    option.series[0].itemStyle.color.colorStops = props.colorStops || []

    // 应用更新后的配置
    myChart.value.setOption(option)
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
    if (myRef.value) {
      myChart.value = echarts.init(myRef.value)
      updateChart()
    }
  })
})

// 图表配置项
const option = {
  title: {
    text: "",
  },
  tooltip: {},
  grid: {
    left: "1%",
    right: "5%",
    bottom: "0%",
    containLabel: true,
  },
  xAxis: {
    name: "",
    axisLabel: {
      interval: "auto",
      fontSize: 12,
      rotate: 0,
    },
    data: [],
    splitLine: {
      show: false,
    },
  },
  yAxis: {
    type: "value",
    name: "",
    splitLine: {
      show: false,
    },
  },
  series: [
    {
      type: "bar",
      data: [],
      label: {
        show: true,
        position: "top",
        distance: 15,
        verticalAlign: "middle",
        color: "#424656",
        fontSize: 14,
      },
      showBackground: false,
      itemStyle: {
        color: {
          type: "linear",
          x: 0,
          y: 1,
          x2: 0,
          y2: 0,
          colorStops: [],
        },
      },
    },
  ],
}
</script>
