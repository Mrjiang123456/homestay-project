<template>
  <div class="chart-container" ref="myRef" />
</template>

<script setup>
import * as echarts from "echarts";
import { nextTick, onMounted, ref, watch } from "vue";

// 定义属性
const myRef = ref(null)
const myChart = ref(null)
const props = defineProps(["data", "colorStops", "route"])

// 更新图表数据
const updateChartData = () => {
  if (myChart.value) {
    option.series[0].data = props.data
    myChart.value.setOption(option)
  }
}

// 监听数据变化并更新图表
watch(
  () => props.data,
  (newData) => {
    if (newData) {
      updateChartData()
    }
  },
  { immediate: true }, // 初始时立即执行，以捕获初始数据
)

// 初始化 ECharts
onMounted(() => {
  nextTick(() => {
    if (myRef.value) {
      myChart.value = echarts.init(myRef.value)
      updateChartData() // 设置初始数据
    }
  })
})

// 图表配置
const option = {
  title: {
    text: "",
    left: "center",
  },
  tooltip: {
    trigger: "item",
  },
  legend: {
    orient: "vertical", // 设置图例为垂直排列
    left: "left", // 将图例放在左边
  },
  toolbox: {
    show: true,
  },
  series: [
    {
      type: "pie",
      radius: [25, 175],
      center: ["50%", "50%"],
      roseType: "area",
      itemStyle: {
        borderRadius: 8,
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
