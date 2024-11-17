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

  option.xAxis.data = props.data.map((item) => item.name)
  option.series[0].data = props.data.map((item) => Number(item.value))
  option.series[0].name = props.route ? props.route.meta.title : "活跃度趋势"
  option.xAxis.name = props.route ? props.route.meta.xname : ""
  option.yAxis.name = props.route ? props.route.meta.yname : ""
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
      if (props.data && props.data.length) {
        updateChart()
      }
    }
  })
})

// 图表配置项
const option = {
  tooltip: {},
  legend: {
    textStyle: { color: "gray" },
    top: 20,
    right: 20,
  },
  grid: {
    left: "2%",
    right: "5%",
    bottom: "0%",
    containLabel: true,
  },
  xAxis: {
    type: "category",
    data: [],
    splitLine: { show: false },
    axisLabel: {
      interval: "auto",
      fontSize: 12,
      rotate: 0,
    },
  },
  yAxis: {
    type: "value",
    splitLine: { show: false },
  },
  series: [
    {
      name: "",
      data: [],
      type: "line",
      label: {
        show: true,
        position: "top",
        distance: 15,
        verticalAlign: "middle",
        color: "#424656",
        fontSize: 14,
      },
      smooth: true,
      areaStyle: {
        color: new echarts.graphic.LinearGradient(
          0,
          1,
          0,
          0,
          props.colorStops,
          false,
        ),
      },
      symbol: "circle",
      symbolSize: 7,
      itemStyle: {
        color: "#0F5197",
        borderColor: "#0F5197",
      },
      lineStyle: { color: "#0F5197" },
      emphasis: { focus: "series" },
    },
  ],
}
</script>
